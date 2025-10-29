package game.tiles.behaviors;

public interface Swimmable {
    /** Vertical visual offset when entity is standing in this tile */
    double getSubmergeOffset(double tileSize);
}