/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Prueba;
import modelo.PruebaDAO;

/**
 *
 * @author Rafael Bastidas
 */
@WebServlet(name = "PruebaController", urlPatterns = {"/PruebaController"})
public class PruebaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PruebaDAO pruebaDAO = new PruebaDAO();
        String accion;
        RequestDispatcher dispatcher = null;
        
        accion = request.getParameter("accion");
        
        if(accion == null || accion.isEmpty()){
            dispatcher = request.getRequestDispatcher("Prueba/index.jsp");
            List<Prueba> listaprueba = pruebaDAO.listarPrueba();
            request.setAttribute("lista", listaprueba);
        }
        
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
