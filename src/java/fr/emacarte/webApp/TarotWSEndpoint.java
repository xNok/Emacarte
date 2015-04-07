/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
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
    private static final TarotSessionHandler sessionHandler = new TarotSessionHandler();  

    @OnOpen
    public void onOpen(final Session session, @PathParam("room") String room) throws IOException {      
        session.getUserProperties().put("room", room);
        
        sendMessage("Connecté salle : " + room, session);
        sendMessage(session.toString(), session);
        sendMessage("Utilisateurs connectés à la plate-forme: "  + (session.getOpenSessions().size()+1), session);
        sendMessage("Utilisateurs connectés à la salle: "  + (sessionHandler.getNbrSession(room)+1), session);
        
        session.getUserProperties().put("message", "");
        sessionHandler.addSession(session, room);
    }
    
    @OnMessage
    public void onMessage(final Session session, String message) {       
        String room = (String) session.getUserProperties().get("room");
        System.out.println(message);
        session.getUserProperties().put("message", message);
                
        
//        System.out.println("Nbr sessions :" + sessionHandler.getNbrSession(room));
//        for(Session s : session.getOpenSessions()){
//            if(s.isOpen() && room.equals(s.getUserProperties().get("room"))){
//                try {
//                    if(!s.equals(session)){
//                        sendMessage(message, s);
//                    }     
//                } catch (IOException ex) {
//                    Logger.getLogger(TarotWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        } 
    }

    @OnClose
    public void onClose (Session session) {
        String room = (String) session.getUserProperties().get("room");
        System.out.println("Un utilisateur est déconnecté");
        sessionHandler.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        
    }
    
    public static void sendMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText(message);
    }
    
    public static void sendAsyncMessage(String message, Session session){
        session.getAsyncRemote().sendText(message);
    }
    
}
