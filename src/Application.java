
import java.awt.EventQueue;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matthias
 */
public class Application extends JFrame{

    public Application() {
        initUI();
    }
    
    public void initUI(){
        add(new Board());
        setResizable(false);
        setSize(400,300);
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application app = new Application();
            app.setVisible(true);
            
        });
    }
    
}
