package engine.core;

import engine.input.MouseInput;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Game {
    //Core Systems
    private MouseInput mouseInput;

    private final Stage stage;
    private final Canvas canvas;
    private final Scene scene;
    private GameState currentState;
    private boolean running = true;

    public Game(Stage stage, double width, double height) {
        //Game setup
        this.stage = stage;
        this.canvas = new Canvas(width, height);
        this.scene = new Scene(new javafx.scene.layout.Pane(canvas));
        stage.setScene(scene);
        System.out.println("Game initialized");

        initSystems();

    }

    private void initSystems() {
        initMouseInput();
        
    }

    public void update(double delta) {
        if (currentState != null) {
            currentState.update(delta);
        }    
    }

    public void render(GraphicsContext gc) {
        if (currentState != null) {
            currentState.render(gc);
        }        
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.onExit();
        }

        currentState = newState;
        currentState.onEnter();
    }



    private void initMouseInput() {
        mouseInput = new MouseInput();
        canvas.setOnMouseMoved(mouseInput::onMouseMoved);
        canvas.setOnMousePressed(mouseInput::onMousePressed);
        canvas.setOnMouseReleased(mouseInput::onMouseReleased);
    }


    //GETTERS
    
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

    public MouseInput getMouseInput() {
        return mouseInput;
    }
    
}
