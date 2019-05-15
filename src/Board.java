
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;
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
public class Board extends JPanel{
    private Image img;
    
    
    public Board(){
        initBoard();
    }
    
    public void initBoard(){
        loadImage();
        
        int w = img.getWidth(this);
        int h = img.getHeight(this);
        setPreferredSize(new Dimension(w,h));
    }
    
    private void loadImage(){
        ImageIcon ii = new ImageIcon("./star.png");
        img = ii.getImage();
    }
    
    public void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, null);
    }
    
    public void drawTest(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
       g2d.setRenderingHints(rh);
       
       Dimension size = getSize();
       double w = size.getWidth();
       double h = size.getHeight();
       
       Ellipse2D e = new Ellipse2D.Double(0,0,80,130);
       g2d.setStroke(new BasicStroke(1));
       g2d.setColor(Color.red);
       
       for(double deg = 0; deg < 360; deg += 5){
           AffineTransform at = AffineTransform.getTranslateInstance(w/2, h/2);
           at.rotate(Math.toRadians(deg));
           g2d.draw(at.createTransformedShape(e));
       }
    }
}
