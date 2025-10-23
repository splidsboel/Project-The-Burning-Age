package gamestates;

import entity.Entity;
import entity.Player;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import main.GamePanel;
import tile.TileManager;
import static tools.Constants.PlayerConstants.RUNNING_LEFT;
import static tools.Constants.PlayerConstants.RUNNING_RIGHT;
import world.DecorManager;

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
        entity = new Entity(gp);
        player = new Player(gp);
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
        gp.tileM.draw(g2);
        gp.decorM.draw(g2, player.cameraX, player.cameraY);
        player.render(g2);

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
            getPlayer().playerAction = RUNNING_LEFT;
        }
        if (e.getX() > getPlayer().screenX + (gp.tileSize/2)) {
            getPlayer().playerAction = RUNNING_RIGHT;
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
        if (code == KeyEvent.VK_A && gp.getPlaying().getPlayer().isUp()) {
            gp.getPlaying().getPlayer().setLeft_up(true);
            } else if (code == KeyEvent.VK_A && gp.getPlaying().getPlayer().isDown()) {
                gp.getPlaying().getPlayer().setLeft_down(true);
            } else if (code == KeyEvent.VK_D && gp.getPlaying().getPlayer().isUp()) {
                gp.getPlaying().getPlayer().setRight_up(true);
            } else if (code == KeyEvent.VK_D && gp.getPlaying().getPlayer().isDown()) {
                gp.getPlaying().getPlayer().setRight_down(true);
            }
            
            // Handle single-direction movements
            switch (code) {
                case KeyEvent.VK_W -> gp.getPlaying().getPlayer().setUp(true);
                case KeyEvent.VK_S -> gp.getPlaying().getPlayer().setDown(true);
                case KeyEvent.VK_A -> gp.getPlaying().getPlayer().setLeft(true);
                case KeyEvent.VK_D -> gp.getPlaying().getPlayer().setRight(true);
                case KeyEvent.VK_SPACE -> gp.getPlaying().getPlayer().dash();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gp.getPlaying().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_S:
                gp.getPlaying().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_A:
                gp.getPlaying().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                gp.getPlaying().getPlayer().setRight(false);
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
