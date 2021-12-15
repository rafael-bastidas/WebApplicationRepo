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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael Bastidas
 */
public class PruebaDAO {

    Connection conexion;
    
    public PruebaDAO() {
        Conexion con = new Conexion();
        conexion = con.getConexion();
    }
    
    public List<Prueba> listarPrueba(){
        
        PreparedStatement ps;
        ResultSet rs;
        List<Prueba> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT prueba.idprueba, prueba.name FROM prueba");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("idprueba");
                String name = rs.getString("name");
                
                Prueba prueba = new Prueba(id,name);
                lista.add(prueba);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
    
    public Prueba mostrarPrueba(int _id){
        
        PreparedStatement ps;
        ResultSet rs;
        Prueba prueba = null;
        
        try {
            ps = conexion.prepareStatement("SELECT idprueba, name FROM prueba WHERE idprueba=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("idprueba");
                String name = rs.getString("name");
                
                prueba = new Prueba(id,name);
            }
            
            return prueba;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
    
    public boolean insertar(Prueba prueba){
        
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("INSERT INTO prueba (name) VALUES (?)");
            ps.setString(1, prueba.getName());
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        
    }
    
    public boolean actualizar (Prueba prueba){
        
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("UPDATE prueba SET name=? WHERE idprueba=?");
            ps.setString(1, prueba.getName());
            ps.setInt(2, prueba.getId());
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        
    }
    
    public boolean eliminar (int _id){
        
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("DELETE FROM prueba WHERE idprueba=?");
            ps.setInt(1, _id);
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        
    }
    
}
