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
@WebServlet(name = "AddLife", urlPatterns = {"/AddLife"})
public class AddLife extends HttpServlet {

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
            out.println("<title>Servlet AddLife</title>");
            out.println("</head>");
            out.println("<body>");

            if ("Continuar".equals(request.getParameter("escogerTrainer"))) {
                String trainer = request.getParameter("trainer1");
                Trainer entrenador = miEjb.getTrainerByName(trainer);

                if (entrenador.getPotions() >= 1) {
                    out.println("Escoge el pokemon que vas a curar con la pocion que has gastado: ");
                    entrenador.setPotions(entrenador.getPotions() - 1);
                    miEjb.modificarPociones(entrenador);
                    out.println("<form method=\"POST\">");
                     out.println("Pociones restantes: "+entrenador.getPotions());
                    out.println("Pokemons de " + trainer + ": <select name=\"pokemon1\">");
                    for (Pokemon p1 : entrenador.getPokemonCollection()) {
                        out.println("<p><option value=\"" + p1.getName() + "\">" + p1.getName() + "</option></p>");
                    }
                   
                    out.println("</select>");
                    out.println("<input type=\"submit\" name=\"usarPocion\" value=\"Usar Pocion\">");
                    out.println("</form>");

                } else {
                    out.println("No tienes suficientes puntos");
                    out.println("<p><a href=\"index.html\">Volver al menu principal</a></p>");
                }
            }else if ("Usar Pocion".equals(request.getParameter("usarPocion"))) {
                String pokemon = request.getParameter("pokemon1");
                Pokemon pt = miEjb.getPokemonByName(pokemon);
                if (miEjb.addVida(pt)) {
                    out.println(" La vida que tiene ahora el pokemon " + pt.getName() + " es: " + (pt.getLife() + 50));
                }                
                out.println("<p><a href=\"index.html\">Volver al menu principal</a></p>");
            } else {
                String trainer = request.getParameter("trainer1");
                out.println("Escoge el entrenador que vas a usar: ");;
                out.println("<form method=\"POST\">");
                out.println(" ");
                out.println("Trainer1: <select name=\"trainer1\">");
                List<Trainer> trainer1 = miEjb.selectAllTrainers();
                for (Trainer t : trainer1) {
                    if (t.getPokemonCollection().size() > 0) {
                        out.println("<p><option value=\"" + t.getName() + "\">" + t.getName() + "</option></p>");
                    } else {
                        out.println("No hay entrenadores que tengan pokemons");
                        out.println("<p><a href=\"index.html\">Volver al menu principal</a></p>");
                    }
                }
                out.println("</select>");
                out.println("<input type=\"hidden\" name=\"trainer1\" value=\"" + trainer + "\">");
                out.println("<input type=\"hidden\" name=\"trainer2\" value=\"" + trainer + "\">");
                out.println("<input type=\"submit\" name=\"escogerTrainer\" value=\"Continuar\">");

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
