package game.entities;

import engine.core.Game;

public abstract class Actor extends Entity {
    protected double speed;
    protected double dx, dy;

    //Stats
    protected int health;



    public Actor(Game game, double x, double y, double width, double height, double speed) {
        super(game, x, y, width, height);
        this.speed = speed;
    }

    @Override
    public void update(double delta) {

    }

}
