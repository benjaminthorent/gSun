package com.ei3info.gsun;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class Bouton extends Button{
	
	public Bouton(Context context,String texte){
		super(context);
	    this.setLayoutParams(new 
	        FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
	        ViewGroup.LayoutParams.WRAP_CONTENT));
	    //this.setOrientation(LinearLayout.VERTICAL); 
	    this.setText(texte);
	}
	
	

}
