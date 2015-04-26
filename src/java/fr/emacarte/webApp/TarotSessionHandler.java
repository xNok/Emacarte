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
    
    //email -- session
    public static HashMap<String, CustomSession> utilisateurs = new HashMap();
    //session -- salle
    public static HashMap<CustomSession, String> sessions = new HashMap();
    //numéros ou id -- Salle
    public static HashMap<String, Salle> salles = new HashMap();
    
    public TarotSessionHandler(){
        
    }
    
    public static void createSalle(){
        Salle s = new Salle(String.valueOf(salles.size()+1));
        Thread t = new Thread(s);
        t.start();
        salles.put(String.valueOf(salles.size()+1), s);
     }
    
    public void addSession(CustomSession session,String room){
        //TODO
        //le joueur à actualisr la page -- la web socket session a changé
        if(utilisateurs.containsKey(session.getEmail())){
            CustomSession oldsession = utilisateurs.get(session.getEmail());
            sessions.remove(oldsession);
            utilisateurs.get(session.getEmail()).update(session);
            System.out.println("Session mise à jours");
        }else{
            Salle s = salles.get(room);
            s.addPlayer(session); 
            System.out.println("Session ajouté à la salle n°" + room + " totale: " + getNbrSession(room));
        }  
        
        utilisateurs.put(session.getEmail(), session);
    }
    
    public void removeSession(Session session, String room){        
        sessions.remove(session);
        String se = sessions.get(session);
        Salle s = salles.get(room);
        s.removePlayer(session);
        System.out.println("un joueur a quitté la salle n°" + se);
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
