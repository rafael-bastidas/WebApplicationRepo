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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DataFollow;
import modelo.DataRepositories;
import modelo.DataUser;
import modelo.Prueba;

/**
 *
 * @author Rafael Bastidas
 */
@WebServlet(name = "DataUserController", urlPatterns = {"/DataUserController"})
public class DataUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        System.out.println("id: " + request.getParameter("idusers"));
        int idusers = Integer.parseInt(request.getParameter("idusers"));

        DataUser dataUser = getUserByIdusers(idusers);

        dispatcher = request.getRequestDispatcher("Register/index.jsp");
        request.setAttribute("dataUser", dataUser);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String path = "Home/index.jsp";

        int idusers = Integer.parseInt(request.getParameter("idusers"));
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String bio = request.getParameter("bio");
        String imagen = request.getParameter("imagen");

        DataUser dataUser = new DataUser(idusers, user, password, bio, imagen);

        if (idusers == 0) {
            boolean validuser = getUserByUser(user);
            if (!validuser) {
                int id = insertar(dataUser);
                System.out.println("id: " + id);
                dataUser.setIdusers(id);
            } else {
                request.setAttribute("message", "El usuario ya existe");
                path = "Register/index.jsp";
            }
        } else {
            actualizar(dataUser);
            List<DataRepositories> listarepositories = listarRepositories(dataUser.getIdusers());
            request.setAttribute("listaRepositories", listarepositories);
            
            List<DataFollow> listFollow = getListOfFollow(dataUser.getIdusers());
            request.setAttribute("listFollow", listFollow);
            List<DataFollow> listFollowers = getListOfFollowers(dataUser.getIdusers());
            request.setAttribute("listFollowers", listFollowers);
        }

        dispatcher = request.getRequestDispatcher(path);
        //request.setAttribute("dataUser", dataUser);
        ServletContext application = this.getServletContext();
        application.setAttribute("dataUser", dataUser);
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    
    public Connection establecerConexionDB() {
        Conexion con = new Conexion();
        return con.getConexion();
    }

    public int insertar(DataUser dataUser) {

        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("INSERT INTO users ( user, password, bio, imagen) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dataUser.getUser());
            ps.setString(2, dataUser.getPassword());
            ps.setString(3, dataUser.getBio());
            ps.setString(4, dataUser.getImagen());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return 0;
            }

            try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (int) generatedKeys.getLong(1);
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return 0;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean actualizar(DataUser dataUser) {

        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("UPDATE users SET user=?, password=?, bio=?, imagen=? WHERE idusers=?");
            ps.setString(1, dataUser.getUser());
            ps.setString(2, dataUser.getPassword());
            ps.setString(3, dataUser.getBio());
            ps.setString(4, dataUser.getImagen());
            ps.setInt(5, dataUser.getIdusers());
            ps.execute();

            return true;
        } catch (SQLException e) {
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

    public boolean getUserByUser(String _user) {

        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = conexion.prepareStatement("SELECT * FROM users WHERE user=?");
            ps.setString(1, _user);
            rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Line 145 " + e.toString());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public DataUser getUserByIdusers(int _id) {

        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        DataUser dataUser = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM users WHERE idusers=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();

            while (rs.next()) {

                int idusers = rs.getInt("idusers");
                String user = rs.getString("user");
                String password = rs.getString("password");
                String bio = rs.getString("bio");
                String imagen = rs.getString("imagen");

                dataUser = new DataUser(idusers, user, password, bio, imagen);
            }

            return dataUser;
        } catch (SQLException e) {
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

    public List<DataRepositories> listarRepositories(int _id) {
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DataRepositories> lista = new ArrayList<>();

        try {
            ps = conexion.prepareStatement("SELECT * FROM repositories WHERE idusers=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("idrepositories");
                int idusers = rs.getInt("idusers");
                String namerepo = rs.getString("namerepo");

                DataRepositories dataRepositories = new DataRepositories(id, idusers, namerepo);
                lista.add(dataRepositories);
            }

            return lista;
        } catch (SQLException e) {
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
