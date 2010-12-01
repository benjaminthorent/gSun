package com.ei3info.gsun;


public class PositionUtilisateur {
	public double latitude; // en degrés
	public double longitude; // en degrés
	

	public PositionUtilisateur(double lat, double lon){
		this.latitude = lat;
		this.longitude = lon;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	
}
