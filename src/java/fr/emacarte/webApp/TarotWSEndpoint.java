/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
        
        sendChatMessage("Connecté salle : " + room, session);
        sendChatMessage(session.toString(), session);
        sendChatMessage("Utilisateurs connectés à la plate-forme: "  + (session.getOpenSessions().size()+1), session);
        sendChatMessage("Utilisateurs connectés à la salle: "  + (sessionHandler.getNbrSession(room)+1), session);
        
        session.getUserProperties().put("message", "");
        sessionHandler.addSession(session, room);
    }
    
    @OnMessage
    public void onMessage(final Session session, String message) {       
        String room = (String) session.getUserProperties().get("room");
        System.out.println(message);
                
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject jsonMessage = reader.readObject();
        
        String action = jsonMessage.getString("action");
        System.out.println(action);
        
        if ("chat".equals(action)) {
            //envoyer le message à tout les utilisateur de la salle
            for(Session s : session.getOpenSessions()){
                if(s.isOpen() && room.equals(s.getUserProperties().get("room"))){
                    try {
                        if(!s.equals(session)){
                            sendMessage(message, s);
                        }     
                    } catch (IOException ex) {
                        Logger.getLogger(TarotWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } 
        }else if("tarot".equals(action)){
            //les informations sont communiqué au joueur via la session
            session.getUserProperties().put("message", message);
        }  
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
    
    /**
     * Envoyer un message synchrone à la session
     * @param message
     * @param session
     * @throws IOException 
     */
    public static void sendMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText(message);
    }
    
    /**
     * Envoyer un message asynchrone à la session
     * @param message
     * @param session 
     */
    public static void sendAsyncMessage(String message, Session session){
        System.out.println(message);
        session.getAsyncRemote().sendText(message);
    }
    
    /**
     * Construction des message pour le chat
     * @param message
     * @param session
     * @param color compréhensible pour une propiétée css
     */
    public static void sendChatMessage(String message, Session session, String color){
        String json = "{"
           + "\"action\": \"chat\","
           + "\"message\": \""+ message + "\","
           + "\"color\": \""+ color + "\""
           + "}"
        ;
        
        sendAsyncMessage(json, session);
    }
    
    /**
     * Construction des message pour le chat sans précisser la couleur
     * @param message
     * @param session
     * color = blue
     */
    public static void sendChatMessage(String message, Session session){
        sendChatMessage(message, session, "blue");
    }
}
