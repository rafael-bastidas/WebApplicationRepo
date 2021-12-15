/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rafael Bastidas
 */
public class InitSession {
    
    Connection conexion;

    public InitSession() {
        Conexion con = new Conexion();
        conexion = con.getConexion();
    }
    
    public DataUser getUserByIdusers(int _id){
        
        PreparedStatement ps;
        ResultSet rs;
        DataUser dataUser = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM users WHERE idusers=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int idusers = rs.getInt("idusers");
                String user = rs.getString("user");
                String password = rs.getString("password");
                String bio = rs.getString("bio");
                String imagen = rs.getString("imagen");
                
                dataUser = new DataUser(idusers, user, password, bio, imagen);
            }
            
            return dataUser;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
    
    public DataUser getUserByUserAndPassword(String _user, String _password){
        
        PreparedStatement ps;
        ResultSet rs;
        DataUser dataUser = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM users WHERE user=? AND password=?");
            ps.setString(1, _user);
            ps.setString(2, _password);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int idusers = rs.getInt("idusers");
                String user = rs.getString("user");
                String password = rs.getString("password");
                String bio = rs.getString("bio");
                String imagen = rs.getString("imagen");
                
                dataUser = new DataUser(idusers, user, password, bio, imagen);
            }
            
            return dataUser;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
    
}
