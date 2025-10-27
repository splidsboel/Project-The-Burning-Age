package game.states.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.core.Game;
import engine.map.TiledMap;
import game.entities.Entity;
import game.entities.actors.Player;
import game.states.PlayState;
import javafx.scene.canvas.GraphicsContext;

public class World extends PlayState {
    private final double canvasWidth;
    private final double canvasHeight;
    private double x,y;

    private final List<Entity> entities = new ArrayList<>();
    private final Player player;
    //private final Camera camera;

    public World(Game game) {
        super(game);
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();
        System.out.println("World initialized.");

        //camera = new Camera(game.getCanvas().getWidth(), game.getCanvas().getHeight());
        player = new Player(game, game.getOriginalTileSize() * 2, game.getOriginalTileSize() * 2);
        
        entities.add(player);
    }

    @Override
    public void update(double delta) {
        player.update(delta);
        //camera.follow(player, game.getWorldWidth(), game.getWorldHeight());
    }

    @Override
    public void render(GraphicsContext g) {
        g.save();
        //camera.apply(g);
        for (Entity e : entities) {
            e.render(g);
        }
        //spawnEntities();

        g.restore();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void spawnEntities() throws IOException {
        TiledMap map = TiledMap.load("assets/maps/world.tmj");
        for (TiledMap.Layer layer : map.layers) {
            if ("objectgroup".equals(layer.type)) {
                for (TiledMap.MapObject obj : layer.objects) {
                    switch (obj.type) {
                        //case "Tree" -> addEntity(new Tree(game, obj.x, obj.y));
                   }
                }
            }
        }
    }

}
