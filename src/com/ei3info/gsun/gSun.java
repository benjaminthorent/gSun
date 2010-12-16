package com.ei3info.gsun;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.BatteryManager;
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
	protected static int fileCount;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gsun = this.getApplicationContext().getDir("gsun", MODE_PRIVATE); 
        Fichier.newDir("defaut");
        Fichier.newDir("defaut" + File.separator + "carac");
        
        //FOR TEST : CREATES gsun/rep1/photo1, gsun/rep1/photo1txt, gsun/rep1/carac/photocarac
        Fichier.newDir("rep1");
        File photo1 = Fichier.File("rep1", "photo1");
		Fichier.create(photo1);
		File photo1txt = Fichier.File("rep1", "photo1txt");
		Fichier.create(photo1txt);
		File photocarac = Fichier.File("rep1", "carac", "photocarac");
		Fichier.create(photocarac);
        Bitmap bmp = Fichier.drawableToBitmap(getResources().getDrawable(R.drawable.chrysanthemum));
        byte[] data = Fichier.bitmapToByte(bmp);
        Fichier.setPicture(photo1, data);
        Bitmap bmp2 = Fichier.drawableToBitmap(getResources().getDrawable(R.drawable.desert));
        byte[] data2 = Fichier.bitmapToByte(bmp2);
        Fichier.setPicture(photocarac, data2);
        Fichier.setInfo(photo1txt, "15/12", "15:00", "2", "soleil");
        
	    Button accueil_calcul =(Button)findViewById(R.id.accueil_calcul);
	    Button accueil_mesures =(Button)findViewById(R.id.accueil_mesures);
	    Button accueil_aide =(Button)findViewById(R.id.accueil_aide);
	    


	
	     
	    //On cr�e un �couteur d'�v�nement commun au deux Button
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
	  
    
	    //on affecte aux Button l'�couteur d'�v�nement
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
            });}
        
    
        
        
    
}