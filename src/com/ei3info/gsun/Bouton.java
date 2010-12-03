package com.ei3info.gsun;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public class Bouton extends Button{
	
	public Bouton(Context context,String texte, int taille, int margin_left){
		super(context);
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		l.gravity=0x50;
		l.leftMargin=margin_left;
	    this.setLayoutParams(l);
	    this.setText(texte);
	    this.setPadding(taille, taille, taille, taille);
	}
	
}
