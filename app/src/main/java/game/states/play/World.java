package game.states.play;

import java.util.ArrayList;
import java.util.List;

import engine.core.Game;
import engine.map.TiledMap;
import engine.map.TiledMapLoader;
import engine.render.Camera;
import game.entities.Entity;
import game.entities.actors.Player;
import game.states.PlayState;
import game.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;

public class World extends PlayState {
    //Map
    private final Tile[][] tiles;
    private TiledMap map;
    private final int mapWidth, mapHeight; //tileSize;

    //Screen
    private final double canvasWidth;
    private final double canvasHeight;

    //Entites
    private final List<Entity> entities = new ArrayList<>();
    private final Player player;
    private final Camera camera;

    public World(Game game) {
        super(game);
        this.canvasWidth = game.getCanvas().getWidth();
        this.canvasHeight = game.getCanvas().getHeight();

        // Load map and renderer
        TiledMapLoader loader = new TiledMapLoader();
        TiledMapLoader.LoadedMapData data = loader.load("/maps/world.tmj");
        map = new TiledMap(data);
        this.mapWidth= map.getWidth();
        this.mapHeight = map.getHeight();
        //this.tileSize = map.getTileSize();
        tiles = new Tile[mapHeight][mapWidth];


        System.out.println("World initialized.");

        
    

        // Create player and camera
        camera = new Camera(game.getCanvas().getWidth(), game.getCanvas().getHeight());
        player = new Player(game, game.getOriginalTileSize(), game.getOriginalTileSize());
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

    // if you want camera scrolling uncomment:
    //camera.apply(g);

    // In render:
    for (int y = 0; y < map.getHeight(); y++) {
        for (int x = 0; x < map.getWidth(); x++) {
            Tile tile = map.getTile(x, y);
            if (tile != null)
                tile.render(g, x * map.getTileWidth(), y * map.getTileHeight(), game.getOriginalTileSize());
        }
    }

    // 3. draw entities (player etc.) above world
    for (Entity e : entities) {
        e.render(g);
    }

    g.restore();
}
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
