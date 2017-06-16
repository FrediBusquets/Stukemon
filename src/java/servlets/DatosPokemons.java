/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.StukemonEJB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pokemon;

/**
 *
 * @author USER
 */
@WebServlet(name = "DatosPokemons", urlPatterns = {"/DatosPokemons"})
public class DatosPokemons extends HttpServlet {
 @EJB
    StukemonEJB miEjb;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatosPokemons</title>");            
            out.println("</head>");
            out.println("<body>");

            
//            E sta opción deberá mostrar el li stado con
//todos los datos de los pokemon en una tabla, ordenada de mayor a
//menor por nivel y puntos de vida. E s decir , ordenado primero por
//nivel y en caso de empate por pun tos de vida de mayor a menor . 
            
            
           out.println("<table border='1'>");
           out.println("<tr>");
           out.println("<th> Nombre </th>");
           out.println("<th> Tipo </th>");
           out.println("<th> Habilidad </th> ");
           out.println("<th> Ataque </th>");
           out.println("<th> Defensa </th>");
           out.println("<th> Velocidad </th>");
           out.println("<th> Vida </th>");
           out.println("<th> Nivel </th>");
           out.println("<th> Entrenador </th>");
           out.println("</tr>");
            for (Pokemon pokemon : miEjb.selectAllPokemonsOrder()) {
                
            out.println("<tr>");
                out.println("<td>"+pokemon.getName()+"</td>");  
                out.println("<td>"+pokemon.getType()+"</td>"); 
                out.println("<td>"+pokemon.getAbility()+"</td>"); 
                out.println("<td>"+pokemon.getAttack()+"</td>"); 
                out.println("<td>"+pokemon.getDefense()+"</td>"); 
                out.println("<td>"+pokemon.getSpeed()+"</td>"); 
                out.println("<td>"+pokemon.getLife()+"</td>"); 
                out.println("<td>"+pokemon.getLevel()+"</td>"); 
                out.println("<td>"+pokemon.getTrainer().getName()+"</td>"); 
            out.println("<tr>");                  
                    }
           out.println("</table> ");      
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

}
