package game.states.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.core.Game;
import engine.map.TiledMap;
import engine.map.TiledRenderer;
import engine.render.Camera;
import game.entities.Entity;
import game.entities.actors.Player;
import game.states.PlayState;
import javafx.scene.canvas.GraphicsContext;

public class World extends PlayState {
    private final double canvasWidth;
    private final double canvasHeight;
    private double x,y;

    private TiledMap map;
    private TiledRenderer renderer;
    private final List<Entity> entities = new ArrayList<>();
    private final Player player;
    private final Camera camera;

    public World(Game game) {
        super(game);
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();
        System.out.println("World initialized.");

        
        // Load map and renderer
        //---loadMap();
        // Create player and camera
        camera = new Camera(game.getCanvas().getWidth(), game.getCanvas().getHeight());
        player = new Player(game, game.getOriginalTileSize(), game.getOriginalTileSize());
        entities.add(player);
    }

    @Override
    public void update(double delta) {
        player.update(delta);
        camera.follow(player, game.getWorldWidth(), game.getWorldHeight());
    }

    @Override
    public void render(GraphicsContext g) {
        g.save();
        //--renderer.render(g);
        //--renderEntities();
        camera.apply(g);
        for (Entity e : entities) {
            e.render(g);
        }
        g.restore();
    }

    private void loadMap() {
        try {
            map = TiledMap.load("/assets/maps/world.tmj");
            renderer = new TiledRenderer(map);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load map", e);
        }
    }

    public void renderEntities() throws IOException {
        TiledMap map = TiledMap.load("assets/maps/world.tmj");
        for (TiledMap.Layer layer : map.layers) {
            if ("objectgroup".equals(layer.type)) {
                for (TiledMap.MapObject obj : layer.objects) {
                    switch (obj.type) {
                            //case "treeWide" -> addEntity(new TreeWide(game, obj.x, obj.y));
                            //case "treeTall" -> addEntity(new TreeTall(game, obj.x, obj.y));
                   }
                }
            }
        }
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
