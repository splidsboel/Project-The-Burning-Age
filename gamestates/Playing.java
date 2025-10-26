package gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.GamePanel;
import tile.TileManager;
import tools.Renderable;
import world.Entity;
import world.actor.Player;

public class Playing extends State implements Statemethods{
    public Entity entity;
    public Player player;
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
        player = new Player(gp,gp.tileSize * 13, gp.tileSize * 15);


        tileM = gp.tileM;
    }

    @Override
    public void update() {
        player.update();
        gp.getEntityM().update();
        
    }

    @Override
    public void draw(Graphics2D g2) {
        // Draw background first
        gp.tileM.draw(g2);

        // Collect and sort visible entities
        List<Renderable> drawList = new ArrayList<>(gp.getEntityM().getVisibleEntities(
            player.getCameraX(),
            player.getCameraY(),
            gp.screenWidth,
            gp.screenHeight
        ));
        drawList.add(player);

        drawList.sort(Comparator.comparingDouble(Renderable::getBottomY));

        // Draw entities in depth order
        for (Renderable r : drawList) {
            r.draw(g2, player.getCameraX(), player.getCameraY());

            if(p_pressed) {
                // Debug: draw collision box and text
                Rectangle sa = r.getSolidArea();
                g2.setColor(Color.GREEN);
                g2.drawRect((int)(sa.x - player.getCameraX()), (int)(sa.y - player.getCameraY()), sa.width, sa.height);

                gp.debugText(g2);
            }
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.up = true;
            case KeyEvent.VK_S -> player.down = true;
            case KeyEvent.VK_A -> player.left = true;
            case KeyEvent.VK_D -> player.right = true;
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
