/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import fr.emacarte.webApp.app.Tarot;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Alexandre
 */

@ApplicationScoped
@ServerEndpoint("/tarot/{room}")
public class TarotWSEndpoint {
    
    @Inject
    private static TarotSessionHandler sessionHandler;  

    @OnOpen
    public void onOpen(final Session session, @PathParam("room") String room) throws IOException {      
        session.getUserProperties().put("room", room);
        
        sendMessage("Connecté salle : " + room, session);
        sendMessage(session.toString(), session);
        sendMessage("Utilisateurs connectés : "  + session.getOpenSessions().size(), session);
    }
    
    @OnMessage
    public void onMessage(final Session session, String message) {       
        String room = (String) session.getUserProperties().get("room");
        for(Session s : session.getOpenSessions()){
            if(s.isOpen() && room.equals(s.getUserProperties().get("room"))){
                try {
                    sendMessage(message, session);
                } catch (IOException ex) {
                    Logger.getLogger(TarotWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }

    @OnClose
    public void onClose (Session session) {
        System.out.println("Un utilisateur est déconnecté");
        sessionHandler.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        
    }
    
    
    public void sendMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText(message);
    }
    
}
