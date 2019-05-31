
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matthias
 */
public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens;
    private Character player;
    private Shot shot;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int deaths = 0;

    private boolean ingame = true;
    private final String explosion = "./explosion.png";

    private Thread animator;
    

    public Board() {
        
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public void gameInit() {

        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        player = new Character();
        shot = new Shot();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * draws Image for Aliens
     * @param g 
     */
    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    /**
     * draws image for Player character
     * @param g 
     */
    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            ingame = false;
        }
    }

    /**
     * draws Image for Shot
     * @param g 
     */
    public void drawShot(Graphics g) {

        if (shot.isVisible()) {
            
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    /**
     * draws Graphics in UI
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * Run Method of the main Thread
     */
    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        
        
        while (ingame) {

            repaint();
            player.act();
            if (shot.isVisible()) {

                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Alien alien: aliens) {

                    int alienX = alien.getX();
                    int alienY = alien.getY();

                    if (alien.isVisible() && shot.isVisible()) {
                        if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY) && shotY <= (alienY + ALIEN_HEIGHT)) {
                            ImageIcon ii = new ImageIcon(explosion);
                            alien.setImage(ii.getImage());
                            alien.setDying(true);
                            deaths++;
                            shot.die();
                        }
                    }
                }

                int y = shot.getY();
                y -= 4;

                if (y < 0) {
                    shot.die();
                } else {
                    shot.setY(y);
                }
            }

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                
            }
            
            beforeTime = System.currentTimeMillis();
        }
    }

    /**
     * detects Key Actions and activates methods in Character class
     * moves character image in UI
     * starts shutdown process
     */
    private class TAdapter extends KeyAdapter {

        /**
         * activates Character.keyReleased
         * @param e 
         */
        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        /**
         * activates Character.keyPressed
         * moves character image in UI
         * starts shotdown Process
         * @param e 
         */
        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                
                if (ingame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
            if(key == KeyEvent.VK_ESCAPE){
                String name = JOptionPane.showInputDialog(null, "Insert Name:");
                Application.shutDown(name, deaths);
            }
        }
    }
}
