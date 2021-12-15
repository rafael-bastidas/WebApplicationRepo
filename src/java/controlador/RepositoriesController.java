/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import config.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DataRepositorie;
import modelo.DataRepositories;
import modelo.DatoMural;

/**
 *
 * @author Rafael Bastidas
 */
@WebServlet(name = "RepositoriesController", urlPatterns = {"/RepositoriesController"})
public class RepositoriesController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion.equals("delete")) {
            deleteRepo(request, response);
        } else if (accion.equals("verrepo")) {
            verRepo(request, response);
        } else if (accion.equals("deleteImgFromRepositorie")) {
            deleteImgFromRepositorie(request, response);
        } else if (accion.equals("updateMural")) {
            updateMural(request, response);
        } else if (accion.equals("likes")) {
            configLike(request, response);
        } else {
           System.out.println("No entro "); 
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        System.out.println("accion "+accion);
        if (accion.equals("crear repo")) {
            crearRepo(request, response);
        } else if (accion.equals("addImagenToRepositorie")) {
            addImagenToRepositorie(request, response);
        } else if (accion.equals("searchTags")) {
            searchTags(request, response);
        } else {
           System.out.println("No entro "); 
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    public void searchTags(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        String tags = request.getParameter("tags");
        
        List<DatoMural> listarMural = listarMuralByTags(tags);
        request.setAttribute("listaMural", listarMural);
        
        dispatcher = request.getRequestDispatcher("Mural/index.jsp");
        dispatcher.forward(request, response);
    }
    
    
    public void configLike(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        int idrepositorie = Integer.parseInt(request.getParameter("idrepositorie"));
        
        DataRepositorie dataRepositorie = getRepositorieByIdrepositorie(idrepositorie);
        darLikes(idrepositorie,dataRepositorie.getLikes()+1);
        
        List<DatoMural> listarMural = listarMural();
        request.setAttribute("listaMural", listarMural);
        
        dispatcher = request.getRequestDispatcher("Mural/index.jsp");
        dispatcher.forward(request, response);
    }
            
            
    public void updateMural(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        List<DatoMural> listarMural = listarMural();
        request.setAttribute("listaMural", listarMural);
        
        dispatcher = request.getRequestDispatcher("Mural/index.jsp");
        dispatcher.forward(request, response);
    }
            
            
    public void deleteImgFromRepositorie(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        int idrepositories = Integer.parseInt(request.getParameter("idrepositories"));
        int idrepositorie = Integer.parseInt(request.getParameter("idrepositorie"));
        int idusers = Integer.parseInt(request.getParameter("idusers"));
        System.out.println("idusers "+idusers+" idrepositorie "+idrepositorie);
        
        boolean validDelete = eliminarImagenDeRepositorie(idrepositorie);
        
        if (!validDelete) {
            request.setAttribute("message", "No se pudo eliminar la imagen del repositorio");
        }
        
        List<DataRepositories> listarepositories = listarRepositories(idusers);
        request.setAttribute("listaRepositories", listarepositories);
            
        DataRepositories dataRepositories = getRepositoriesByIdrepositories(idrepositories);
        request.setAttribute("dataRepositoriesSelect", dataRepositories);
        
        List<DataRepositorie> listaRepositorie = getListRepositorieByIdrepositories(idrepositories);
        request.setAttribute("listaRepositorieSelect", listaRepositorie);
                        
        dispatcher = request.getRequestDispatcher("Home/index.jsp");
        dispatcher.forward(request, response);
    }
    
    public void addImagenToRepositorie(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        int idrepositorie = Integer.parseInt(request.getParameter("idrepositorie"));
        int idrepositories = Integer.parseInt(request.getParameter("idrepositories"));
        int idusers = Integer.parseInt(request.getParameter("idusers"));
        String tags = request.getParameter("tags");
        String imagen = request.getParameter("imagen");
        System.out.println("dataRepositorie: idrepositorie "+idrepositorie+" idrepositories "+idrepositories+" idusers "+idusers+" tags "+tags+" imagen "+imagen);
        
        DataRepositorie dataRepositorie = new DataRepositorie(idrepositorie,idrepositories,tags,imagen);
        boolean validInsertarUpdate = true;
        if (idrepositorie == 0) {
            validInsertarUpdate = insertarImagenEnRepositorie(dataRepositorie);
        } else {
            validInsertarUpdate = actualizarImagenEnRepositorie(dataRepositorie);
        }
        
        if (validInsertarUpdate) {
            List<DataRepositorie> listaRepositorie = getListRepositorieByIdrepositories(idrepositories);
            request.setAttribute("listaRepositorieSelect", listaRepositorie);
        } else {
            request.setAttribute("message", "No se pudo Insertar la imagen en el repositorio");
        }
        
        DataRepositories dataRepositories = getRepositoriesByIdrepositories(idrepositories);
        request.setAttribute("dataRepositoriesSelect", dataRepositories);
            
        List<DataRepositories> listarepositories = listarRepositories(idusers);
        request.setAttribute("listaRepositories", listarepositories);
        
        dispatcher = request.getRequestDispatcher("Home/index.jsp");
        dispatcher.forward(request, response);
    }

    
    public void verRepo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        
        RequestDispatcher dispatcher = null;
        int idrepositories = Integer.parseInt(request.getParameter("idrepositories"));
        System.out.println("idrepositories "+idrepositories);
        
        
        DataRepositories dataRepositories = getRepositoriesByIdrepositories(idrepositories);
        request.setAttribute("dataRepositoriesSelect", dataRepositories);
        
        List<DataRepositories> listarepositories = listarRepositories(dataRepositories.getIdusers());
        request.setAttribute("listaRepositories", listarepositories);
        
        List<DataRepositorie> listaRepositorie = getListRepositorieByIdrepositories(idrepositories);
        request.setAttribute("listaRepositorieSelect", listaRepositorie);
        
        dispatcher = request.getRequestDispatcher("Home/index.jsp");
        dispatcher.forward(request, response);
    }
            
            
    public void deleteRepo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
                
        RequestDispatcher dispatcher = null;
        int idusers = Integer.parseInt(request.getParameter("idusers"));
        int idrepositories = Integer.parseInt(request.getParameter("idrepositories"));
        System.out.println("idusers "+idusers+" idrepositories "+idrepositories);
        
        boolean validDelete = eliminar(idrepositories, idusers);
        
        if (validDelete) {
            List<DataRepositories> listarepositories = listarRepositories(idusers);
            request.setAttribute("listaRepositories", listarepositories);
        } else {
            request.setAttribute("message", "No se pudo eliminar el repositorio");
        }
        
        dispatcher = request.getRequestDispatcher("Home/index.jsp");
        dispatcher.forward(request, response);
    }
    
    public void crearRepo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
                
        RequestDispatcher dispatcher = null;
        int idusers = Integer.parseInt(request.getParameter("idusers"));
        String namerepo = request.getParameter("namerepo");
        
        DataRepositories dataRepositories = new DataRepositories(0,idusers, namerepo);
        boolean validInsert = insertarboolean(dataRepositories);
        
        if (validInsert) {
            List<DataRepositories> listarepositories = listarRepositories(idusers);
            request.setAttribute("listaRepositories", listarepositories);
        } else {
            request.setAttribute("message", "No se pudo crear el repositorio");
        }
        
        dispatcher = request.getRequestDispatcher("Home/index.jsp");
        dispatcher.forward(request, response);
    }
    
    public Connection establecerConexionDB() {
        Conexion con = new Conexion();
        return con.getConexion();
    }
    
    public int insertar(DataRepositories dataRepositories) {

        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("INSERT INTO repositories ( namerepo, idusers) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dataRepositories.getNamerepo());
            ps.setInt(2, dataRepositories.getIdusers());
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
        }

    }
    
    public boolean insertarboolean (DataRepositories dataRepositories) {
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("INSERT INTO repositories ( namerepo, idusers) VALUES (?,?)");
            ps.setString(1, dataRepositories.getNamerepo());
            ps.setInt(2, dataRepositories.getIdusers());
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println("Error 130"+e.toString());
            return false;
        }
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
        }
        
    }
    
    public List<DatoMural> listarMural (){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DatoMural> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT users.idusers, users.user, repositories.namerepo, repositorie.idrepositorie, repositorie.tags, repositorie.imagen, repositorie.likes FROM users inner join repositories on users.idusers = repositories.idusers inner join repositorie on repositories.idrepositories = repositorie.idrepositories");
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idusers = rs.getInt("idusers");
                int idrepositorie = rs.getInt("idrepositorie");
                String user = rs.getString("user");
                String namerepo = rs.getString("namerepo");
                String tags = rs.getString("tags");
                String imagen = rs.getString("imagen");
                int likes = rs.getInt("likes");
                
                DatoMural datoMural = new DatoMural(idusers,idrepositorie,user,namerepo,tags,imagen,likes);
                lista.add(datoMural);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public List<DatoMural> listarMuralByTags (String _tags){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DatoMural> lista = new ArrayList<>();
        
        try {
            String searLikes = "%"+_tags+"%";
            ps = conexion.prepareStatement("SELECT users.idusers, users.user, repositories.namerepo, repositorie.idrepositorie, repositorie.tags, repositorie.imagen, repositorie.likes FROM users inner join repositories on users.idusers = repositories.idusers inner join repositorie on repositories.idrepositories = repositorie.idrepositories WHERE repositorie.tags like ?");
            ps.setString(1, searLikes);
            System.out.println("Reparando "+ps.toString());
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idusers = rs.getInt("idusers");
                int idrepositorie = rs.getInt("idrepositorie");
                String user = rs.getString("user");
                String namerepo = rs.getString("namerepo");
                String tags = rs.getString("tags");
                String imagen = rs.getString("imagen");
                int likes = rs.getInt("likes");
                
                DatoMural datoMural = new DatoMural(idusers,idrepositorie,user,namerepo,tags,imagen,likes);
                lista.add(datoMural);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println("line 394 "+e.toString());
            return null;
        }
    }
    
    public boolean eliminar (int _idrepositories, int _idusers){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("DELETE FROM repositories WHERE idrepositories=? AND idusers=?");
            ps.setInt(1, _idrepositories);
            ps.setInt(2, _idusers);
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        
    }
    
    public List<DataRepositorie> getListRepositorieByIdrepositories(int _id){
        
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        List<DataRepositorie> lista = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM repositorie WHERE idrepositories=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idrepositorie = rs.getInt("idrepositorie");
                int idrepositories = rs.getInt("idrepositories");
                String tags = rs.getString("tags");
                String imagen = rs.getString("imagen");
                
                DataRepositorie dataRepositorie = new DataRepositorie(idrepositorie,idrepositories,tags,imagen);
                lista.add(dataRepositorie);
            }
            
            return lista;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public DataRepositories getRepositoriesByIdrepositories(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        DataRepositories dataRepositories = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM repositories WHERE idrepositories=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("idrepositories");
                int idusers = rs.getInt("idusers");
                String namerepo = rs.getString("namerepo");
                
                dataRepositories = new DataRepositories(id,idusers,namerepo);
            }
            
            return dataRepositories;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public boolean insertarImagenEnRepositorie(DataRepositorie dataRepositorie){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("INSERT INTO repositorie ( idrepositories, tags, imagen ) VALUES (?,?,?)");
            ps.setInt(1, dataRepositorie.getIdrepositories());
            ps.setString(2, dataRepositorie.getTags());
            ps.setString(3, dataRepositorie.getImagen());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public boolean actualizarImagenEnRepositorie(DataRepositorie dataRepositorie){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("UPDATE repositorie SET idrepositories=?, tags=?, imagen=? WHERE idrepositorie=?");
            ps.setInt(1, dataRepositorie.getIdrepositories());
            ps.setString(2, dataRepositorie.getTags());
            ps.setString(3, dataRepositorie.getImagen());
            ps.setInt(4, dataRepositorie.getIdrepositorie());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public boolean eliminarImagenDeRepositorie(int _id){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        
        try {
            ps = conexion.prepareStatement("DELETE FROM repositorie WHERE idrepositorie=?");
            ps.setInt(1, _id);
            ps.execute();
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public DataRepositorie getRepositorieByIdrepositorie(int _id){
        
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;
        ResultSet rs;
        DataRepositorie dataRepositorie = null;
        try {
            ps = conexion.prepareStatement("SELECT * FROM repositorie WHERE idrepositorie=?");
            ps.setInt(1, _id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int idrepositorie = rs.getInt("idrepositorie");
                int idrepositories = rs.getInt("idrepositories");
                String tags = rs.getString("tags");
                String imagen = rs.getString("imagen");
                int likes = rs.getInt("likes");
                
                dataRepositorie = new DataRepositorie(idrepositorie,idrepositories,tags,imagen,likes);
            }
            
            return dataRepositorie;
        } catch(SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public boolean darLikes(int _id, int _likes){
        Connection conexion = establecerConexionDB();
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("UPDATE repositorie SET likes=? WHERE idrepositorie=?");
            ps.setInt(1, _likes);
            ps.setInt(2, _id);
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    
}
