package game.tiles;

public class Tile {
    private final int id;
    private final String name;

    public Tile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
