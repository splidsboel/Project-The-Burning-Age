package game.entities.behavior;

public interface Damageable {
    void takeDamage(int amount);
    boolean isDead();
}
