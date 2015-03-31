/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import fr.emacarte.webApp.app.Joueur;
import fr.emacarte.webApp.app.Tarot;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author Alexandre
 */
class Salle implements Runnable{
    
    public ArrayList<Session> players = new ArrayList();
    public Tarot tarot;
    public String name;
    
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Salle.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("echo salle n°" + name);
        }
    }

    Salle(String name) {
        this.name = name;
        this.tarot = new Tarot();
    }

    public void addPlayer(Session session) {
        players.add(session);
        if(players.size() == 4){
            System.out.println("Il y a 4 joueurs dans la salle n°" + name);
            Joueur[] joueurs = tarot.getJoueurs();
            for (int i = 0; i < 4; i++) {
                joueurs[i].setId(players.get(i));
            }
        }
    }
    
    public void removePlayer(Session session) {
        players.remove(session);
    }
    
    public int getSize(){
        return players.size();
    }
    
    public ArrayList<Session> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Session> players) {
        this.players = players;
    }

    public Tarot getTarot() {
        return tarot;
    }

    public void setTarot(Tarot tarot) {
        this.tarot = tarot;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}
