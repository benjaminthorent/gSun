package com.ei3info.gsun;

public class Temps {
	
	public int jour;
	public int mois;
	public int heure;
	
	public Temps(int jour, int mois, int heure) {
		super();
		this.jour = jour;
		this.mois = mois;
		this.heure = heure;
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getHeure() {
		return heure;
	}

	public void setHeure(int heure) {
		this.heure = heure;
	}
	
	public int getQuantiemeAnnee() {
		float jour_decimal = (float) ((this.mois - 1)*365.25/12 + this.jour);
		// round renvoie la partie entiere la plus proche
		int jour_entier = Math.round(jour_decimal);
		return jour_entier;
	}
	
	public double getDeclinaisonSolaire() {
		double arg = 0.986 * (this.getQuantiemeAnnee() + 284);
		double delta = 23.45 * Math.sin(arg);
		return delta;
	}
	
}
