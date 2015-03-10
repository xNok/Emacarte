package fr.emacarte.webApp.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
	private String id;
	private ArrayList<Carte> main;
	private float score;
	private Scanner input;

	public Joueur(String i) {
		id = i;
		score = 0;
		main = new ArrayList<Carte>();
	}

	public void afficherMain() {
		System.out.println("Main de " + id + " :");
		for (int i = 0; i < main.size(); i++) {
			main.get(i).afficherCarteDansTas();
		}
		System.out.println("");
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
		System.out.println("Veuillez poser une carte.");
		input = new Scanner(System.in);
		int rang = input.nextInt();// int rang=(int)(Math.random()*main.size());
		Carte cartePosee = main.get(rang);
		main.remove(rang);
		cartePosee.afficherCarte();
		return cartePosee;
	}
	
	public void afficherPoints(){
		System.out.println(id+", vous avez "+score+" points.");
	}

	public int annoncer() {
		afficherMain();
		System.out.println("A vous de parler " + id + " :");
		input = new Scanner(System.in);
		return input.nextInt();// 0 pour rien 1 pour petite, 2 pour garde, 4
								// pour garde sans et 6 pour garde contre
	}

	public void prendreChien(Pioche pioche){
		pioche.afficherChien();
		main.addAll(pioche.getChien());
		trierMain();
		pioche.getChien().clear();
		for(int i = 0; i<6; i++){
			afficherMain();
			System.out.println("Carte "+(i+1)+" dans le chien :");
			input = new Scanner(System.in);
			int rang = input.nextInt();
			pioche.getChien().add(main.get(rang));
			main.remove(rang);
		}
	}
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Scanner getInput() {
		return input;
	}

	public void setInput(Scanner input) {
		this.input = input;
	}

}