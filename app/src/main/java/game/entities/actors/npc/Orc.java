package game.entities.actors.npc;

import engine.core.Game;
import game.entities.Actor;
import game.entities.behavior.Collidable;
import game.entities.behavior.Damageable;

public class Orc extends Actor implements Collidable, Damageable {

    public Orc(Game game, double x, double y, double width, double height, double speed) {
        super(game, x, y, width, height, speed);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void takeDamage(int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDead'");
    }

    @Override
    public boolean isSolid() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSolid'");
    }
}
