package com.ei3info.gsun;

import java.io.File;
import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;


public class SyntheseMesures extends Activity {

	
	/** Called when the activity is first created. */
	
	protected static Bitmap imageAAfficher;
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.synthesemesures);
            
	        /**AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(R.string.aide_texte)
	            .setCancelable(false)
	            .setTitle(R.string.aide_titre)
	            .setPositiveButton("Retour", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	                }
	            });
	        final AlertDialog alert = builder.create();**/
	        
	        
	        TableLayout tableau = (TableLayout)findViewById(R.id.synthese_tableau);
	        
	        // int nblignes = 3;
	        ArrayList<File> fichiersText = new ArrayList<File>();
	        fichiersText = Fichier.getAllCaracFiles(AccesMesures.repCourant);

	        int nblignes = fichiersText.size();
	        
	        
        ArrayList<TableRow> vecteurligne = new ArrayList<TableRow>(nblignes);
	        
	     for(int i=0;i<nblignes;i++){
	    	 TableRow ligne = new TableRow(this);
	    	 vecteurligne.add(ligne);
	     }
	        
	     final ArrayList<Bitmap> images=new ArrayList<Bitmap>();
	        for(int i=0;i<nblignes;i++){
	        	
	        	
		        vecteurligne.get(i).setLayoutParams(new LayoutParams(
		                LayoutParams.FILL_PARENT,
		                LayoutParams.WRAP_CONTENT));
		      
		        //Nom: exactement 15 caractčres
		        //Mois: 3 espaces au début, 2 caractčres, 2 espaces ŕ la fin
		        //Jour: 3 espaces au début, 2 caractčres, 3 espaces ŕ la fin
		        //Heure: 3 espaces au début, 2 caractčres + les caractčres ":00", 2 espaces ŕ la fin
		        //Etat: Spinner ŕ régler
		        //Préc. : 2 espaces au début, 1 caractčre + le caractčre "°".
		        Button c1 = new Button(this);
		        c1.setId(i);
		        String nom = fichiersText.get(i).getName();
		        String nombis = Fichier.ajoutEsp(nom.substring(0,nom.length()-3), 1);
		        c1.setText(nombis);
		        c1.setTextColor(R.color.texte);
		        
		        Bitmap image = Fichier.getPicture(Fichier.File(AccesMesures.repCourant.getName(), fichiersText.get(i).getName()));
		        images.add(image);
		        
		        OnClickListener c1onClickLister = new OnClickListener() {
		            public void onClick(View v){
		            	
		            	
		            		//SyntheseMesures.imageAAfficher=images.get(v.getId());
		            	Bitmap bmp = Fichier.drawableToBitmap(getResources().getDrawable(R.drawable.chrysanthemum));
		            	SyntheseMesures.imageAAfficher=bmp;
		            		Intent intent = new Intent(SyntheseMesures.this, AffichagePhotoSynthese.class);
		        			startActivity(intent);
		            		finish();
		            	
		            }
		        };
		        c1.setOnClickListener(c1onClickLister);
		        c1.setBackgroundColor(0xCCCCFF);
		        
		        TextView c2 = new TextView(this);
		        c2.setText(Fichier.getInfo(fichiersText.get(i))[0]);
		        c2.setTextColor(R.color.texte);

		        
		        TextView c3 = new TextView(this);
		        c3.setText(Fichier.getInfo(fichiersText.get(i))[1]);
		        c3.setTextColor(R.color.texte);
		    
		        Button c4 = new Button(this);
		        c4.setText(Fichier.getInfo(fichiersText.get(i))[2]);
		        c4.setBackgroundDrawable(null);
		        c4.setTextColor(R.color.texte);
		        
		        /**c4.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		                alert.show();
		            }
		            });**/
		        
		        TextView c5 = new TextView(this);
		        c5.setText(Fichier.getInfo(fichiersText.get(i))[3]);
		        c5.setTextColor(R.color.texte);

		        
		        vecteurligne.get(i).addView(c1);
		        vecteurligne.get(i).addView(c2);
		        vecteurligne.get(i).addView(c3);
		        vecteurligne.get(i).addView(c4);
		        vecteurligne.get(i).addView(c5);
		       
		        
		        tableau.addView(vecteurligne.get(i),new TableLayout.LayoutParams(
		                LayoutParams.FILL_PARENT,
		                LayoutParams.WRAP_CONTENT));
	        }
 
	            
	            Button synthese_retour = (Button)findViewById(R.id.synthese_retour);
	            OnClickListener onClickLister = new OnClickListener() { 
			    	@Override
			    	public void onClick(View v){
			    		//switch(v.getId()){
				    	//case R.id.synthese_retour :
					    	Intent intent1 = new Intent(SyntheseMesures.this, AccesMesures.class);
							startActivity(intent1);
					    	finish();
				    	//break;
				    	//}
			    	}
	            };
	            synthese_retour.setOnClickListener(onClickLister);
	    
	    }  
}

