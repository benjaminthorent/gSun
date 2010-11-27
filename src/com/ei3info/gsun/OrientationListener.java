package com.ei3info.gsun;

public interface OrientationListener {  
	   
    public void onOrientationChanged(float azimuth,   
            float pitch, float roll);  
   
    /** 
     * The objective is on the left 
     */  
    public void GoLeft();  
   
    /** 
     * The objective is on the right
     */  
    public void GoRight();  
   
    /** 
     * The objective is above
     */  
    public void GoUp();  
   
    /** 
     * The objective is under
     */  
    public void GoDown(); 
    
    /** 
     * Objective reached
     */  
    public void Ok(); 
   
}