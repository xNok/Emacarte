/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author Alexandre
 */
@ApplicationScoped
public class TarotSessionHandler {
    
    private final Set sessions = new HashSet<>();
    
    public void addSession(Session session){
        sessions.add(session);
    }
    
    public void removeSession(Session session){
        sessions.remove(session);
    }
    
    public int getNbrSession(){
        return sessions.size();
    }
}
