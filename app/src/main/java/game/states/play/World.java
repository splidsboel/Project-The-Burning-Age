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
import game.entities.behavior.Hittable;
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
        camera.setZoom(3);
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
        camera.apply(g);

        int tileSize = (int)game.getTileSize();

        // --- compute visible tile range ---
        int startX = (int)(camera.getX() / tileSize);
        int endX   = (int)((camera.getX() + camera.getViewportWidth() / camera.getZoom()) / tileSize) + 1;
        int startY = (int)(camera.getY() / tileSize);
        int endY   = (int)((camera.getY() + camera.getViewportHeight() / camera.getZoom()) / tileSize) + 1;

        // clamp to map size
        startX = Math.max(0, startX);
        startY = Math.max(0, startY);
        endX   = Math.min(map.getMapWidth(),  endX);
        endY   = Math.min(map.getMapHeight(), endY);

        // --- draw only visible tiles ---
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Tile tile = map.getTile(x, y);
                if (tile == null) continue;

                double worldX = x * tileSize;
                double worldY = y * tileSize;

                tile.render(g, worldX, worldY, tileSize);
            }
        }

        // --- draw only visible entities ---
        entities.sort(Comparator.comparingDouble(Entity::getBottomY));
        for (Entity e : entities) {
            // skip off-screen entities
            double ex = e.getX();
            double ey = e.getY();
            double ew = e.getWidth();
            double eh = e.getHeight();

            boolean visible =
                ex + ew > camera.getX() &&
                ex < camera.getX() + camera.getViewportWidth() / camera.getZoom() &&
                ey + eh > camera.getY() &&
                ey < camera.getY() + camera.getViewportHeight() / camera.getZoom();

            if (visible) e.render(g);
        }
        debug(g);
        g.restore();

    }


    public void debug(GraphicsContext g) {
        // --- debug solid areas ---
        if (game.getKeyboardInput().isKeyPressed(javafx.scene.input.KeyCode.SPACE)) {
            for (Entity e : entities) {
                g.setLineWidth(0.4);
                if (e instanceof Collidable c && c.getSolidArea() != null) {
                    var s = c.getSolidArea();
                    g.setStroke(javafx.scene.paint.Color.GREEN);
                    g.strokeRect(s.getMinX(), s.getMinY(), s.getWidth(), s.getHeight());
                }
                if(e instanceof Hittable hit && hit.getHitbox() != null) {
                    var h = hit.getHitbox();
                    g.setStroke(javafx.scene.paint.Color.RED);
                    g.strokeRect(h.getMinX(), h.getMinY(), h.getWidth(), h.getHeight());
                }
            }
        }
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
