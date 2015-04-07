/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import java.util.HashMap;
import javax.faces.bean.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author Alexandre
 */
@ApplicationScoped
public class TarotSessionHandler {
    
    private final HashMap<Session, String> sessions = new HashMap();
    public static HashMap<String, Salle> salles = new HashMap();
    
    public TarotSessionHandler(){
        
    }
    
    public static void createSalle(){
        Salle s = new Salle(String.valueOf(salles.size()+1));
        Thread t = new Thread(s);
        t.start();
        salles.put(String.valueOf(salles.size()+1), s);
     }
    
    public void addSession(Session session, String room){
        sessions.put(session, room);
        Salle s = salles.get(room);
        s.addPlayer(session);
        System.out.println("Session ajouté à la salle n°" + room + " totale: " + getNbrSession(room));
    }
    
    public void removeSession(Session session){        
        sessions.remove(session);
        String s = sessions.get(session);
        System.out.println("un joueur a quitté la salle n°" + s);
    }
    
    public int getNbrSession(){
        return sessions.size();
    }
    
    public int getNbrSession(String room){
        Salle s = salles.get(room);
        System.out.println("Peronnes dans la salle n°" + room + "=" + s.getSize());
        return s.getSize();
    }

}
