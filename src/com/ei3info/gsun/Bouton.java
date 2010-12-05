package com.ei3info.gsun;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class Bouton extends Button{
	
	public Bouton(Context context,String texte, int largeur, int hauteur, int margin){
		super(context);
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
	    this.setText(texte);
	    this.setWidth(largeur);
	    this.setHeight(hauteur);
		l.gravity=0x50;
		if(margin>0){
			l.leftMargin=margin;
		}else{
			l.leftMargin=-margin-largeur;
		}
	    this.setLayoutParams(l);
	}
	
}
