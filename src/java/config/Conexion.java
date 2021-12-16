/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael Bastidas
 */
public class Conexion {
    
    public Connection getConexion() {
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://bfhbxriumysvx17kbyus-mysql.services.clever-cloud.com/bfhbxriumysvx17kbyus", "uzqrd2jf7vblttv2", "ok3L8EkkqRejiyJUmYaF");
            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/rafaelba_repo", "root", "admin");
            return conexion;
        }catch(SQLException e) {
            System.out.println("Error de conexion"+e.toString());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
