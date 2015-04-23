/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import fr.emacarte.model.Utilisateur;
import fr.emacarte.servlet.ConnexionServlet;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author Alexandre
 */
public class CustomSession{
    
    private Session socketSession;
    private HttpSession httpSession;

    public CustomSession(Session socketSession, HttpSession httpSession) {
        this.socketSession = socketSession;
        this.httpSession = httpSession;
    }
    
    public Utilisateur getUtilisateur(){
        return (Utilisateur) httpSession.getAttribute(ConnexionServlet.ATT_SESSION_USER);
    }

    public String getEmail(){
        return getUtilisateur().getEmail();
    }
    
    public Session getSocketSession() {
        return socketSession;
    }

    public void setSocketSession(Session socketSession) {
        this.socketSession = socketSession;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    void update(CustomSession session) {
        this.socketSession = session.getSocketSession();
        this.httpSession = session.getHttpSession();
    }
    
    
    
}
