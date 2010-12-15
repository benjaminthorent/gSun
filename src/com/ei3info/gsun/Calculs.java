package com.ei3info.gsun;

public class Calculs {
	
	//class which allows to provide Solar Azimuth and Solar Height
	//depending on the user position and the date
	private PositionUtilisateur position;
	private Temps date;
	
	
	//constructor with fields
	public Calculs(PositionUtilisateur position, Temps date) {
		this.position = position;
		this.date = date;
	}
	
	//Return the time difference (in hour) between a civil day (24) and the solar day duration (variable
	//according to the specific day
	public double EquationTemps(){
		//average anomaly in degrees
		double M = (357 + 0.9856 * date.getQuantiemeAnnee())*Math.PI/180;
		//center equation
		double C = 1.914 * Math.sin(M) + 0.02 * Math.sin(2*M);
		// True Solar longitude in degrees
		double L = (280 + C + 0.9856 * date.getQuantiemeAnnee())*Math.PI/180 ;
		//Reduction to the equator
		double R = -2.465 * Math.sin(2*L) + 0.053 * Math.sin(4*L);
		//Time equation in minutes 
		double ET = (C + R) * 4;
		//Translation into hours
		ET /= 60;
		return ET;
	}
	
	// it is a parameter necessary to compute the rise and the fall of the sun 
	public double parametreLeverCoucher(){
		double H1 = (-0.01454 - Math.sin(date.getDeclinaisonSolaire()*Math.PI/180) * Math.sin(position.getLatitude()*Math.PI/180)) / (Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.cos(position.getLatitude()*Math.PI/180));
		double H0 =0;
		if (H1 < -1 || H1 > 1){
			H0 = -2;
			System.out.println("c'est n'importe quoi ce truc");
		} else {
			H0 = Math.acos(H1)*180/Math.PI;
		}
		// Translation into hours
		H0 /= 15;
		return H0;
	}
	
	// Returns the Solar time, necessary to compute the Azimuth and the Height
	public double tempsSolaireVrai(){
		//temps solaire moyen
		double TSM = (double)(date.getHeure() + position.getLongitude()/15);
		//temps solaire vrai
		double TSV = TSM - this.EquationTemps() - this.fuseau() - majorationHoraire();
		
		return TSV;
	}
	
	
	// Returns the hour angle
	public double angleHoraire(){
		double AH = 15*(this.tempsSolaireVrai() - 12);
		return AH;
	}
	
	// Returns the Solar Azimuth
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
	
	// Returns the Solar Height 
	public double getHauteurSolaire(){
		double sinH = Math.sin(position.getLatitude()*Math.PI/180) * Math.sin(date.getDeclinaisonSolaire()*Math.PI/180) + Math.cos(position.getLatitude()*Math.PI/180) * Math.cos(date.getDeclinaisonSolaire()*Math.PI/180) * Math.cos(this.angleHoraire()*Math.PI/180);
		return Math.asin(sinH) * 180/Math.PI;
	}
	
	
	// Add an hour or two according to the period ( winter or summer), from Solar time to civil time 
	public double majorationHoraire(){
		double offset = 0;
		if (date.mois == 7 || date.mois == 8 || date.mois == 6 && date.jour >=21 || date.mois == 9 && date.jour <=21){
			offset = 1;
		} else if (date.mois == 11 || date.mois == 12 || date.mois == 1 || date.mois == 2 || date.mois == 3 && date.jour <=21){
			offset = 0;
		} else {offset=1;}
		
		return offset;
	}
		
	
	// Returns the French time zone
	public double fuseau(){
		return 1;
	}
	
	//Returns the hour the Sun is rising
	public double getHeureLever(){
		//true solar hour
		double VL = 12 - this.parametreLeverCoucher();
		// longitude expressed in hours
		double longitude = position.getLongitude()/15;
		//UTC hour
		double TL = VL + this.EquationTemps() - longitude + this.fuseau();
		
		//legal hour : add 1 hour for summer
		double HL = TL + majorationHoraire();
		return HL;
		//return TL;
	}
	
	//Returns the hour the Sin is setting
	public double getHeureCoucher(){
		//true solar hour
		double VC = 12 + this.parametreLeverCoucher();
		// longitude expressed in hours
		double longitude = position.getLongitude()/15;
		//UTC hour
		double TC = VC + this.EquationTemps() - longitude + this.fuseau();
		
		//legal hour : add 1 hour for summer
		double HC = TC + majorationHoraire();
		return HC;	
		}
	
	
	

}

