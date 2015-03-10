package fr.emacarte.webApp.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Tarot implements Runnable{
	Pioche pioche;
	Joueur[] joueurs;
	int preneur;
	int appele;
	int annonce = 0;
	ArrayList<Carte> preneurs;
	ArrayList<Carte> opposants;
	Scanner input;
	boolean fin=false;
        

        public void run() {
            jouerPartie();
        }

	public Tarot() {
		pioche = new Pioche();
		input = new Scanner(System.in);
		joueurs = new Joueur[4];
		for (int i = 0; i < 4; i++) {
			System.out.println("Id du joueur " + (i + 1) + " ?");
			input = new Scanner(System.in);
			joueurs[i] = new Joueur(input.nextLine());
		}
		preneurs = new ArrayList<Carte>();
		opposants = new ArrayList<Carte>();
	}

	public void tourAnnonce(int prem) {
		annonce=0;
		for (int i = prem; i < prem + joueurs.length; i++) {
			int a = joueurs[i % joueurs.length].annoncer();
			if (a > annonce) {
				annonce = a;
				preneur = i % joueurs.length;
			}
		}
	}

	public void jouerManche(int manche) {
		int excuse=10;
		opposants.clear();
		preneurs.clear();
		pioche.getChien().clear();
		//pioche.afficherPaquet();
		distribuer(joueurs, pioche, manche % 4);
		int prem = (manche + 1) % 4;
		tourAnnonce(prem);
		if (annonce != 0) {
                    System.out.println(joueurs[preneur].getId()+" prend une "+annonce(annonce)+".");
                    System.out.println("");
			for (int i = prem; i < prem + 4; i++) {
				joueurs[i % 4].trierMain();
				if(joueurs[i%4].getMain().get(17).getValeur()==22){
					excuse=i%4;
				}
			}
			if(annonce<5){
				if(annonce<3){
					joueurs[preneur].prendreChien(pioche);
				}
				preneurs.addAll(pioche.getChien());
				pioche.getChien().clear();
			}else{
				opposants.addAll(pioche.getChien());
				pioche.getChien().clear();
			}
			int ouvre=prem;
			for (int i = 0; i < 18; i++) {
				int a=jouerLevee(ouvre);
				ouvre=a;
			}
			int nbBouts = compterBouts(preneurs);
			float pointsFaits = compterPoints(preneurs);
			boolean preneurExcuse=true;
			for(int i=0; i<opposants.size();i++){
				if(opposants.get(i).getValeur()==22){
					preneurExcuse=false;
				}
			}
			if(excuse==preneur&&preneurExcuse==false){
				nbBouts+=1;
				pointsFaits+=4.5;
			}
			if(excuse!=preneur&&preneurExcuse==true){
				nbBouts-=1;
				pointsFaits-=4.5;
			}
			int pointsNecessaires = 56;
			switch(nbBouts){
			case 1 : pointsNecessaires=51;
			break;
			case 2 : pointsNecessaires=41;
			break;
			case 3 : pointsNecessaires=36;
			break;
			}
			System.out.println(joueurs[preneur].getId()+" a fait "+pointsFaits+" points pour "+pointsNecessaires+".");
			
			
			joueurs[0].getMain().clear();
			joueurs[0].getMain().addAll(preneurs);
			joueurs[0].afficherMain();
			
			
			float base=0;
			if(pointsFaits-pointsNecessaires>=0){
				base=(pointsFaits-pointsNecessaires+25)*annonce;
			}else{
				base=(pointsFaits-pointsNecessaires-25)*annonce;
			}
			for(int i=0;i<4;i++){
				if(i==preneur){
					joueurs[i].setScore(joueurs[i].getScore()+3*base);
				}else{
					joueurs[i].setScore(joueurs[i].getScore()-base);
				}
			}
			pioche.getPaquet().clear();
			pioche.getPaquet().addAll(opposants);
			pioche.getPaquet().addAll(preneurs);
			pioche.afficherPaquet();
		}else{
			System.out.println("Tout le monde a passé.");
			pioche.getPaquet().clear();
			pioche.getPaquet().addAll(pioche.getChien());
			pioche.getChien().clear();
			for(int i = 0; i<4; i++){
				pioche.getPaquet().addAll(joueurs[i].getMain());
			}
			pioche.afficherPaquet();
		}
		for (int i = prem; i < prem + 4; i++) {
			joueurs[i % 4].afficherPoints();
			joueurs[i%4].getMain().clear();
		}
	}

	public int jouerLevee(int prem) {
		ArrayList<Carte> levee = new ArrayList<Carte>();
		int meilleur = prem;
		Carte ouverture = joueurs[prem].poserCarte();
		levee.add(ouverture);
		int valmeil = ouverture.getValeur();
		int coulmeil = ouverture.getCouleur();
		for (int i = prem + 1; i < prem + 4; i++) {
			Carte suivante = joueurs[i % 4].poserCarte();
			levee.add(suivante);
			if (suivante.getCouleur() == coulmeil
					&& suivante.getValeur() > valmeil
					&& suivante.getValeur() != 22) {
				meilleur = i % 4;
				valmeil = suivante.getValeur();
			}
			if (coulmeil != 0 && suivante.getCouleur() == 0) {
				meilleur = i % 4;
				valmeil = suivante.getValeur();
				coulmeil=0;
			}
			if(valmeil==22){
				meilleur=i%4;
				valmeil=suivante.getValeur();
				coulmeil=suivante.getCouleur();
			}
		}
		if (meilleur == preneur) {
			preneurs.addAll(levee);
		} else {
			opposants.addAll(levee);
		}
		System.out.println(joueurs[meilleur].getId()+" remporte la levée.");
		System.out.println("-------------------------------------------------------------------");
		return meilleur;
	}

	public void jouerPartie() {
		int manche=1;
		while(fin==false){
			System.out.println("");
			System.out.println("Manche "+manche+" :");
			jouerManche(manche);
			manche+=1;
		}
	}
        
        public String annonce(int a){
            String an="";
            switch(a){
                case 1 : an="petite";
                    break;
                case 2 : an="garde";
                    break;
                case 4 : an="garde sans";
                    break;
                case 6 : an="garde contre";
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
				// System.out.println(3*j+" cartes");
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
	
	public int compterBouts(ArrayList<Carte> paquet){
		int bouts = 0;
		for (int i = 0; i < paquet.size(); i++) {
			if(paquet.get(i).isBout()){
				bouts+=1;
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
