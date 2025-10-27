package engine.core;

import java.util.concurrent.atomic.AtomicBoolean;

import game.states.MenuState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Engine extends Application {

    @Override
    public void start(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        Pane root = new Pane();
        Scene scene = new Scene(root, width, height);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.setScene(scene);

        System.out.println("Game starting...");
        Game game = new Game(stage, width, height);
        game.changeState(new MenuState(game)); // <-- create state first
        startGameThread(game);    // <-- then start loop
        stage.show();
        
    }



    private void startGameThread(Game game) {
        AtomicBoolean renderPending = new AtomicBoolean(false);

        Thread gameThread = new Thread(() -> {
            long lastTime = System.nanoTime();

            while (game.isRunning()) {
                long now = System.nanoTime();
                double delta = (now - lastTime) / 1e9;
                if (delta > 0.1) delta = 0.1;
                lastTime = now;

                game.update(delta);
                GraphicsContext gc = game.getGraphicsContext();
                if (renderPending.compareAndSet(false, true)) {
                    Platform.runLater(() -> {
                        game.render(gc);
                        renderPending.set(false);
                    });
                }

                Thread.onSpinWait();
            }
        }, "GameThread");

        gameThread.setDaemon(true);
        gameThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
