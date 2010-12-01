package com.ei3info.gsun;

public class Calculs {
	private PositionUtilisateur position;
	private Temps date;
	
	
	
	public Calculs(PositionUtilisateur position, Temps date) {
		this.position = position;
		this.date = date;
	}
	

	public double EquationTemps(){
		//anomalie moyenne en degrčs
		double M = (357 + 0.9856 * date.getQuantiemeAnnee())*Math.PI/180;
		//equation du centre
		double C = 1.914 * Math.sin(M) + 0.02 * Math.sin(2*M);
		//Longitude vraie du Soleil en degrčs
		double L = (280 + C + 0.9856 * date.getQuantiemeAnnee())*Math.PI/180 ;
		//Reduction ŕ l'équateur
		double R = -2.465 * Math.sin(2*L) + 0.053 * Math.sin(4*L);
		//Equation du temps en minutes
		double ET = (C + R) * 4;
		//conversion en heures
		ET /= 60;
		return ET;
	}
	
	public double parametreLeverCoucher(){
		//correspond au HO du pdf théorique
		double H1 = (-0.01454 - Math.sin(date.getDeclinaisonSolaire()*Math.PI/180) * Math.sin(position.getLatitude()*Math.PI/180)) / (Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.cos(position.getLatitude()*Math.PI/180));
		double H0 =0;
		if (H1 < -1 || H1 > 1){
			H0 = -2;
			System.out.println("c'est n'importe quoi ce truc");
		} else {
			H0 = Math.acos(H1)*180/Math.PI;
		}
		// conversion en heure décimale
		H0 /= 15;
		return H0;
	}
	
	public double tempsSolaireVrai(){
		//temps solaire moyen
		double TSM = (double)(date.getHeure() + position.getLongitude()/15);
		//temps solaire vrai
		double TSV = TSM + this.EquationTemps();
		return TSV;
	}
	
	public double angleHoraire(){
		//test du TP énergétique 
		//double TSV = 15;
		double AH = 15*(this.tempsSolaireVrai() - 12);
		return AH;
	}
	
	public double getAzimut(){
		double num = Math.sin(position.getLatitude()*Math.PI/180) * Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.cos(this.angleHoraire()*Math.PI/180) - Math.cos(position.getLatitude()*Math.PI/180) * Math.sin(date.getDeclinaisonSolaire()*Math.PI/180);
		double cosA = (num)/(Math.cos(this.getHauteurSolaire()*Math.PI/180));
		double sinA = (Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.sin(this.angleHoraire()*Math.PI/180)) / Math.cos(this.getHauteurSolaire()*Math.PI/180);
		double Azimut = 0;
		if (cosA >=0){
			Azimut = Math.asin(sinA)*180/Math.PI;
		} else{
			Azimut = (Math.PI - Math.asin(sinA)) * 180/Math.PI;
		}
		return Azimut;
	}
	
	public double getHauteurSolaire(){
		double sinH = Math.sin(position.getLatitude()*Math.PI/180) * Math.sin(date.getDeclinaisonSolaire()*Math.PI/180) + Math.cos(position.getLatitude()*Math.PI/180) * Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.cos(this.angleHoraire()*Math.PI/180);
		return Math.asin(sinH) * 180/Math.PI;
	}
	
	//attention ŕ la longitude.... diviser par 15 avant le calcul...
	
	public double offsetHeure(double heure){
		double offset = heure;
		if (date.mois == 7 || date.mois == 8 || date.mois == 6 && date.jour >=21 || date.mois == 9 && date.jour <=21){
			offset += 1;
		} else if (date.mois == 1 || date.mois == 2 || date.mois == 12 && date.jour >=21 || date.mois == 3 && date.jour <=21){
			offset += 2;
		}
		
		return offset;
	}
	
	public double getHeureLever(){
		//heure vraie
		double VL = 12 - this.parametreLeverCoucher();
		// longitude en heures
		double longitude = position.getLongitude()/15;
		//heure UTC
		double TL = VL + this.EquationTemps() + longitude;
		// heure légale
		
		// + 1 heure en hiver, + 2 heures en été
		double HL = offsetHeure(TL);
		return HL;
	}
	
	public double getHeureCoucher(){
		//heure vraie
		double VC = 12 + this.parametreLeverCoucher();
		// longitude en heures
		double longitude = position.getLongitude()/15;
		//heure UTC
		double TC = VC + this.EquationTemps() + longitude;
		// heure légale
		// + position.getFuseau(); //attention il manque + 1 haure en été
		double HC = offsetHeure(TC);
		return HC;	}
	
	
	

}

