/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import config.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import modelo.DataFollow;
import modelo.DataRepositorie;
import modelo.DataRepositories;
import modelo.DataUser;
import modelo.InitSession;

/**
 *
 * @author Rafael Bastidas
 */
@MultipartConfig
@WebServlet(name = "SessionController", urlPatterns = {"/SessionController"})
public class SessionController extends HttpServlet {


 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion.equals("deleteUser")) {
            deleteUser(request, response);
        } else if (accion.equals("cerrarPerfil")) {
            cerrarPerfil(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = null;
        
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        
        InitSession initSession = new InitSession();
        DataUser dataUser = initSession.getUserByUserAndPassword(user, password);
        
        if (dataUser != null) {
            dispatcher = request.getRequestDispatcher("Home/index.jsp");
            //request.setAttribute("dataUser", dataUser);
            ServletContext application = this.getServletContext();
            application.setAttribute("dataUser", dataUser);
            
            List<DataRepositories> listarepositories = listarRepositories(dataUser.getIdusers());
            request.setAttribute("listaRepositories", listarepositories);
            
            List<DataFollow> listFollow = getListOfFollow(dataUser.getIdusers());
            request.setAttribute("listFollow", listFollow);
            List<DataFollow> listFollowers = getListOfFollowers(dataUser.getIdusers());
            request.setAttribute("listFollowers", listFollowers);
        } else {
            dispatcher = request.getRequestDispatcher("index.jsp");
            request.setAttribute("user", user);
            request.setAttribute("password", password);
            request.setAttribute("message", "Usuario no registrado");
        }
        
        dispatcher.forward(request, response);
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    public void cerrarPerfil(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        ServletContext application = this.getServletContext();
        application.removeAttribute("dataUser");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    public void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        int idusers = Integer.parseInt(request.getParameter("idusers"));
        System.out.println("Borrar usuario: idusers "+idusers);
        
        boolean validDelete = eliminarUsuario(idusers);
        
        if (!validDelete) {
            request.setAttribute("message", "No se pudo eliminar la cuenta de usuario");
        }
               
        ServletContext application = this.getServletContext();
        application.removeAttribute("dataUser");
        
        dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    public Connection establecerConexionDB() {
        Conexion con = new Conexion();
        return con.getConexion();
    }
    
    public List<DataRepositories> listarRepositories(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DataRepositories> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM repositories WHERE idusers=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("idrepositories");
                int idusers = rs.getInt("idusers");
                String namerepo = rs.getString("namerepo");
                
                DataRepositories dataRepositories = new DataRepositories(id,idusers,namerepo);
                lista.add(dataRepositories);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public boolean eliminarUsuario(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("DELETE FROM users WHERE idusers=?");
            ps.setInt(1, _id);
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<DataFollow> getListOfFollow(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DataFollow> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT users.idusers, users.user, follow.idfollow from users inner join follow on users.idusers = follow.idusersdest where follow.idusersorigin=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idfollow = rs.getInt("idfollow");
                int idusers = rs.getInt("idusers");
                String user = rs.getString("user");
                
                DataFollow dataFollow = new DataFollow(idfollow,idusers,user);
                lista.add(dataFollow);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<DataFollow> getListOfFollowers(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DataFollow> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT users.idusers, users.user, follow.idfollow from users inner join follow on users.idusers = follow.idusersorigin where follow.idusersdest=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idfollow = rs.getInt("idfollow");
                int idusers = rs.getInt("idusers");
                String user = rs.getString("user");
                
                DataFollow dataFollow = new DataFollow(idfollow,idusers,user);
                lista.add(dataFollow);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
