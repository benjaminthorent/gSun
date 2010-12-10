package com.ei3info.gsun;

/**
 * Listener to define actions when the state (go left, right, up, down or stop) changes
 * @author bthorent
 *
 */
public interface OrientationListener {  
	   
	/**
	 * Listener for change in orientation
	 * @param azimuth
	 * @param pitch
	 * @param roll
	 */
	
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