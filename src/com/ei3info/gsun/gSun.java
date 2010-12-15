package com.ei3info.gsun;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	protected static int precisionMin=1;
	protected static int precisionMax=3;
	protected static int precision=3;
	
	protected static File gsun;
	protected static File temp;
	protected static File temptxt;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gsun = this.getApplicationContext().getDir("gsun", MODE_PRIVATE); 
        new File(gsun, "defaut").mkdirs();
        new File(gsun.getAbsolutePath() + File.separator + "defaut" + File.separator + "carac").mkdirs();
        
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
		    	}	
	    	};
	    };
	  
    
	    //on affecte aux Button l'écouteur d'évènement
        accueil_calcul.setOnClickListener(onClickLister);
        accueil_mesures.setOnClickListener(onClickLister);
        
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.aide_texte)
            .setCancelable(false)
            .setTitle(R.string.aide_titre)
            .setPositiveButton("Retour", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                }
            });
        final AlertDialog alert = builder.create();
        
        accueil_aide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.show();
            }
            });
    }
}