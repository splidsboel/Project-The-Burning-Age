package game.states.play;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import engine.core.Game;
import engine.map.TiledMap;
import engine.map.TiledMapLoader;
import engine.render.Camera;
import game.entities.Entity;
import game.entities.actors.Player;
import game.entities.behavior.Collidable;
import game.states.PlayState;
import game.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;

public class World extends PlayState {
    private final List<Entity> entities = new ArrayList<>();
    private final Player player;
    private final Camera camera;
    private final TiledMap map;

    private static final String path = "/maps/world.tmj";

    public World(Game game) {
        super(game);

        // Load map
        TiledMapLoader.LoadedMapData data = game.getTiledLoader().load(path);
        map = new TiledMap(game, data);
        game.setTiledMap(map); // <-- make map accessible to other systems (Player)


        // Camera and player
        player = new Player(game, this, game.getOriginalTileSize(), game.getOriginalTileSize());
        camera = new Camera(game.getCanvas().getWidth(), game.getCanvas().getHeight());

        // Load entities from map
        entities.addAll(map.getEntities());
        entities.add(player);

        System.out.println("World initialized with " + entities.size() + " entities.");
    }

    @Override
    public void load() {
    
    }

    @Override
    public void update(double delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        camera.update(player.getX(),player.getY(),game.getTileSize(),map.getMapWidth(),(map.getMapHeight()));
    }

    @Override
    public void render(GraphicsContext g) {
        g.save();
        camera.apply(g); // enable if camera scrolling is implemented
        
        // Draw tiles
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                Tile tile = map.getTile(x, y);
                if (tile != null)
                    tile.render(g, x * game.getTileSize(), y * game.getTileSize(), game.getTileSize());
            }
        }

        // Sort and draaw entities
        entities.sort(Comparator.comparingDouble(Entity::getBottomY)); 
        for (Entity e : entities) {
            e.render(g);
        }

        // Debug 
        if (game.getKeyboardInput().isKeyPressed(javafx.scene.input.KeyCode.SPACE)) {
            // Draw debug shapes (world space)
            g.setLineWidth(1.5);
            for (Entity e : entities) {
                if (e instanceof Collidable c && c.getSolidArea() != null) {
                    var s = c.getSolidArea();
                    g.setStroke(javafx.scene.paint.Color.GREEN);
                    g.strokeRect(s.getMinX(), s.getMinY(), s.getWidth(), s.getHeight());
                }
            }
        }
        g.restore();

        // Draw FPS in screen space (HUD)
        g.setFill(javafx.scene.paint.Color.WHITE);
        g.fillText("FPS: " + game.getEngine().getFps(), 20, 30);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    //GETTERS
    public Player getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    

}
