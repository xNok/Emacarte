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
    public String onMessage(String message, Session session) throws IOException {       
        sendMessage(message, session);
        return "message recu : " + message;
    }
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
        sendMessage("Connecté", session);
    }

    @OnClose
    public void onClose (Session session) {
        System.out.println("Un utilisateur est déconnecté");
        sessionHandler.removeSession(session);
    }
    
    
    public void sendMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText(message);
    }
    
}
