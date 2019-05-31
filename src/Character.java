
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matthias
 */
public class Character extends Sprite implements Commons{
    private final int START_Y = 280;
    private final int START_X = 270;

    private final String playerImg = "./player.png";
    private int width;

    public Character() {

        initCharacter();
    }

    private void initCharacter() {
        
        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }

    /**
     * moves the character and stops at the border
     */
    public void act() {
        
        x += dx;
        
        if (x <= 2) {
            x = 2;
        }
        
        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
    }

    /**
     * sets the value to dx to move in the right direction
     * @param e 
     */
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 2;
        }
    }

    /**
     * character stops when key is released
     * @param e 
     */
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 0;
        }
    }
}
