/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.StukemonEJB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Trainer;

/**
 *
 * @author USER
 */
@WebServlet(name = "ConseguirPociones", urlPatterns = {"/ConseguirPociones"})
public class ConseguirPociones extends HttpServlet {
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
            out.println("<title>Servlet ConseguirPociones</title>");            
            out.println("</head>");
            out.println("<body>");
             
            
                if ("Comprar".equals(request.getParameter("adquirirPociones"))) {
               
                String trainer = request.getParameter("trainer1");
                int nPociones = Integer.parseInt(request.getParameter("nPociones"));
                Trainer entrenador = miEjb.getTrainerByName(trainer);
                     if(nPociones*10>entrenador.getPoints()){
                          out.println("No tienes suficientes puntos");
                           out.println("<p><a href=\"index.html\">Volver al menú principal</a></p>");
                      }else{
                     entrenador.setPoints(entrenador.getPoints()-(nPociones*10));
                     entrenador.setPotions(entrenador.getPotions()+nPociones);
                     miEjb.modificarPociones(entrenador);
                     miEjb.modificarPuntos(entrenador);
                     out.println("La compra ha sido realizada, al entrenador le quedan: "+entrenador.getPoints()+" puntos y tiene: "+entrenador.getPotions()+"pociones");
                       out.println("<p><a href=\"index.html\">Volver al menú principal</a></p>");
                        }
                     
                  }else{
                out.println("<form method=\"POST\">");
                out.println("Escoge el entrenador y también las pociones que quieres comprar: ");
                out.println("Trainer: <select name=\"trainer1\">");
                List<Trainer> trainer = miEjb.selectAllTrainers();
                for (Trainer t : trainer) {
                    if (t.getPokemonCollection().size() > 0) {
                        out.println("<p><option value=\"" + t.getName() + "\">" + t.getName() + "</option></p>");
                    }else{
                        out.println("No hay entrenadores que tengan pokemons");
                    }         
                }
               
               out.println(": <input type=\"int\" name=\"nPociones\">");                       
               out.println("<input type=\"submit\" name=\"adquirirPociones\" value=\"Comprar\">");               
               out.println("</select>");
               out.println("</form>");
               
            }
            
            
            
            
            out.println("</form>");
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
