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
import model.Battle;
import model.Pokemon;
import model.Trainer;

/**
 *
 * @author USER
 */
@WebServlet(name = "Fight", urlPatterns = {"/Fight"})
public class Fight extends HttpServlet {

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
            out.println("<title>Servlet Fight</title>");
            out.println("</head>");
            out.println("<body>");

            if ("Continuar".equals(request.getParameter("escogerTrainer"))) {
                String nTrainer1 = request.getParameter("trainer1");
                String nTrainer2 = request.getParameter("trainer2");
                Trainer entrenador1 = miEjb.getTrainerByName(nTrainer1);
                Trainer entrenador2 = miEjb.getTrainerByName(nTrainer2);
                if (entrenador1.equals(entrenador2)) {
                    out.println("Error: El entrenador no puede pelear contra si mismo");
                    out.println("<p><a href=\"index.html\">Volver al menu principal</a></p>");
                } else {
                    out.println("<form method=\"POST\">");
                    out.println("Pokemons de " + nTrainer1 + ": <select name=\"pokemon1\">");
                    for (Pokemon p1 : entrenador1.getPokemonCollection()) {
                        out.println("<p><option value=\"" + p1.getName() + "\">" + p1.getName() + "</option></p>");

                    }
                    out.println("</select>");
                    out.println("Pokemons de " + nTrainer2 + ": <select name=\"pokemon2\">");
                    for (Pokemon p2 : entrenador2.getPokemonCollection()) {
                        out.println("<p><option value=\"" + p2.getName() + "\">" + p2.getName() + "</option></p>");

                    }
                    out.println("</form>");
                    out.println("</select>");
                    out.println("<input type=\"hidden\" name=\"trainer1\" value=\"" + nTrainer1 + "\">");
                    out.println("<input type=\"hidden\" name=\"trainer2\" value=\"" + nTrainer2 + "\">");
                    out.println("<input type=\"submit\" name=\"escogerPokemons\" value=\"Fight\">");
                }

            } else if ("Fight".equals(request.getParameter("escogerPokemons"))) {

                out.println("Comineza la batalla: ");

                String nTrainer1 = request.getParameter("trainer1");
                String nTrainer2 = request.getParameter("trainer2");
                Trainer entrenador1 = miEjb.getTrainerByName(nTrainer1);
                Trainer entrenador2 = miEjb.getTrainerByName(nTrainer2);

                String p1 = request.getParameter("pokemon1");
                String p2 = request.getParameter("pokemon2");
                Pokemon pt1 = miEjb.getPokemonByName(p1);
                Pokemon pt2 = miEjb.getPokemonByName(p2);

                int fuerzaAtaquept1 = pt1.getAttack() + (2 * pt1.getLevel()) - (pt2.getDefense() / 3);
                int fuerzaAtaquept2 = pt2.getAttack() + (2 * pt2.getLevel()) - (pt1.getDefense() / 3);
                int Lifept1 = pt1.getLife() - fuerzaAtaquept2;
                int Lifept2 = pt2.getLife() - fuerzaAtaquept1;

                if (pt1.getSpeed() > pt2.getSpeed()) {
                    out.println("El primer pokemon en atacar será: " + pt1.getName());
                    if (fuerzaAtaquept1 < pt2.getLife()) {
                        pt2.setLife(Lifept2);
                        if (miEjb.modificarVidaP(pt2)) {
                            out.println("El pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                        }
                        if(fuerzaAtaquept2 < pt1.getLife()){
                            pt1.setLife(Lifept1);
                            if (miEjb.modificarVidaP(pt1)) {
                                out.println("Ahora atacará el pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                            }}else{
                            pt1.setLife(0);
                            if (miEjb.modificarVidaP(pt1)) {
                            out.println("Ahora atacará el pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");   
                            }
                            }
                    } else {
                        pt2.setLife(0);
                        if (miEjb.modificarVidaP(pt2)) {
                            out.println("El pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                        }
                    }
                } else if (pt1.getSpeed() < pt2.getSpeed()) {
                    out.println("El primer pokemon en atacar será: " + pt2.getName());
                    if (fuerzaAtaquept2 < pt1.getLife()) {
                        pt1.setLife(Lifept1);
                        if (miEjb.modificarVidaP(pt1)) {
                            out.println("El pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                        }
                        if(fuerzaAtaquept1 < pt2.getLife()){
                            pt2.setLife(Lifept2);
                            if (miEjb.modificarVidaP(pt2)) {
                                out.println("Ahora atacará el pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                            }}else{
                            pt2.setLife(0);
                            if (miEjb.modificarVidaP(pt2)) {
                            out.println("Ahora atacará el pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");   
                            }
                            }
                    } else {
                        pt1.setLife(0);
                        if (miEjb.modificarVidaP(pt1)) {
                            out.println("El pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                        }
                    }
                } else {
                    int r = (int) Math.random() * 10 / 2;
                    if (r > 0) {
                        out.println("El primer pokemon en atacar será: " + pt1.getName());
                        if (fuerzaAtaquept1 < pt2.getLife()) {
                            pt2.setLife(Lifept2);
                            if (miEjb.modificarVidaP(pt2)) {
                                out.println("El pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                            }
                            if(fuerzaAtaquept2 < pt1.getLife()){
                            pt1.setLife(Lifept1);
                            if (miEjb.modificarVidaP(pt1)) {
                                out.println("Ahora atacará el pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                            }}else{
                            pt1.setLife(0);
                            if (miEjb.modificarVidaP(pt1)) {
                            out.println("Ahora atacará el pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");   
                            }
                            }
                        } else {
                            pt2.setLife(0);
                            if (miEjb.modificarVidaP(pt2)) {
                                out.println("El pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                            }
                        }
                    } else {
                        out.println("El primer pokemon en atacar será: " + pt2.getName());
                        if (fuerzaAtaquept2 < pt1.getLife()) {
                            pt1.setLife(Lifept1);
                            if (miEjb.modificarVidaP(pt1)) {
                                out.println("El pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                            }
                            if(fuerzaAtaquept1 < pt2.getLife()){
                            pt2.setLife(Lifept2);
                            if (miEjb.modificarVidaP(pt2)) {
                                out.println("Ahora atacará el pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");
                            }}else{
                            pt2.setLife(0);
                            if (miEjb.modificarVidaP(pt2)) {
                            out.println("Ahora atacará el pokemon: " + pt1.getName() + " Ataca al pokemon: " + pt2.getName() + " dejandolo con: " + pt2.getLife() + " de vida. ");   
                            }
                            }
                        } else {
                            pt1.setLife(0);
                            if (miEjb.modificarVidaP(pt1)) {
                                out.println("El pokemon: " + pt2.getName() + " Ataca al pokemon: " + pt1.getName() + " dejandolo con: " + pt1.getLife() + " de vida. ");
                            }
                        }
                    }
                }
                Battle battle = new Battle();
                battle.setPokemon1(pt1);
                battle.setPokemon2(pt2);
                if (pt1.getLife() > pt2.getLife()) {
                    entrenador1.setPoints(entrenador1.getPoints() + 10);
                    battle.setWinner(pt1);
                    miEjb.insertarBatalla(battle);
                    if (miEjb.modificarPuntos(entrenador1)) {
                        out.println(" Ha Ganado el entrenador: " + entrenador1.getName() + ". Recibe 10 puntos. Puntos Actuales del entrenador: " + entrenador1.getPoints() + ". ");
                    }
                    pt1.setLevel(pt1.getLevel() + 1);
                    miEjb.modificarLevel(pt1);
                    if (miEjb.modificarLevel(pt1)) {
                        out.println(" El pokemon: " + pt1.getName() + " sube un nivel. Nivel Actual del pokemon: " + pt1.getLevel());
                    }
                } else if (pt1.getLife() < pt2.getLife()) {
                    battle.setWinner(pt2);
                    miEjb.insertarBatalla(battle);
                    entrenador2.setPoints(entrenador2.getPoints() + 10);
                    if (miEjb.modificarPuntos(entrenador2)) {
                        out.println(" Ha Ganado el entrenador: " + entrenador2.getName() + ". Recibe 10 puntos. Puntos Actuales del entrenador: " + entrenador2.getPoints() + ". ");
                        pt2.setLevel(pt2.getLevel() + 1);
                        miEjb.modificarLevel(pt2);
                        if (miEjb.modificarLevel(pt2)) {
                            out.println(" El pokemon: " + pt2.getName() + " sube un nivel. Nivel Actual del pokemon: " + pt2.getLevel());
                        }
                    }
                    miEjb.insertarBatalla(battle);
                } else {
                    out.println("Ha sido un empate por lo que esta batalla se declara nula");
                }
                out.println("<p><a href=\"index.html\">Volver al menu principal</a></p>");
            } else {
                out.println("Escoge a los entrenadores que se van a enfrentar: ");
                out.println("<form method=\"POST\">");
                out.println(" ");
                out.println("Trainer1: <select name=\"trainer1\">");
                List<Trainer> trainer1 = miEjb.selectAllTrainers();
                for (Trainer t : trainer1) {
                    if (t.getPokemonCollection().size() > 0) {
                        out.println("<p><option value=\"" + t.getName() + "\">" + t.getName() + "</option></p>");
                    }
                }
                out.println("</select>");
                out.println("Trainer2: <select name=\"trainer2\">");
                List<Trainer> trainer2 = miEjb.selectAllTrainers();
                for (Trainer t : trainer2) {
                    if (t.getPokemonCollection().size() > 0) {
                        out.println("<p><option value=\"" + t.getName() + "\">" + t.getName() + "</option></p>");
                    }
                }
                out.println("</select>");
                out.println("<input type=\"submit\" name=\"escogerTrainer\" value=\"Continuar\">");
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
