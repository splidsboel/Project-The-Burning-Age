package engine.core;

import engine.input.KeyboardInput;
import engine.input.MouseInput;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Game {
    //Core Systems
    private MouseInput mouseInput;
    private KeyboardInput keyboardInput;

    //World Settings
    private double originalTileSize;



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
        System.out.println("Game initialized.");

        initSystems();

    }

    private void initSystems() {
        initInputs(); //Keyboard and mouse
        initWorldSettings();
        
    }

    private void initWorldSettings() {
        originalTileSize = 32;
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



    private void initInputs() {
        mouseInput = new MouseInput();
        keyboardInput = new KeyboardInput();
        canvas.setOnMouseMoved(mouseInput::onMouseMoved);
        canvas.setOnMousePressed(mouseInput::onMousePressed);
        canvas.setOnMouseReleased(mouseInput::onMouseReleased);
        canvas.setOnKeyPressed(keyboardInput::onKeyPressed);
        canvas.setOnKeyReleased(keyboardInput::onKeyReleased);
    }


    //GETTERS

    public double getOriginalTileSize() {
        return originalTileSize;
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

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public KeyboardInput getKeyboardInput() {
        return keyboardInput;
    }

    
}
