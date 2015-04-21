package fr.emacarte.webApp.app;

import fr.emacarte.webApp.TarotWSEndpoint;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

public class Tarot{

    Pioche pioche;
    Joueur[] joueurs;
    int preneur;
    int appele;
    int annonce = 0;
    ArrayList<Carte> preneurs;
    ArrayList<Carte> opposants;
    boolean fin = false;
    
    public void broadcast(String message){
        System.out.println("broadcast : " + message);
        for (Joueur p:joueurs) {
            TarotWSEndpoint.sendChatMessage(message, p.getId());
        }
    }
    
    public void broadcastInfo(String message){
        System.out.println("broadcast : " + message);
        for (Joueur p:joueurs) {
            TarotWSEndpoint.sendAsyncMessage(message, p.getId());
        }
    }

    public Tarot() {
        pioche = new Pioche();
        joueurs = new Joueur[4];
        preneurs = new ArrayList<Carte>();
        opposants = new ArrayList<Carte>();
    }

    public void tourAnnonce(int prem) {
        System.out.println("Tarot : tourAnnonce");
        annonce = 0;
        for (int i = prem; i < prem + joueurs.length; i++) {
            int a = joueurs[i % joueurs.length].annoncer();
            if (a > annonce) {
                annonce = a;
                preneur = i % joueurs.length;
            }
        }
    }

    public void jouerManche(int manche) {
        System.out.println("Tarot : jouerManche");
        int excuse = 10;
        
        //netoyage
        opposants.clear();
        preneurs.clear();
        pioche.getChien().clear();
        //pioche.afficherPaquet();
        distribuer(joueurs, pioche, manche % 4);
        int prem = (manche + 1) % 4;
        
        //tour d'annonce
        tourAnnonce(prem);
        if (annonce != 0) {
            System.out.println("Tarot : jouerManche annonce!=0");
            //si ca marche on broadcast
            broadcast(joueurs[preneur].getId() + " prend une " + annonce(annonce) + ".");
            broadcast("Commençons la manche.");
            
            //trie de la main
            for (int i = prem; i < prem + 4; i++) {
                joueurs[i % 4].trierMain();
                if (joueurs[i % 4].getMain().get(17).getValeur() == 22) {
                    excuse = i % 4;
                }
            }
            if (annonce < 5) {
                if (annonce < 3) {
                    joueurs[preneur].prendreChien(pioche);
                }
                preneurs.addAll(pioche.getChien());
                pioche.getChien().clear();
            } else {
                opposants.addAll(pioche.getChien());
                pioche.getChien().clear();
            }
            int ouvre = prem;
            for (int i = 0; i < 17; i++) {
                int a = jouerLevee(ouvre, false);
                ouvre = a;
            }
            int alexcuse=jouerLevee(ouvre, true);
            int nbBouts = compterBouts(preneurs);
            float pointsFaits = compterPoints(preneurs);
            boolean preneurExcuse = true;
            for (int i = 0; i < opposants.size(); i++) {
                if (opposants.get(i).getValeur() == 22) {
                    preneurExcuse = false;
                }
            }
            if (excuse == preneur && preneurExcuse == false) {
                nbBouts += 1;
                pointsFaits += 4.5;
            }
            if (excuse != preneur && preneurExcuse == true) {
                nbBouts -= 1;
                pointsFaits -= 4.5;
            }
            for(int i=0;i<4;i++){
                if(i==alexcuse){
                    if(i==preneur){
                        preneurExcuse=false;
                    }else{
                        preneurExcuse=true;
                    }
                }
            }
            int pointsNecessaires = 56;
            switch (nbBouts) {
                case 1:
                    pointsNecessaires = 51;
                    break;
                case 2:
                    pointsNecessaires = 41;
                    break;
                case 3:
                    pointsNecessaires = 36;
                    break;
            }
            broadcast(joueurs[preneur].getId() + " a fait " + pointsFaits + " points pour " + pointsNecessaires + ".");

            joueurs[0].getMain().clear();
            joueurs[0].getMain().addAll(preneurs);
            joueurs[0].afficherMain();

            float base = 0;
            if (pointsFaits - pointsNecessaires >= 0) {
                base = (pointsFaits - pointsNecessaires + 25) * annonce;
            } else {
                base = (pointsFaits - pointsNecessaires - 25) * annonce;
            }
            for (int i = 0; i < 4; i++) {
                if (i == preneur) {
                    joueurs[i].setScore(joueurs[i].getScore() + 3 * base);
                } else {
                    joueurs[i].setScore(joueurs[i].getScore() - base);
                }
            }
            pioche.getPaquet().clear();
            pioche.getPaquet().addAll(opposants);
            pioche.getPaquet().addAll(preneurs);
            pioche.afficherPaquet();
        } else {
            broadcast("Tout le monde a passé.");
            pioche.getPaquet().clear();
            pioche.getPaquet().addAll(pioche.getChien());
            pioche.getChien().clear();
            for (int i = 0; i < 4; i++) {
                pioche.getPaquet().addAll(joueurs[i].getMain());
            }
            pioche.afficherPaquet();
        }
        for (int i = prem; i < prem + 4; i++) {
            joueurs[i % 4].afficherPoints();
            joueurs[i % 4].getMain().clear();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void afficherLevee(Carte cartePosee){
        //afficher la carte joué a tout les joueurs
        JsonObject jo = Json.createObjectBuilder()
            .add("action", Communication.afficherLevee)
            .add("carte", Json.createObjectBuilder()
                .add("couleur", cartePosee.getCouleur())
                .add("valeur", cartePosee.getValeur())
            )
        .build();
                
        broadcastInfo(jo.toString());
    }
    
    public int jouerLevee(int prem, boolean derniere) {
        ArrayList<Carte> levee = new ArrayList<Carte>();
        int excuse=10;
        int meilleur = prem;
        Carte ouverture = joueurs[prem].poserCarte();
        
        //affichage
        afficherLevee(ouverture);
        levee.add(ouverture);
        Carte meilleure = ouverture;
        
        if(ouverture.getValeur()==22){
            excuse=prem;
        }
        
        //boucle pour les autre joueurs
        for (int i = prem + 1; i < prem + 4; i++) {
            Carte suivante = joueurs[i % 4].poserCarte(ouverture, meilleure);
            
            //affichage
            afficherLevee(ouverture);
            
            levee.add(suivante);
            if(suivante.getValeur()==22){
                excuse=i%4;
            }
            if (suivante.getCouleur() == meilleure.getCouleur()
                    && suivante.getValeur() > meilleure.getValeur()
                    && suivante.getValeur() != 22) {
                meilleur = i % 4;
                meilleure = suivante;
            }
            if (ouverture.getValeur() == 22) {
                ouverture = suivante;
            }
            if (meilleure.getCouleur() != 0 && suivante.getCouleur() == 0) {
                meilleur = i % 4;
                meilleure = suivante;
            }
            if (meilleure.getValeur() == 22) {
                meilleur = i % 4;
                meilleure = suivante;
            }
        }
        if (meilleur == preneur) {
            preneurs.addAll(levee);
        } else {
            opposants.addAll(levee);
        }
        broadcast("-------------------------------------------------------------------");
        broadcast(joueurs[meilleur].getId() + " remporte la levée.");
        broadcast("-------------------------------------------------------------------");
        if(derniere==false){
            return meilleur;
        }else{
            return excuse;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void jouerPartie() {
        int manche = 1;
        while (fin == false) {
            broadcast("Manche : " + manche);
            
            jouerManche(manche);
            manche += 1;
        }
    }

    public String annonce(int a) {
        String an = "";
        switch (a) {
            case 1:
                an = "petite";
                break;
            case 2:
                an = "garde";
                break;
            case 4:
                an = "garde sans";
                break;
            case 6:
                an = "garde contre";
                break;
        }
        return an;
    }

    public void distribuer(Joueur[] joueurs, Pioche pioche, int dealer) {
        int nbchien = 0;
        int ch = 0;
        int j = 1;
        boolean chi = false;
        switch (joueurs.length) {
            case 3:
                nbchien = 6;
                break;
            case 4:
                nbchien = 6;
                break;
            case 5:
                nbchien = 3;
                break;
        }
        while (pioche.getPaquet().size() > 0) {
            if (chi == false) {
                joueurs[(dealer + j) % joueurs.length].getMain().add(
                        pioche.getPaquet().get(0));
                pioche.getPaquet().remove(0);
                joueurs[(dealer + j) % joueurs.length].getMain().add(
                        pioche.getPaquet().get(0));
                pioche.getPaquet().remove(0);
                joueurs[(dealer + j) % joueurs.length].getMain().add(
                        pioche.getPaquet().get(0));
                pioche.getPaquet().remove(0);
				// broadcast(3*j+" cartes");
                // pioche.afficherPaquet();
                // pioche.afficherChien();
                // joueurs[(dealer+j)%joueurs.length].afficherMain();
                if (ch < nbchien) {
                    chi = true;
                }
                j += 1;
            } else {
                pioche.getChien().add(pioche.getPaquet().get(0));
                pioche.getPaquet().remove(0);
                chi = false;
                ch += 1;
            }
        }
    }

    public float compterPoints(ArrayList<Carte> paquet) {
        float points = 0;
        for (int i = 0; i < paquet.size(); i++) {
            points += paquet.get(i).getPoint();
        }
        return points;
    }

    public int compterBouts(ArrayList<Carte> paquet) {
        int bouts = 0;
        for (int i = 0; i < paquet.size(); i++) {
            if (paquet.get(i).isBout()) {
                bouts += 1;
            }
        }
        return bouts;
    }

    public Pioche getPioche() {
        return pioche;
    }

    public void setPioche(Pioche pioche) {
        this.pioche = pioche;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    public int getPreneur() {
        return preneur;
    }

    public void setPreneur(int preneur) {
        this.preneur = preneur;
    }

    public int getAppele() {
        return appele;
    }

    public void setAppele(int appele) {
        this.appele = appele;
    }

    public ArrayList<Carte> getPreneurs() {
        return preneurs;
    }

    public void setPreneurs(ArrayList<Carte> preneurs) {
        this.preneurs = preneurs;
    }

    public ArrayList<Carte> getOpposants() {
        return opposants;
    }

    public void setOpposants(ArrayList<Carte> opposants) {
        this.opposants = opposants;
    }
}
