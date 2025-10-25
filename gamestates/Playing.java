package gamestates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.GamePanel;
import tile.TileManager;
import tools.Renderable;
import world.DecorManager;
import world.Entity;
import world.actor.Player;

public class Playing extends State implements Statemethods{
    public Entity entity;
    public Player player;
    public DecorManager decorM;
    public TileManager tileM;

    //Keys
    public boolean p_pressed = false;
    
    Graphics2D g2;

    public Playing(GamePanel gp) {
       super(gp);
       this.gp = gp;
       initializeClasses();
    }

    public void initializeClasses() {

        //ENTITIES AND OBJECTS
        player = new Player(gp,10,10);

        tileM = gp.tileM;
        decorM = gp.decorM;

    }

    @Override
    public void update() {
        player.update();
        gp.decorM.update();
        
    }

    @Override
    public void draw(Graphics2D g2) {
        //TILES
        gp.tileM.draw(g2);

        //PLAYER AND DECOR SORT
        List<Renderable> drawList = new ArrayList<>();
        drawList.add(player);
        drawList.addAll(gp.decorM.decorList);
        drawList.sort(Comparator.comparingDouble(Renderable::getBottomY));
        for (Renderable r : drawList) {
            if(r instanceof Player) {
                player.draw(g2, player.getCameraX(), player.getCameraY());
            } else if (r instanceof Entity e) {
                e.draw(g2, player.getCameraX(), player.getCameraX());
            }
        }

        if (p_pressed) {
            gp.debugText(g2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() < getPlayer().screenX + (gp.tileSize/2)) {
            getPlayer().playerAction = getPlayer().runningLeft;
        }
        if (e.getX() > getPlayer().screenX + (gp.tileSize/2)) {
            getPlayer().playerAction = getPlayer().runningRight;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        if (e.getWheelRotation() < 0) {
            gp.zoomInOut(10);
            
        } else if(e.getWheelRotation() > 0){
            gp.zoomInOut(-10);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Handle diagonal movements
        if (code == KeyEvent.VK_A && gp.getPlaying().getPlayer().up) {
            gp.getPlaying().getPlayer().left_up = true;;
            } else if (code == KeyEvent.VK_A && gp.getPlaying().getPlayer().down) {
                gp.getPlaying().getPlayer().left_down = true;
            } else if (code == KeyEvent.VK_D && gp.getPlaying().getPlayer().up) {
                gp.getPlaying().getPlayer().right_up = true;
            } else if (code == KeyEvent.VK_D && gp.getPlaying().getPlayer().down) {
                gp.getPlaying().getPlayer().right_down = true;
            }
            
            // Handle single-direction movements
            switch (code) {
                case KeyEvent.VK_W -> gp.getPlaying().getPlayer().up=true;
                case KeyEvent.VK_S -> gp.getPlaying().getPlayer().down=true;
                case KeyEvent.VK_A -> gp.getPlaying().getPlayer().left=true;
                case KeyEvent.VK_D -> gp.getPlaying().getPlayer().right=true;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gp.getPlaying().getPlayer().up = false;
                break;
            case KeyEvent.VK_S:
                gp.getPlaying().getPlayer().down = false;
                break;
            case KeyEvent.VK_A:
                gp.getPlaying().getPlayer().left = false;
                break;
            case KeyEvent.VK_D:
                gp.getPlaying().getPlayer().right = false;
                break;
            case KeyEvent.VK_ESCAPE:
                Gamestate.state = Gamestate.MENU;
                break;
            case KeyEvent.VK_P:
                p_pressed = true;
                break;
        }
    }


    //GETTERS
    public Player getPlayer() {
        return player;
    }
}
