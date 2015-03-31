/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.servlet;

import fr.emacarte.webApp.TarotSessionHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Gestion des partie -> création, rejoindre, fin de partie
 * 
 * @author Alexandre
 */
public class GameServlet extends HttpServlet {
    
    public static String VUE = "/WEB-INF/game/homeGame.jsp" ;
    

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
        
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
    
    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        TarotSessionHandler.createSalle();
        response.sendRedirect("/Emacarte/jeux/salle_de_jeux?num=" + TarotSessionHandler.salles.size());
    }
    
    private void join(HttpServletRequest request, HttpServletResponse response){
        
    }
    
    private void salle(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("salle", request.getParameter("num"));
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
        VUE = "/WEB-INF/game/homeGame.jsp";
        
        //gestion des URI
        System.out.println("GET");
        String URI = request.getRequestURI();
        String[] s = URI.split("/");
        if(s.length > 3){ //Uri longue => option différente
            System.out.println(s[3]);
            switch (s[3]){
                case "create": 
                    VUE = "/WEB-INF/game/createGame.jsp";
                 break;
                case "join":
                    VUE = "/WEB-INF/game/joinGame.jsp";
                 break;
                case "salle_de_jeux" :
                    VUE = "/WEB-INF/game/salleGame.jsp";
                    salle(request, response);
                 break;
                default : 
                    VUE = "/WEB-INF/game/homeGame.jsp";
             } 
        }else{ //uri courte traitement normal
            request.setAttribute("salles", TarotSessionHandler.salles.keySet().toArray()); 
        }
        

        
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
        
        boolean create = false;
        
        String URI = request.getRequestURI();
        String[] s = URI.split("/");
        if(s.length > 3){
            System.out.println(s[3]);
            switch (s[3]){
                case "create": 
                    create(request, response);
                    create = true;
                 break;
                case "join":
                    VUE = "/WEB-INF/game/joinGame.jsp";
                    join(request, response);
                 break;
                default : VUE = "/WEB-INF/game/homeGame.jsp";
             } 
        }
        
        if(!create){
            processRequest(request, response);
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

}
