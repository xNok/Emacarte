package fr.emacarte.webApp.app;
import java.util.ArrayList;
import java.util.Collections;

/**
 *Classe contenant les 78 cartes nÃƒÂ©cessaires au jeu de tarot
 * @author emacarte
 */
public class Pioche {
	private ArrayList<Carte> paquet;
	private ArrayList<Carte> chien;
	
	 /**
     * Constructeur Initialise le paquet avec les 78 cartes
     */
	public Pioche(){
		paquet = new ArrayList<Carte>();
            for (int i = 1; i < 23; i++) 
            {
                paquet.add(new Carte(0,i));
            }
            for (int i = 1; i<5; i++){
                    for(int j = 1; j<15; j++){
                            paquet.add(new Carte(i,j));
                    }
            }
            Collections.shuffle(paquet);
            chien = new ArrayList<Carte>();
	}
	
	/**
     * Affiche le paquet dans son intégralité Utile uniquement pour les phases
     * de test
     */
    public void afficherPaquet() {
    	for (int i = 0; i < paquet.size(); i++) {
            paquet.get(i).afficherCarteDansTas();
	}
    }
    
    public void afficherChien() {
    	for (int i = 0; i < chien.size(); i++) {
            chien.get(i).afficherCarteDansTas();
	}
    }
    
	
	public ArrayList<Carte> piocher(int nb){
		ArrayList<Carte> retour =new ArrayList();
		for(int i = 0; i<nb; i++){
			retour.add(paquet.get(0));
			paquet.remove(0);
		}
		return retour;
	}

	  /**
     * Réinitialise et mélange le paquet.
     */
    public void reinitialiserPioche(ArrayList<Carte> paq1, ArrayList<Carte> paq2) {
    	paquet.clear();
    	paquet.addAll(paq1);
    	paquet.addAll(paq2);
    }
    
	public ArrayList<Carte> getPaquet() {
		return paquet;
	}

	public void setPaquet(ArrayList<Carte> paquet) {
		this.paquet = paquet;
	}
	
	public ArrayList<Carte> getChien() {
		return chien;
	}

	public void setChien(ArrayList<Carte> chien) {
		this.chien = paquet;
	}
}
