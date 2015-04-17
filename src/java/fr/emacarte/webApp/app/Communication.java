/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp.app;

import fr.emacarte.webApp.TarotWSEndpoint;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Olivier
 */
public class Communication {
    
    //correspondance action Java -> action JavaScript
    public static final String envoyerMain = "afficherMain";
    public static final String carteDepose = "carteDepose";
    public static final String annonce = "annonce";

    private String connexion;
    private String server;

    public Communication() {
        connexion = "";
        server = "";
    }

    public String envoyerCarte(Carte carte) {
        String retour = "";
        retour += "{\"carte\":{\"couleur\":\"";
        retour += Integer.toString(carte.getCouleur());
        retour += "\",\"valeur\":\"";
        retour += Integer.toString(carte.getValeur());
        retour += "\"}}";
        return retour;
    }

    /**
     * Encoyer la main au format Json
     * @param paquet
     * @return Main to Json
     */
    public String envoyerMain(ArrayList<Carte> paquet) {
        
        JsonArrayBuilder main = Json.createArrayBuilder();
        for (int i = 0; i < paquet.size(); i++) {
            main.add(Json.createObjectBuilder()
                .add("idcarte",i)    
                .add("couleur", paquet.get(i).getCouleur())
                .add("valeur", paquet.get(i).getValeur())
            );
        }
        
        JsonObject jo = Json.createObjectBuilder()
            .add("action", envoyerMain)
            .add("main", main)
        .build();
        
        return jo.toString();
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

    public String resultat(int resultat) {
        String retour = "";
        retour += "{\"resultat\":\"" + Integer.toString(resultat) + "\"}";
        return retour;
    }

    public String preneur(Joueur[] joueurs, int preneur) {
        String retour = "";
        retour += "{\"preneur\":\"" + joueurs[preneur].getId() + "\"}";
        return retour;
    }

    public int entreeCarte(Joueur joueur) {
        System.out.println("Com : entrer cartes");
        int l = joueur.getMain().size();
        boolean ok = false;
        int retour = 0;
        boolean err = false;
        while (ok == false) {
            if (err == true) {
                TarotWSEndpoint.sendAsyncMessage("entrez un entier possible, merci !", joueur.getId());
            }
            err = true;
            String message = joueur.getMessage(carteDepose);
            int val = Integer.parseInt(message);
            ok = true;
            
            if (val >= 0 && val < l) {
                ok = true;
                retour = val;
            }
        }

        return retour;
    }

    public int entreeAnnonce(Joueur joueur) {
        System.out.println("Com : entrer annonce");
        boolean ok = false;
        int retour = 0;
        boolean err = false;
        while (ok == false) {
            if (err == true) {
                TarotWSEndpoint.sendChatMessage("entrez un entier possible, merci !", joueur.getId(), "orange");
            }
            
            //on récupère l'annonce
            err = true;
            String message = joueur.getMessage(annonce);
            
            int val = Integer.parseInt(message);
                if (val == 0 || val == 1 || val == 2 || val == 4 || val == 6) {
                    ok = true;
                    retour = val;
                }
        }
        return retour;
    }
}
