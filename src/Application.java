
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Application extends JFrame implements Commons{

    private static Application app;
    private Connection con;
    private static boolean scoreIsBetter;
    
    /**
     * create Connection to Database
     * and intialize UI
     */
    public Application() {
        initUI();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            
        }
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost/", "postgres", "postgres");
        } catch (SQLException ex) {
            System.out.println("Error");
        }
    }
    
    /**
     * Initializes the UI
     */
    public void initUI(){
        
        add(new Board());
        setResizable(false);
        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
    }
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
            app = new Application();
            app.setVisible(true);
    }
    
    /**
     * shuts down the program and saves score
     * @param name
     * @param score 
     */
    public static void shutDown(String name, int score){
        Boolean exists;
        try {
            app.createTable();
            exists = app.checkData(name,score);
            if(!exists){
                app.insertData(name, score);
            }
            else if(scoreIsBetter){
                app.updateData(name, score);
            }
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        app.dispose();
        System.exit(0);
    }
    
    /**
     * creates table if doesnt exist
     * @throws Exception 
     */
    public void createTable() throws Exception{
        
        String sql = "CREATE TABLE IF NOT EXISTS SpaceInvadersScore"
                + "("
                + "     Name character varying PRIMARY KEY,"
                + "     Score integer NOT NULL"
                + ");";
        
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        
    }
    
    /**
     * inserts data into database if it doesnt exist
     * @param name
     * @param score
     * @throws SQLException 
     */
    public void insertData(String name, int score) throws SQLException{
        name = "'"+name+"'";
        String sql = "INSERT INTO SpaceInvadersScore VALUES("+name+","+score+");";
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
    }
    
    /**
     * checks if name is already in database
     * @param name
     * @param score
     * @return
     * @throws SQLException 
     */
    public boolean checkData(String name, int score) throws SQLException{
        name = "'"+name+"'";
        String sql = "SELECT * "
                + "FROM SpaceInvadersScore "
                + "WHERE name="+name;
        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while(rs.next()){
        if(rs!=null){
            if(rs.getInt("score")<score)
                scoreIsBetter=true;
                return true;
            }
            else
                return false;
        }
        return false;
    }
    
    /**
     * updates data if already exists and score is higher
     * @param name
     * @param score
     * @throws SQLException 
     */
    public void updateData(String name, int score) throws SQLException{
        name = "'"+name+"'";
        String sql = "UPDATE SpaceInvadersScore "
                + "SET score="+score+" "
                + "WHERE name="+name;
        Statement state = con.createStatement();
        state.executeUpdate(sql);
    }
}
