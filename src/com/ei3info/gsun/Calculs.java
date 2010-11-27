package com.ei3info.gsun;

public class Calculs {
	
	private PositionUtilisateur position;
	private Temps date;
	
	public Calculs(PositionUtilisateur position, Temps date) {
		this.position = position;
		this.date = date;
	}

	public double EquationTemps() {
		// anomalie moyenne en degrés
		double M = 357 + 0.9856 * date.getQuantiemeAnnee();
		// équation du centre
		double C = 1.914 * Math.sin(M) + 0.02 * Math.sin(2*M);
		// Longitude vraie du soleil en degrés
		double L = 280 + C + 0.9856 * date.getQuantiemeAnnee();
		// Réduction à l'équateur
		double R = -2.465 * Math.sin(2*L) + 0.053 * Math.sin(4*L);
		// Equation du temps en minutes
		double ET = (C + R) * 4;
		// conversion en heures
		ET /= 60;
		return ET;
	}
	
	public double parametreLeverCoucher() {
		//correspond au H0 cité dans l'étude théorique
		double H1 = (-0.01454 - Math.sin(date.getDeclinaisonSolaire()) * Math.sin(position.getLatitude())) / (Math.cos(date.getDeclinaisonSolaire()) * Math.cos(position.getLatitude()));
		double H0 =0;
		if (H1 < -1 || H1 > 1) {
			H0 = -2;
			System.out.println("incorrect");
		} else {
			H0 = Math.acos(H1);
		}
		// conversion en heure décimale
		H0 /= 15;
		return H0;
	}
	
	public double angleHoraire() {
		// temps solaire moyen
		double TSM = (double)(date.getHeure() + position.getLongitude()/15);
		// temps solaire vrai
		double TSV = TSM + this.EquationTemps();
		// angle horaire
		double AH = 15*(TSV - 12);
		return AH;
	}
	
	public double getAzimut() {
		double num = Math.sin(position.getLatitude()) * Math.cos(date.getDeclinaisonSolaire()) * Math.cos(this.angleHoraire()) - Math.cos(position.getLatitude()) * Math.sin(date.getDeclinaisonSolaire());
		double cosA = (num)/(Math.cos(this.getHauteurSolaire()));
		double sinA = (Math.cos(date.getDeclinaisonSolaire()) * Math.sin(this.angleHoraire())) / Math.cos(this.getHauteurSolaire());
		double Azimut = 0;
		if (cosA >=0) {
			Azimut = Math.asin(sinA);
		} else {
			Azimut = Math.PI - Math.asin(sinA);
		}
		return Azimut;
	}
	
	public double getHauteurSolaire() {
		double sinH = Math.sin(position.getLatitude()) * Math.sin(date.getDeclinaisonSolaire()) + Math.cos(position.getLatitude()) * Math.cos(date.getDeclinaisonSolaire()) * Math.cos(this.angleHoraire());
		return Math.asin(sinH);
	}
	
	// attention à la longitude.... diviser par 15 avant le calcul...
	
	public double getHeureLever() {
		// heure vraie
		double VL = 12 - this.parametreLeverCoucher();
		// longitude en heures
		double longitude = position.getLongitude()/15;
		// heure UTC
		double TL = VL + this.EquationTemps() + longitude;
		// heure légale
		double HL = TL + position.getFuseau(); //attention il manque + 1 heure en été
		return HL;
	}
	
	public double getHeureCoucher() {
		// heure vraie
		double VC = 12 + this.parametreLeverCoucher();
		// longitude en heures
		double longitude = position.getLongitude()/15;
		// heure UTC
		double TC = VC + this.EquationTemps() + longitude;
		// heure légale
		double HC = TC + position.getFuseau(); //attention il manque + 1 heure en été
		return HC;
	}

}
