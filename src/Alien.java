
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Initializes Alien Characters
 * @author Matthias
 */
public class Alien extends Sprite{
    private final String alienImg = "./alien.png";

    public Alien(int x, int y) {

        initAlien(x, y);
    }

    /**
     * Initializes Alien and sets it to the correct Coordinates
     * @param x
     * @param y 
     */
    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }


}
