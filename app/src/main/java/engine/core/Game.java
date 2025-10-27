package engine.core;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Game {
    private Stage stage;
    private final Canvas canvas;
    private Scene scene;
    private GameState currentState;


    private boolean running = true;

    public Game(Stage stage, double width, double height) {
        this.stage = stage;
        this.canvas = new Canvas(width, height);
        this.scene = new Scene(new javafx.scene.layout.Pane(canvas));
        stage.setScene(scene);
    }

    // Called every loop iteration
    public void update(double delta) {
        if (currentState != null) {
            currentState.update(delta);
        }    
    }

    // Called via Platform.runLater from loop
    public void render(GraphicsContext gc) {
        if (currentState != null) {
            currentState.render(gc);
        }        
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.onExit();
        } else {
            currentState = newState;
            currentState.onEnter();
        } 
    }
    
    public Canvas getCanvas() {
        return canvas;
    }

    public Scene getScene() {
        return scene;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    public Stage getStage() {
        return stage;
    }

        public boolean isRunning() {
        return running;
    }

    public void stopRunning() {
        running = false;
    }
    
}
