
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
public class Character {
    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private int w,h;
    private Image img;
    
    public Character(){
        loadImage();
    }
    
    private void loadImage(){
        ImageIcon ii = new ImageIcon("./craft.png");
        img = ii.getImage();
        w = img.getWidth(null);
        h = img.getHeight(null);
    }
    
    public void move(){
        x+=dx;
        y+=dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Image getImg() {
        return img;
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_LEFT)
            dx = -2;
        if(key == KeyEvent.VK_RIGHT)
            dx = 2;
        if(key == KeyEvent.VK_UP)
            dy = -2;
        if(key == KeyEvent.VK_DOWN)
            dy = 2;
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_LEFT)
            dx = 0;
        if(key == KeyEvent.VK_RIGHT)
            dx = 0;
        if(key == KeyEvent.VK_UP)
            dy = 0;
        if(key == KeyEvent.VK_DOWN)
            dy = 0;
    }
}
