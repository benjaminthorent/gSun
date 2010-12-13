package com.ei3info.gsun;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * Class that allows to create a standard button with specific characteristics
 * @author bthorent
 *
 */
public class Bouton extends Button{
	
	/**
	 * Button Constructor
	 * @param context : corresponding to the current context in the application
	 * @param texte : text to display in the button
	 * @param largeur : desired width for the button
	 * @param hauteur : desired height for the button
	 * @param margin : margin with the middle of the screen width (if negative on the left, on the right if positive)
	 */
	public Bouton(Context context,String texte, int gravity, int largeur, int hauteur, int margin){
		super(context);
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
	    //text, width and height definition for the button
		this.setText(texte);
	    this.setWidth(largeur);
	    this.setHeight(hauteur);
	    //button on the bottom of the screen
		l.gravity=gravity;
		//Definition of the margin as described in the parameters description
		if(margin>=0){
			l.leftMargin=margin;
		}else{
			l.leftMargin=-margin-largeur;
		}
		//Associate the layout parameters previously defined to the button
	    this.setLayoutParams(l);
	}
	
}
