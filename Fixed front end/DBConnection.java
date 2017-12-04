/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginSystem;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author niraj
 */
public class DBConnection {
    
    private Connection DBConnection;
    public Connection connect() {
        /*try{  
            Class.forName("com.sql.jdbc.Driver");
            System.out.println("Connection Success!");
            
        }catch(ClassNotFoundException cnfe){
            System.out.println("Connection Failed!" + cnfe);
        }
        */
        String url = "jdbc:mysql://localhost:3306/housing";
        try{
            DBConnection = (Connection) DriverManager.getConnection(url, "admin", "admin");
            System.out.println("Database Connected!!");
        }catch (SQLException se){
            System.out.println("No database!!"+ se);
        }
        return DBConnection;
    }
    
}
