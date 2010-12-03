package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;

public class gSun extends Activity {
    /** Called when the activity is first created. */
	
	protected static Temps temps;
    protected static PositionUtilisateur posUtilisateur;
    protected static Calculs calcul;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	    Button accueil_calcul =(Button)findViewById(R.id.accueil_calcul);
	    Button accueil_mesures =(Button)findViewById(R.id.accueil_mesures);
	    Button accueil_aide =(Button)findViewById(R.id.accueil_aide);
	
	     
	    //On crée un écouteur d'évènement commun au deux Button
	    OnClickListener onClickLister = new OnClickListener() {
	 
	    	@Override
	    	public void onClick(View v){
		    	switch(v.getId()){
		    	case R.id.accueil_calcul:
		    		Intent intent = new Intent(gSun.this, Param.class);
					startActivity(intent);
		    		finish();
		    		break;
		    	case R.id.accueil_mesures:
		    		Intent intent2 = new Intent(gSun.this, AccesMesures.class);
					startActivity(intent2);
					finish();
		    		break;
		    	case R.id.accueil_aide:
		    		Intent intent3 = new Intent(gSun.this, Aide.class);
					startActivity(intent3);
					finish();
		    		break;
	    	}	
	    };
	    
	 };
	  
    
	    //on affecte aux Button l'écouteur d'évènement
        accueil_calcul.setOnClickListener(onClickLister);
        accueil_mesures.setOnClickListener(onClickLister);
        accueil_aide.setOnClickListener(onClickLister);
    }
}