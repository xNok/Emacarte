/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import fr.emacarte.webApp.app.Communication;
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
    
    //Clefs utilisable pour le champ action
    public static final String[] keys = {"chat", "carteDepose", "annonce"};
    
    @Inject
    private static final TarotSessionHandler sessionHandler = new TarotSessionHandler();  

    @OnOpen
    public void onOpen(final Session session, @PathParam("room") String room) throws IOException {      
        session.getUserProperties().put("room", room);
        
        sendChatMessage("Connecté salle : " + room, session);
        sendChatMessage(session.toString(), session);
        sendChatMessage("Utilisateurs connectés à la plate-forme: "  + (session.getOpenSessions().size()+1), session);
        sendChatMessage("Utilisateurs connectés à la salle: "  + (sessionHandler.getNbrSession(room)+1), session);
        
        //initialisation des Clefs
        for(String key : keys){
            session.getUserProperties().put(key, "");
        }
        
        sessionHandler.addSession(session, room);
    }
    
    @OnMessage
    public void onMessage(final Session session, String message) {       
        String room = (String) session.getUserProperties().get("room");
        System.out.println("in : " + message);
                
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject jsonMessage = reader.readObject();
        
        String action = jsonMessage.getString("action");
        System.out.println("action :" + action);
        
        if ("chat".equals(action)) {
            System.out.println("action validated : chat");
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
        }else if("jouerCarte".equals(action)){
            System.out.println("action validated : jouerCarte");
            //les informations sont communiqué au joueur via la session
            String valeur = jsonMessage.getString("valeur");
            session.getUserProperties().put(Communication.carteDepose, valeur);
        }else if("annonce".equals(action)){
            System.out.println("action validated : annonce");
            String valeur = jsonMessage.getString("valeur");
            session.getUserProperties().put(Communication.annonce, valeur);
        } 
    }

    @OnClose
    public void onClose (Session session) {
        String room = (String) session.getUserProperties().get("room");
        System.out.println("Un utilisateur est déconnecté");
        sessionHandler.removeSession(session, room);
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
        System.out.println("out : " + message);
        session.getBasicRemote().sendText(message);
    }
    
    /**
     * Envoyer un message asynchrone à la session
     * @param message
     * @param session 
     */
    public static void sendAsyncMessage(String message, Session session){
        System.out.println("out : " + message);
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
