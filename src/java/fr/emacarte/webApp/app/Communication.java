/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp.app;

import java.util.ArrayList;

/**
 *
 * @author Olivier
 */
public class Communication {

    private String connexion;
    private String server;

    public String envoyerCarte(Carte carte) {
        String retour = "";
        retour += "{\"carte\":{\"couleur\":\"";
        retour += Integer.toString(carte.getCouleur());
        retour += "\",\"valeur\":\"";
        retour += Integer.toString(carte.getValeur());
        retour += "\"}}";
        return retour;
    }

    public String envoyerMain(ArrayList<Carte> paquet) {
        String retour = "";
        retour += "{\"main\":{";
        for (int i = 0; i < paquet.size(); i++) {
            if (i != 0) {
                retour += ",";
            }
            retour += "\"carte";
            retour += "" + (i + 1);
            retour += "\":{\"couleur\":\"";
            retour += Integer.toString(paquet.get(i).getCouleur());
            retour += "\",\"valeur\":\"";
            retour += Integer.toString(paquet.get(i).getValeur());
            retour += "\"}";
        }
        retour += "}}";
        return retour;
    }
    
    public String envoyerChien(ArrayList<Carte> paquet) {
        String retour = "";
        retour += "{\"chien\":{";
        for (int i = 0; i < paquet.size(); i++) {
            if (i != 0) {
                retour += ",";
            }
            retour += "\"carte";
            retour += "" + (i + 1);
            retour += "\":{\"couleur\":\"";
            retour += Integer.toString(paquet.get(i).getCouleur());
            retour += "\",\"valeur\":\"";
            retour += Integer.toString(paquet.get(i).getValeur());
            retour += "\"}";
        }
        retour += "}}";
        return retour;
    }

    public String envoyerScore(int score) {
        String retour = "";
        retour += "{\"score\":\"" + Integer.toString(score) + "\"}";
        return retour;
    }

    public String envoyerAnnonce(int annonce) {
        String retour = "";
        retour += "{\"annonce\":\"" + Integer.toString(annonce) + "\"}";
        return retour;
    }
    
    public String resultat(int resultat){
        String retour = "";
        retour += "{\"resultat\":\"" + Integer.toString(resultat) + "\"}";
        return retour;
    }
    
    public String preneur(Joueur[] joueurs, int preneur){
        String retour = "";
        retour += "{\"preneur\":\"" + joueurs[preneur].getId() + "\"}";
        return retour;
    }
}