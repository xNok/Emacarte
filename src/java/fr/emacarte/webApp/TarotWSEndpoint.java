/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Alexandre
 */

@ApplicationScoped
@ServerEndpoint("/tarot")
public class TarotWSEndpoint {
    
    @Inject
    private TarotSessionHandler sessionHandler = new TarotSessionHandler();

    @OnMessage
    public String onMessage(String message) {
        System.out.println("Message : " + message);
        return "message reçu";
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Un utilisateur est connecté");
        sessionHandler.addSession(session);
        System.out.println("Il y a : "+ sessionHandler.getNbrSession() +" utilisateur(s) dans la salle");
    }

    @OnClose
    public void onClose (Session session) {
        System.out.println("Un utilisateur est déconnecté");
        sessionHandler.removeSession(session);
    }
    
}
