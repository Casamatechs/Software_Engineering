/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.troy.cs.frontend;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author niraj
 */
public class ProjectSoft {
    
     public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        
        new Login_iframe(null, true).show();
    }
    
}
