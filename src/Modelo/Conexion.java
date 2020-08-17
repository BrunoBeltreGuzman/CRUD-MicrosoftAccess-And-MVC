/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author diosl
 */
public class Conexion {
    
    private Connection con;
    private PreparedStatement ps;

    private String url="jdbc:ucanaccess://C:\\BD\\DataBaseEstudiante.accdb";
    
    public Connection getConexion(){
        
        try {
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println(e);
        }
        return con;
    }  
}
