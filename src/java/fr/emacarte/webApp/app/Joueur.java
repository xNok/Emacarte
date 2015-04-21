package fr.emacarte.webApp.app;

import fr.emacarte.webApp.TarotWSEndpoint;
import static fr.emacarte.webApp.app.Communication.envoyerMain;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

public class Joueur {
	private Session id;
	private ArrayList<Carte> main;
	private float score;
	private Communication com;

	public Joueur(Session i) {
		id = i;
		score = 0;
		main = new ArrayList<Carte>();
                com = new Communication();
	}

	public void afficherMain() {
		TarotWSEndpoint.sendChatMessage("Votre main est prête :",id, "red");
                TarotWSEndpoint.sendAsyncMessage(com.envoyerMain(main), id);
	}
        
        public String getMessage(){
            System.out.println("GetMessage");
            String message = "";
            while(message.isEmpty()){
//                    TarotWSEndpoint.sendChatMessage("echo annonce ;" + Thread.currentThread().toString());
//                    try {                            
//                        Thread.sleep(500);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                 message = (String) id.getUserProperties().get("message");
            }
            id.getUserProperties().put("message", "");
            
            System.out.println("end : getMessage");
            return message;
        }
        
        public String getMessage(String key){
            System.out.println("GetMessage " + key );
            String message = "";
            while(message.equals("")){
//                    TarotWSEndpoint.sendChatMessage("echo annonce ;" + Thread.currentThread().toString());
//                    try {                            
//                        Thread.sleep(500);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
//                    }
  
                 message = (String) id.getUserProperties().get(key);
            }
            id.getUserProperties().put(key, "");
            
            System.out.println("end : getMessage " + key);
            return message;
        }
        
	public void trierMain() {
		ArrayList<Carte> mainTriee = new ArrayList<Carte>();
		ArrayList<Carte> coul1 = new ArrayList<Carte>();
		ArrayList<Carte> coul2 = new ArrayList<Carte>();
		ArrayList<Carte> coul3 = new ArrayList<Carte>();
		ArrayList<Carte> coul4 = new ArrayList<Carte>();
		ArrayList<Carte> atout = new ArrayList<Carte>();
		for (int i = 0; i < main.size(); i++) {
			if (main.get(i).getCouleur() == 0) {
				atout.add(main.get(i));
			}
			if (main.get(i).getCouleur() == 1) {
				coul1.add(main.get(i));
			}
			if (main.get(i).getCouleur() == 2) {
				coul2.add(main.get(i));
			}
			if (main.get(i).getCouleur() == 3) {
				coul3.add(main.get(i));
			}
			if (main.get(i).getCouleur() == 4) {
				coul4.add(main.get(i));
			}
		}
		trierParValeur(atout);
		trierParValeur(coul1);
		trierParValeur(coul2);
		trierParValeur(coul3);
		trierParValeur(coul4);
		mainTriee.addAll(coul1);
		mainTriee.addAll(coul2);
		mainTriee.addAll(coul3);
		mainTriee.addAll(coul4);
		mainTriee.addAll(atout);
		main = mainTriee;
	}

	public void trierParValeur(ArrayList<Carte> paq) {
		ArrayList<Carte> paqTri = new ArrayList<Carte>();
		int m = paq.size();
		for (int i = 0; i < m; i++) {
			int v = 23;
			int k=0;
			for (int j = 0; j < paq.size(); j++) {
				if (paq.get(j).getValeur() < v) {
					v=paq.get(j).getValeur();
					k = j;
				}
			}
			paqTri.add(paq.get(k));
			paq.remove(k);
		}
		paq.clear();
		paq.addAll(paqTri);
	}

	public Carte poserCarte() {
		afficherMain();
		TarotWSEndpoint.sendChatMessage("Veuillez poser une carte.",id, "red");
                
                //ecoute
		int rang=com.entreeCarte(this);
		Carte cartePosee = main.get(rang);
		main.remove(rang);
		cartePosee.afficherCarte();
                
                
                
		return cartePosee;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        public Carte poserCarte(Carte demande, Carte meilleure){
            afficherMain();
		TarotWSEndpoint.sendChatMessage("Veuillez poser une carte.",id, "red");
		int rang = com.entreeCarte(this);
		Carte cartePosee = main.get(rang);
                while(verifierCarte(demande,meilleure,cartePosee)==false){
                    TarotWSEndpoint.sendChatMessage("Vous ne pouvez pas poser cette carte, choisissez-en une autre !",id, "orange");
                    rang=com.entreeCarte(this);
                    cartePosee = main.get(rang);
                }
		main.remove(rang);
		cartePosee.afficherCarte();
		return cartePosee;
        }
	
        public boolean verifierCarte(Carte demande, Carte meilleure, Carte choisie){
            boolean ok=true;
            int coul=demande.getCouleur();
            int val=demande.getValeur();
            if(choisie.getCouleur()!=coul){
                if(choisie.getCouleur()!=22){
                    if(coul==0){
                        if(possedeCouleur(0)){
                            ok=false;
                        }
                    }else{
                        if(possedeCouleur(coul)){
                            ok=false;
                        }
                        if(choisie.getCouleur()!=0){
                            if(possedeCouleur(0)){
                                ok=false;
                            }
                        }
                    }
                }
            }
            if(choisie.getCouleur()==0){
                if(meilleure.getCouleur()==0){
                    if(meilleure.getValeur()>choisie.getValeur()){
                        if(possedePlusGrandAtout(meilleure.getValeur())){
                            ok=false;
                        }
                    }
                }
            }
            if(meilleure.getValeur()==22){
                ok=true;
            }
            if(choisie.getValeur()==22){
                ok=true;
            }
            return ok;
        }
        
        public boolean possedePlusGrandAtout(int valeur){
            boolean ok=false;
            for(int i=0; i<main.size();i++){
                if(main.get(i).getCouleur()==0){
                    if(main.get(i).getValeur()>valeur&&main.get(i).getValeur()!=22){
                        ok=true;
                    }
                }
            }
            return ok;
        }
        
        public boolean possedeCouleur(int couleur){
            boolean ok=false;
             for(int i=0; i<main.size();i++){
                 if(main.get(i).getCouleur()==couleur&&main.get(i).getValeur()!=22){
                     ok=true;
                 }
             }
             return ok;
        } 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
	public void afficherPoints(){
		TarotWSEndpoint.sendChatMessage(id+", vous avez "+score+" points.",id);
	}

	public int annoncer() {
		afficherMain();
		TarotWSEndpoint.sendChatMessage("A vous de parler !", id);
                
                String json = "{"
                    + "\"action\": \"annonce\""
                    + "}"
                ;
                TarotWSEndpoint.sendAsyncMessage(json, id);
                
                int annonce = com.entreeAnnonce(this);

                //return com.entreeAnnonce();// 0 pour rien 1 pour petite, 2 pour garde, 4
								// pour garde sans et 6 pour garde contre
                
                return annonce;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void prendreChien(Pioche pioche){
		pioche.afficherChien();
		main.addAll(pioche.getChien());
		trierMain();
		pioche.getChien().clear();
		for(int i = 0; i<6; i++){
			afficherMain();
			TarotWSEndpoint.sendChatMessage("Carte "+(i+1)+" dans le chien :",id);
			int rang = com.entreeCarte(this);
                        Carte choisie=main.get(rang);
                        while(choisie.getCouleur()==0&&(possedeCouleur(1)||possedeCouleur(2)||possedeCouleur(3)||possedeCouleur(4))){
                            TarotWSEndpoint.sendChatMessage("Pas d'atout dans le chien",id);
                            rang = com.entreeCarte(this);
                            choisie=main.get(rang);
                        }
			pioche.getChien().add(choisie);
			main.remove(rang);
		}
                TarotWSEndpoint.sendChatMessage("Le chien est fait !",id);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * public void distribuer(Joueur[] joueurs, Pioche pioche, int dealer){ int
	 * nbchien=0; int ch=0; int j=1; boolean chi=false; switch(joueurs.length){
	 * case 3 : nbchien=6; break; case 4 : nbchien=6; break; case 5 : nbchien=3;
	 * break; } while(pioche.getPaquet().size()>0){ if(chi=false){
	 * joueurs[(dealer
	 * +j)%joueurs.length].getMain().add(pioche.getPaquet().get(0));
	 * pioche.getPaquet().remove(0);
	 * joueurs[(dealer+j)%joueurs.length].getMain()
	 * .add(pioche.getPaquet().get(0)); pioche.getPaquet().remove(0);
	 * joueurs[(dealer
	 * +j)%joueurs.length].getMain().add(pioche.getPaquet().get(0));
	 * pioche.getPaquet().remove(0); if(ch<nbchien){ chi=true; } j+=1; }else{
	 * pioche.getChien().add(pioche.getPaquet().get(0));
	 * pioche.getPaquet().remove(0); chi=false; } } }
	 */

	public Session getId() {
		return id;
	}

	public void setId(Session id) {
		this.id = id;
	}

	public ArrayList<Carte> getMain() {
		return main;
	}

	public void setMain(ArrayList<Carte> main) {
		this.main = main;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float f) {
		this.score = f;
	}

}