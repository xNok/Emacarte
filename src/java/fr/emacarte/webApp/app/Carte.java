package fr.emacarte.webApp.app;

/**
 * Cartes de Tarot
 *
 * contient une valeur et une couleur les couleurs sont coeur pour 1, pique pour
 * 2, carreau pour 3, trÃƒÂ¨fle pour 4, atout pour 5
 *
 * @author Olivier
 */

public class Carte {
	private int couleur; // entre 0 et 4 (0 pour les atouts)
	private int valeur; // de 1 Ã  14 ou 22
	private String imCouleur;
        private String imValeur;
	private float point = 0.5f; // nombre de points que rapporte la carte
	private boolean bout = false;

	/**
	 * Constructeur
	 *
	 * @param valeur
	 * @param couleur
	 */
	public Carte(int c, int v) {
		couleur = c;
		valeur = v;
		if (couleur == 0) {
			imCouleur="A";
		}
		if (couleur == 1) {
			imCouleur = "♠";
		}
		if (couleur == 2) {
			imCouleur = "♥";
		}
		if (couleur == 3) {
			imCouleur = "♣";
		}
		if (couleur == 4) {
			imCouleur = "♦";
		}
                
                if(couleur>0){
                    if(valeur<11){
                        imValeur=""+valeur;
                    }
                    if(valeur==11){
                        imValeur="V";
                    }
                    if(valeur==12){
                        imValeur="C";
                    }
                    if(valeur==13){
                        imValeur="D";
                    }
                    if(valeur==14){
                        imValeur="R";
                    }
                    if(valeur==1){
                        imValeur="A";
                    }
                }else{
                    if(valeur==22){
                        imValeur="E";
                    }else{
                        imValeur=""+valeur;
                    }
                }
		if (couleur == 0) {
			if (valeur == 1 || valeur == 21 || valeur == 22) {
				bout = true;
				point = 4.5f;
			}
		} else {
			if (valeur == 11) {
				point = 1.5f;
			}
			if (valeur == 12) {
				point = 2.5f;
			}
			if (valeur == 13) {
				point = 3.5f;
			}
			if (valeur == 14) {
				point = 4.5f;
			}
		}
	}

	/**
	 * donne le nom de cette carte sous forme de chaine de caractÃƒÂ¨res
	 *
	 * @return le nom
	 */
	public String toString() {
		if ((valeur == 1 && couleur == 5)) {
			return "le Petit";
		} else if ((valeur == 22 && couleur == 5)) {
			return "l' Excuse";
		} else if ((valeur == 11 || valeur == 12 || valeur == 13 || valeur == 14)
				&& couleur == 5) {
			return valeur + " d'Atout";
		} else {
			String retour = "";
			switch (valeur) {
			case 1:
				retour += "As";
				break;
			case 11:
				retour += "Valet";
				break;
			case 12:
				retour += "Cavalier";
				break;
			case 13:
				retour += "Dame";
				break;
			case 14:
				retour += "Roi";
				break;
			default:
				retour += valeur;
			}
			retour += " ";
			switch (couleur) {
			case 1:
				retour += "de Coeur";
				break;
			case 2:
				retour += "de Pique";
				break;
			case 3:
				retour += "de Carreau";
				break;
			case 4:
				retour += "de TrÃ¨fle";
				break;
			case 5:
				retour += "d'Atout";
				break;
			}
			return retour;
		}
	}

	public void afficherCarte() {
		afficherCarteDansTas();
		System.out.println("");
	}
	
	public void afficherCarteDansTas(){
		String coul="";
		if(couleur==0){
			coul="\u001B[34m";
		}
		if(couleur==2||couleur==4){
			coul="\u001B[31m";
		}
		if(couleur==1||couleur==3){
			coul="\u001B[30m";
		}
		if(couleur!=0){
			System.out.print(coul+"("+imCouleur+","+imValeur+") "+"\u001B[0m");
		}else{
			System.out.print(coul+"("+imValeur+") "+"\u001B[0m");
		}
	}

	public int getCouleur() {
		return couleur;
	}

	public String getImCouleur() {
		return imCouleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public boolean isBout() {
		return bout;
	}

	public void setBout(boolean bout) {
		this.bout = bout;
	}

}