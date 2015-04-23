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
    
    public ArrayList<CustomSession> players = new ArrayList();
    public Tarot tarot;
    public String name;
    
    public boolean run = true;
    public boolean lancer = true;
    
    @Override
    public void run() {
        while(run){
            try {
                Thread.sleep(1000);
                if(players.size() == 4 && lancer){
                    System.out.println("Il y a 4 joueurs dans la salle n°" + name);
                    lancer = false;
                    lancerPartie();

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Salle.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("echo salle n°" + name + " ;" + Thread.currentThread().toString());
        }
    }

    Salle(String name) {
        this.name = name;
        this.tarot = new Tarot();
    }

    public void addPlayer(CustomSession session) {
        players.add(session);
        TarotWSEndpoint.sendAsyncMessage("Bienvenue", session.getSocketSession());

    }
    
    public void lancerPartie(){       
        Joueur[] joueurs = tarot.getJoueurs();
        for (int i = 0; i < 4; i++) {
            CustomSession p = players.get(i);
            TarotWSEndpoint.sendChatMessage("Nous sommes 4 la partie commence", p.getSocketSession());
            System.out.println(p);
            joueurs[i] = new Joueur(p);
        }
 
        tarot.jouerPartie();
    }
    
    public void removePlayer(Session session) {
        players.remove(session);
    }
    
    public int getSize(){
        return players.size();
    }
    
    public ArrayList<CustomSession> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<CustomSession> players) {
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
