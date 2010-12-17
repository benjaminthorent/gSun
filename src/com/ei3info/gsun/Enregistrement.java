package com.ei3info.gsun;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class Enregistrement extends Activity {
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog_text_entry);

        final String rep_enregistrement;
    	final String nom_enregistrement;
        
        ArrayList<String> listeRep = new ArrayList<String>();
	    listeRep = Fichier.getAllDir();
	    listeRep.add(0,"Choisir un répertoire");
	    listeRep.add(1,"Nouveau répertoire");
        
	    final Spinner spinner = (Spinner) findViewById(R.id.repertoire_spinner);
	    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listeRep);
	    spinner.setAdapter(spinnerArrayAdapter);
        
        final Button enregistrement_valider = (Button)findViewById(R.id.enregistrement_valider);
	    
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.alertdialogperso, null);
 
        //CrŽation de l'AlertDialog
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        //On affecte la vue personnalisŽ que l'on a crŽe ˆ notre AlertDialog
        adb.setView(alertDialogView);
 
        //On donne un titre ˆ l'AlertDialog
        adb.setTitle("Titre de notre boite de dialogue");
 
        //On modifie l'ic™ne de l'AlertDialog pour le fun ;)
        adb.setIcon(android.R.drawable.ic_dialog_alert);
 
        //On affecte un bouton "OK" ˆ notre AlertDialog et on lui affecte un Žvnement
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
 
            	//Lorsque l'on cliquera sur le bouton "OK", on rŽcupre l'EditText correspondant ˆ notre vue personnalisŽe (cad ˆ alertDialogView)
            	EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
 
            	//TextView repertoire_nouveau_nom = (TextView)findViewById(R.id.repertoire_nouveau_nom);
            	TextView nouveaurep = (TextView)findViewById(R.id.repertoire_nouveau_nom);
            	final TextView nom =(TextView)findViewById(R.id.nom);
    			final EditText nom_edit=(EditText)findViewById(R.id.nom_edit);
    			final TextView repertoire_nouveau_nom = (TextView)findViewById(R.id.repertoire_nouveau_nom);
    			final TextView nouveau_nom_contrainte = (TextView)findViewById(R.id.nouveau_nom_contrainte);
    			
            	repertoire_nouveau_nom.setText("Le dossier "+et.getText().toString()+" a été créé");
            	repertoire_nouveau_nom.setVisibility(3);
            	nom.setVisibility(3);
            	nom_edit.setText(Fichier.getDefaultName());
            	nom_edit.setVisibility(3);
            	enregistrement_valider.setVisibility(3);
            	
            	nouveau_nom_contrainte.setVisibility(3);

        	    Fichier.newDir(et.getText().toString());
        	    
        	    
        	    
            	//On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
            	//Toast.makeText(Enregistrement3.this, nouveaurep, Toast.LENGTH_SHORT).show();
            	
            	
            	
          } });
 
	    
        //On crŽe un bouton "Annuler" ˆ notre AlertDialog et on lui affecte un ŽvŽnement
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	//Lorsque l'on cliquera sur annuler on quittera l'application
            	finish();
          } });
        
    
    
    
    
    
    
        
		
			
		   
			
			final TextView nom =(TextView)findViewById(R.id.nom);
			final EditText nom_edit=(EditText)findViewById(R.id.nom_edit);
			final TextView nouveau_nom_contrainte = (TextView)findViewById(R.id.nouveau_nom_contrainte);
			
			OnItemSelectedListener onItemSelectedListenerRepertoire = new OnItemSelectedListener() {
		    	//@Override
	        	//@SuppressWarnings("unchecked")
		    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

		    		switch ((int) parent.getItemIdAtPosition(position)) {
		    		case 0:
		    			nom.setVisibility(4);
		    			nom_edit.setVisibility(4);
		    			break;
		    		
		    		case 1:
		    			adb.show();
			    		break;
		    		
		    		default:
		    			nom_edit.setText(Fichier.getDefaultName());
		    			nom.setVisibility(3);
		    			nom_edit.setVisibility(3);
		            	nouveau_nom_contrainte.setVisibility(3);
		            	enregistrement_valider.setVisibility(3);
		    			break;
		    		}
		    	}
		    	
		    	public void onNothingSelected (AdapterView<?> parent) {
		    		//Nothing
		    	}
		    };
		    
		    spinner.setOnItemSelectedListener(onItemSelectedListenerRepertoire);
    
    
		    enregistrement_valider.setOnClickListener(
			        	new OnClickListener() {
			    	        @Override
			    		    public void onClick(View v){
			    	        	if (nom_edit.getText().toString().length() > 15) {
			    	        		Toast.makeText(getBaseContext(), "Le nom dépasse quinze caractres !", 2000).show();
			    	        	} else {
			    	        		final TextView repertoire_nouveau_nom = (TextView)findViewById(R.id.repertoire_nouveau_nom);
			    	        		final EditText nom_edit=(EditText)findViewById(R.id.nom_edit);
			    	        		
			    	        		if (spinner.getSelectedItemPosition()==1) {
			    	        			String rep_enregistrement_a_couper=repertoire_nouveau_nom.getText().toString();
			    	        			int fin = rep_enregistrement_a_couper.length()-11;
			    	        			String rep_enregistrement = rep_enregistrement_a_couper.substring(11,fin);
		
			    	        			String nom_enregistrement = nom_edit.getText().toString();
			    	        			//Toast.makeText(getBaseContext(), rep_enregistrement, 3000).show();
			    	        			
			    	        			boolean bouleen = Fichier.savePicture(nom_enregistrement,rep_enregistrement);
			    	        			if (bouleen) {
			    	        				Toast.makeText(getBaseContext(), "Enregistrement : " + nom_enregistrement + " dans le répertoire " + rep_enregistrement, 3000).show();
			    	        			} else {
			    	        				Toast.makeText(getBaseContext(), "Nom de fichier incorrect", 3000).show();
			    	        			}
			    	        			
			    	        		} else if (spinner.getSelectedItemPosition()!=0) {
			    	        			String rep_enregistrement = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
			    	        			String nom_enregistrement = nom_edit.getText().toString();
			    	        			
			    	        			//File f = Fichier.File("lulu");
			    	        			//f.mkdirs();
			    	        			boolean bouleen = Fichier.savePicture(nom_enregistrement,rep_enregistrement);
			    	        			if (bouleen) {
			    	        				Toast.makeText(getBaseContext(), "Enregistrement : " + nom_enregistrement + " dans le répertoire " + rep_enregistrement, 3000).show();
			    	        			} else {
			    	        				Toast.makeText(getBaseContext(), "Nom de fichier incorrect", 3000).show();
			    	        			}
			    	        			
			    	        		}
			    	        		
			    	        		
			    	        		Intent action = new Intent(Enregistrement.this, gSun.class);
				    				startActivity(action);
				    				finish();
			    	        	}
			    	        	
			    	        }
			        	}
			   );
    
    
    }
}