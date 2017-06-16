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
import model.Pokemon;
import model.Trainer;

/**
 *
 * @author USER
 */
@WebServlet(name = "NewPokemon", urlPatterns = {"/NewPokemon"})
public class NewPokemon extends HttpServlet {
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
            out.println("<title>Servlet NewPokemon</title>");            
            out.println("</head>");
            out.println("<body>");
            
              if ("Guardar".equals(request.getParameter("alta"))) {
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                String ability = request.getParameter("ability");
                int attack = Integer.parseInt(request.getParameter("attack"));
                int defense = Integer.parseInt(request.getParameter("defense"));
                int speed = Integer.parseInt(request.getParameter("speed"));
                int life = Integer.parseInt(request.getParameter("life"));
                int level = 0 ;
                
                Pokemon pokemon = new Pokemon(name, type, ability, attack, defense, speed, life, level);
                String nameTrainer = request.getParameter("nameTrainer");
                Trainer entrenadorEscogido = miEjb.getTrainerByName(nameTrainer);
                pokemon.setTrainer(entrenadorEscogido);
                
                if (miEjb.insertarPokemon(pokemon)) {
                    out.println("<p>Pokemon dado de alta</p>");
                } else {
                    out.println("<p>Ya existe ese pokemon, introduce uno distinto</p>");
                }
                out.println("<p><a href=\"index.html\">Volver al menú principal</a></p>");
            } else {
                out.println("<form method=\"POST\">");
                out.println("name: <input type=\"text\" name=\"name\">");
                out.println("type: <input type=\"text\" name=\"type\">");
                out.println("ability: <input type=\"text\" name=\"ability\">");
                out.println("attack: <input type=\"number\" name=\"attack\">");
                out.println("defense: <input type=\"number\" name=\"defense\">");
                out.println("speed: <input type=\"number\" name=\"speed\">");
                out.println("life: <input type=\"number\" name=\"life\">");
                out.println("nameTrainer: <select name=\"nameTrainer\">");
                // Leemos los conductores de la base de datos
                List<Trainer> trainers = miEjb.selectAllTrainers();
                for (Trainer t : trainers) {
                     if (t.getPokemonCollection().size()<6) {
                    out.println("<p><option value=\""+ t.getName() + "\">" + t.getName() + "</option></p>");
                }
                }
                out.println("</select>");
                out.println("<input type=\"submit\" name=\"alta\" value=\"Guardar\">");
                out.println("</form>");
            }
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
