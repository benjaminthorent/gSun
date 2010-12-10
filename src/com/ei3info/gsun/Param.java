package com.ei3info.gsun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Param extends Activity implements SeekBar.OnSeekBarChangeListener {
	SeekBar mSeekBar;
	TextView mProgressText;
	
	protected int compteur=0;
	protected int heureLever;
	protected int heureCoucher;
	protected int heure=12;

	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.param);
        
        //Getting longitude & lattitude    
        LocationManager objgps = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);        
        Location location = objgps.getLastKnownLocation("gps");
        
        //double latitude = location.getLatitude();
        //int longitude = (int)(location.getLongitude());
        if(location==null){
        	Toast.makeText(this, "Pb !", 1000).show();
        }else{
        	Toast.makeText(this, (int) location.getLatitude(), 1000).show();
        }
        

	
        //Spinner choix type date
			final Spinner param_typedate_spinner=(Spinner) findViewById(R.id.param_typedate);
			ArrayAdapter<CharSequence> param_typedate_adapter = ArrayAdapter.createFromResource(this, R.array.param_typedate_array, android.R.layout.simple_spinner_item);
			param_typedate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			param_typedate_spinner.setAdapter(param_typedate_adapter);
        
        //Spinner choix mois
			final Spinner param_mois_spinner=(Spinner) findViewById(R.id.param_mois);
			ArrayAdapter<CharSequence> param_mois_adapter = ArrayAdapter.createFromResource(this, R.array.param_mois_array, android.R.layout.simple_spinner_item);
			param_typedate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			param_mois_spinner.setAdapter(param_mois_adapter);
        
        //Spinner choix jour
			final Spinner param_jour_spinner=(Spinner) findViewById(R.id.param_jour);
			ArrayAdapter<CharSequence> param_jour_adapter = ArrayAdapter.createFromResource(this, R.array.param_jour_array, android.R.layout.simple_spinner_item);
			param_typedate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			param_jour_spinner.setAdapter(param_jour_adapter);
       
		//Textes
	        final TextView param_lever = (TextView)findViewById(R.id.param_lever);
	        final TextView param_coucher = (TextView)findViewById(R.id.param_coucher);
	        final TextView param_levertext = (TextView)findViewById(R.id.param_levertext);
	        final TextView param_couchertext = (TextView)findViewById(R.id.param_couchertext);
	    
	    //Boutons
	        final Button param_precision_moins = (Button)findViewById(R.id.param_precision_moins);
	        final Button param_precision_valeur = (Button)findViewById(R.id.param_precision_valeur);
	        final Button param_precision_plus = (Button)findViewById(R.id.param_precision_plus);
	        final Button param_retour = (Button)findViewById(R.id.param_retour);
	        final Button param_TrouverSoleil = (Button)findViewById(R.id.param_TrouverSoleil);
	        final Button param_precisiontext = (Button)findViewById(R.id.param_precisiontext);
        
        gSun.temps = new Temps(1,1);
        // TODO Bien instancier !!!
        gSun.posUtilisateur = new PositionUtilisateur(48.8,-1.583);
        gSun.calcul = new Calculs(gSun.posUtilisateur,gSun.temps);


		param_precision_valeur.setText(" " + gSun.precision + " ");
		if (gSun.precision==gSun.precisionMax) {
			param_precision_plus.setVisibility(4);
		}
		if (gSun.precision==gSun.precisionMin) {
			param_precision_moins.setVisibility(4);
		}
        
        //Clics sur les boutons annuler et valider et précision
        OnClickListener onClickLister = new OnClickListener() {
            public void onClick(View v){
            	switch(v.getId()){
            	case R.id.param_retour:
            		Intent intent = new Intent(Param.this, gSun.class);
        			startActivity(intent);
            		finish();
            		break;
            	case R.id.param_TrouverSoleil:
            		gSun.temps.heure=heure;
            		
            		Intent intent2 = new Intent(Param.this, EcranRecherche.class);
        			startActivity(intent2);
        			finish();
            		break;
            	case R.id.param_precisiontext:
            		if (gSun.precision < gSun.precisionMax && gSun.precision > gSun.precisionMin) {
            			param_precision_moins.setVisibility(7-param_precision_moins.getVisibility());
                		param_precision_valeur.setVisibility(7-param_precision_valeur.getVisibility());
                		param_precision_plus.setVisibility(7-param_precision_plus.getVisibility());
            		} else if (gSun.precision == gSun.precisionMax) {
            			param_precision_moins.setVisibility(7-param_precision_moins.getVisibility());
                		param_precision_valeur.setVisibility(7-param_precision_valeur.getVisibility());
                		param_precision_plus.setVisibility(4);
            		} else if (gSun.precision == gSun.precisionMin) {
            			param_precision_plus.setVisibility(7-param_precision_plus.getVisibility());
                		param_precision_valeur.setVisibility(7-param_precision_valeur.getVisibility());
                		param_precision_moins.setVisibility(4);
            		}
            		
            	}
            }
        };
        
        //On affecte aux boutons l'ecouteur d'evenement
			param_retour.setOnClickListener(onClickLister);
			param_TrouverSoleil.setOnClickListener(onClickLister);
			param_precisiontext.setOnClickListener(onClickLister);




			
	   //Choix type date
		    OnItemSelectedListener onItemSelectedListenerPerso = new OnItemSelectedListener() {
		    	//@Override
	        	//@SuppressWarnings("unchecked")
		    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
		    		switch ((int) parent.getItemIdAtPosition(position)) {
		    			case 0:
		    				//On masque les autres champs
			    				param_jour_spinner.setVisibility(4);
			    				param_mois_spinner.setVisibility(4);
				    			param_levertext.setVisibility(4);
				    			param_lever.setVisibility(4);
				    			mSeekBar.setVisibility(4);
				    			mProgressText.setVisibility(4);
				    			param_precisiontext.setVisibility(4);
				    	        param_precision_moins.setVisibility(4);
				    	        param_precision_valeur.setVisibility(4);
				    	        param_precision_plus.setVisibility(4);
				    			param_couchertext.setVisibility(4);
				    			param_coucher.setVisibility(4);
				    			
				    		//On mets les champs à 0
				    			param_jour_spinner.setSelection(0);
				    			param_mois_spinner.setSelection(0);
			    			
				    		break;
		    			
		    			case 2: //Solstice d'ete (21 juin)
		    	    		//On fait apparaitre tous les champs
				    			param_jour_spinner.setVisibility(3);
				    			param_mois_spinner.setVisibility(3);
				    			param_levertext.setVisibility(3);
				    			param_lever.setVisibility(3);
				    			mSeekBar.setVisibility(3);
				    			mProgressText.setVisibility(3);
				    			param_precisiontext.setVisibility(3);
				    			param_couchertext.setVisibility(3);
				    			param_coucher.setVisibility(3);
				    			param_TrouverSoleil.setVisibility(3);
			    			
				    		//On affiche la date : 21 juin
			    				param_jour_spinner.setSelection(3);
			        			param_mois_spinner.setSelection(6);
			        		
				    		//On communique la date
				    			gSun.temps.setJour(21);
				    			gSun.temps.setMois(6);
			    			
				    		//Affichage des heures de lever et de coucher (de part et d'autre de la SeekBar)
				    			heureLever = (int) gSun.calcul.getHeureLever();
				    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
				    			
				    			if (heureLever<10) {
				    				param_lever.setText("0" + heureLever + ":00");
				    			} else {
				    				param_lever.setText("" + heureLever + ":00");
				    			}
				    					
				    			param_coucher.setText(heureCoucher + ":00");
			    			
			    			break;
		    			
		    			case 4:	//Solstice d'hiver (21 decembre)
		    	    		//On fait apparaître tous les champs
			        			param_jour_spinner.setVisibility(3);
			    				param_mois_spinner.setVisibility(3);
				    			param_levertext.setVisibility(3);
				    			param_lever.setVisibility(3);
				    			mSeekBar.setVisibility(3);
				    			mProgressText.setVisibility(3);
				    			param_precisiontext.setVisibility(3);
				    			param_couchertext.setVisibility(3);
				    			param_coucher.setVisibility(3);
				    			param_TrouverSoleil.setVisibility(3);
	
				    		//On affiche la date : 21 decembre
				    			param_jour_spinner.setSelection(3);
			        			param_mois_spinner.setSelection(12);
			    				
			        		//On communique la date
				    			gSun.temps.setJour(21);
				    			gSun.temps.setMois(12);
		    				
				    		//On affiche les heures de lever et de coucher (de part et d'autre de la SeekBar)
				    			heureLever = (int) gSun.calcul.getHeureLever();
				    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
		    				
			    				if (heureLever<10) {
				    				param_lever.setText("0" + heureLever + ":00");
				    			} else {
				    				param_lever.setText("" + heureLever + ":00");
				    			}
		    				
			    				param_coucher.setText("" + heureCoucher + ":00");
			    			
		    				break;
		    			
		    			case 1: //Equinoxe de printemps (21 mars)
		    	    		
		        			//On fait apparaitre tous les champs
			        			param_jour_spinner.setVisibility(3);
				    			param_mois_spinner.setVisibility(3);
				    			param_levertext.setVisibility(3);
				    			param_lever.setVisibility(3);
				    			mSeekBar.setVisibility(3);
				    			mProgressText.setVisibility(3);
				    			param_precisiontext.setVisibility(3);
				    			param_couchertext.setVisibility(3);
				    			param_coucher.setVisibility(3);
				    			param_TrouverSoleil.setVisibility(3);
			    			
				    		//On affiche la date : 21 mars
			    				param_jour_spinner.setSelection(3);
			        			param_mois_spinner.setSelection(3);
				    		
			        		//On communique la date
				    			gSun.temps.setJour(21);
				    			gSun.temps.setMois(3);
		    				
				    		//On affiche les heures de lever et de coucher (de part et d'autre de la SeekBar)
				    			heureLever = (int) gSun.calcul.getHeureLever();
			    				heureCoucher = (int) gSun.calcul.getHeureCoucher();
			    			
			    				if (heureLever<10){
			    					param_lever.setText("0" + heureLever + ":00");
			    				} else {
			    					param_lever.setText("" + heureLever + ":00");
			    				}
			    				
				    			param_coucher.setText("" + heureCoucher + ":00");
				    			
			    			break;
		    			
		    			case 3: //Equinoxe d'automne (21 septembre)
		    	    		
		        			//On fait apparaitre tous les champs
			        			param_jour_spinner.setVisibility(3);
				    			param_mois_spinner.setVisibility(3);
				    			param_levertext.setVisibility(3);
				    			param_lever.setVisibility(3);
				    			mSeekBar.setVisibility(3);
				    			mProgressText.setVisibility(3);
				    			param_precisiontext.setVisibility(3);
				    			param_couchertext.setVisibility(3);
				    			param_coucher.setVisibility(3);
				    			param_TrouverSoleil.setVisibility(3);
			    			
				    		//On affiche la date : 21 mars
			    				param_jour_spinner.setSelection(3);
			        			param_mois_spinner.setSelection(9);
				    		
			        		//On communique la date
				    			gSun.temps.setJour(21);
				    			gSun.temps.setMois(9);
		    				
				    		//On affiche les heures de lever et de coucher (de part et d'autre de la SeekBar)
				    			heureLever = (int) gSun.calcul.getHeureLever();
			    				heureCoucher = (int) gSun.calcul.getHeureCoucher();
			    			
			    				if (heureLever<10){
			    					param_lever.setText("0" + heureLever + ":00");
			    				} else {
			    					param_lever.setText("" + heureLever + ":00");
			    				}
			    				
				    			param_coucher.setText("" + heureCoucher + ":00");
				    			
			    			break;
			    				
		    			case 5: //Date perso
		    				//On fait apparaitre le champ jour
		    					param_jour_spinner.setVisibility(3);
		    				break;
		    		}
		    	}
		    	
		    	public void onNothingSelected (AdapterView<?> parent) {
		    		//Nothing
		    	}
		    };
	    
	    param_typedate_spinner.setOnItemSelectedListener(onItemSelectedListenerPerso);
	    
	    
	    
	    
	    OnItemSelectedListener onItemSelectedListenerPersoJour = new OnItemSelectedListener() {
	    	//@Override
        	//@SuppressWarnings("unchecked")
	    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
	    		switch ((int) parent.getItemIdAtPosition(position)) {
	    		case 0:
	    			//On masque le champ mois et on le met à 0
	    				param_mois_spinner.setVisibility(4);
	    				param_mois_spinner.setSelection(0);
	    				
	    				param_levertext.setVisibility(4);
	        			param_lever.setVisibility(4);
		    			mSeekBar.setVisibility(4);
		    			mProgressText.setVisibility(4);
		    			param_precisiontext.setVisibility(4);
		    	        param_precision_moins.setVisibility(4);
		    	        param_precision_valeur.setVisibility(4);
		    	        param_precision_plus.setVisibility(4);
	        			param_couchertext.setVisibility(4);
	        			param_coucher.setVisibility(4);
	    			break;
	    		
	    		case 1:
		    		gSun.temps.setJour(1);
		    		param_mois_spinner.setVisibility(3);
		    		param_typedate_spinner.setSelection(5);
		    		
		    		if (param_mois_spinner.getSelectedItemPosition()!=0) {
		    			gSun.temps.setMois(param_mois_spinner.getSelectedItemPosition());
		    			
		    			heureLever = (int) gSun.calcul.getHeureLever();
		    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
		    			
		    			if (heureLever<10) {
		    				param_lever.setText("0" + heureLever + ":00");
		    			} else {
		    				param_lever.setText("" + heureLever + ":00");
		    			}
		    					
		    			param_coucher.setText(heureCoucher + ":00");
		    		}
		    		
		    		break;
	    		
	    		case 2:
	    			gSun.temps.setJour(11);
	    			param_mois_spinner.setVisibility(3);
	    			param_typedate_spinner.setSelection(5);
	    			
	    			if (param_mois_spinner.getSelectedItemPosition()!=0) {
		    			gSun.temps.setMois(param_mois_spinner.getSelectedItemPosition());
		    			
		    			heureLever = (int) gSun.calcul.getHeureLever();
		    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
		    			
		    			if (heureLever<10) {
		    				param_lever.setText("0" + heureLever + ":00");
		    			} else {
		    				param_lever.setText("" + heureLever + ":00");
		    			}
		    					
		    			param_coucher.setText(heureCoucher + ":00");
		    		}
	    			
	    			break;
	    		
	    		case 3:
	    			gSun.temps.setJour(21);
		    		param_mois_spinner.setVisibility(3);
		    		
		    		if (param_mois_spinner.getSelectedItemPosition()!=0) {
		    			gSun.temps.setMois(param_mois_spinner.getSelectedItemPosition());
		    			
		    			heureLever = (int) gSun.calcul.getHeureLever();
		    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
		    			
		    			if (heureLever<10) {
		    				param_lever.setText("0" + heureLever + ":00");
		    			} else {
		    				param_lever.setText("" + heureLever + ":00");
		    			}
		    					
		    			param_coucher.setText(heureCoucher + ":00");
		    		}
		    		
		    		
		    		switch (param_mois_spinner.getSelectedItemPosition()) {
		    		case 3:
		    			param_typedate_spinner.setSelection(1);
		    			break;
		    		case 6:
		    			param_typedate_spinner.setSelection(2);
		    			break;
		    		case 9:
		    			param_typedate_spinner.setSelection(3);
		    			break;
		    		case 12:
		    			param_typedate_spinner.setSelection(4);
		    			break;
		    		}
		    		break;
	    		}
	    	}
	    	
	    	public void onNothingSelected (AdapterView<?> parent) {
	    		//Nothing
	    	}
	    };
	    
	    param_jour_spinner.setOnItemSelectedListener(onItemSelectedListenerPersoJour);
	    
	    
	    
	    
	    
	    OnItemSelectedListener onItemSelectedListenerPersoMois = new OnItemSelectedListener() {
	    	//@Override
        	//@SuppressWarnings("unchecked")
	    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
    			if(position == 0) {
    				param_levertext.setVisibility(4);
        			mSeekBar.setVisibility(4);
        			mProgressText.setVisibility(4);
        			param_precisiontext.setVisibility(4);
        	        param_precision_moins.setVisibility(4);
        	        param_precision_valeur.setVisibility(4);
        	        param_precision_plus.setVisibility(4);
        			param_lever.setVisibility(4);
        			param_couchertext.setVisibility(4);
        			param_coucher.setVisibility(4);
	    		} else {
		    		gSun.temps.setMois((int) parent.getItemIdAtPosition(position));
	    			
		    		param_levertext.setVisibility(3);
	    			param_lever.setVisibility(3);
	    			mSeekBar.setVisibility(3);
	    			mProgressText.setVisibility(3);
	    			param_precisiontext.setVisibility(3);
	    			param_couchertext.setVisibility(3);
	    			param_coucher.setVisibility(3);
	    			param_TrouverSoleil.setVisibility(3);
	    			
	    			if (param_jour_spinner.getSelectedItemPosition()!=0) {
		    			gSun.temps.setJour(21);
					
		    			//Affichage des heures de lever et de coucher (de part et d'autre de la SeekBar)
			    			heureLever = (int) gSun.calcul.getHeureLever();
			    			heureCoucher = (int) gSun.calcul.getHeureCoucher();
			    			
			    			if (heureLever<10) {
			    				param_lever.setText("0" + heureLever + ":00");
			    			} else {
			    				param_lever.setText("" + heureLever + ":00");
			    			}
			    					
			    			param_coucher.setText(heureCoucher + ":00");
	    			}
	    		}
	    		
    			
    			switch (position) {
    			case 0:
    				break;
    			case 3:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(1);
	    			} else {
	    				param_typedate_spinner.setSelection(5);
	    			}
    				break;
    			case 6:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(2);
	    			} else {
	    				param_typedate_spinner.setSelection(5);
	    			}
    				break;
    			case 9:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(3);
	    			} else {
	    				param_typedate_spinner.setSelection(5);
	    			}
    				break;
    			case 12:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(4);
	    			} else {
	    				param_typedate_spinner.setSelection(5);
	    			}
    				break;
    			default:
    				param_typedate_spinner.setSelection(5);
    				break;
    			}
	    	}
	    	
	    	public void onNothingSelected (AdapterView<?> parent) {
	    		
	    	}
	    	
	    	
	    };
	    
	    param_mois_spinner.setOnItemSelectedListener(onItemSelectedListenerPersoMois);

     
	    
	    mSeekBar = (SeekBar)findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView)findViewById(R.id.progress);
        mSeekBar.setVisibility(4);
        param_TrouverSoleil.setVisibility(4);
        
       
        OnClickListener onClickPlus = new OnClickListener() {
            public void onClick(View v){
            	if (v.getId() == R.id.param_precision_plus) {
            		gSun.precision=Math.min(gSun.precision+1, gSun.precisionMax);
            		param_precision_valeur.setText(" " + gSun.precision + " ");
            		param_precision_moins.setVisibility(3);
            		if (gSun.precision==gSun.precisionMax) {
            			param_precision_plus.setVisibility(4);
            		}
            	}
            }
        };
        param_precision_plus.setOnClickListener(onClickPlus);
        
        OnClickListener onClickMoins = new OnClickListener() {
            public void onClick(View v){
            	if (v.getId() == R.id.param_precision_moins) {
            		gSun.precision=Math.max(gSun.precision-1, gSun.precisionMin);
            		param_precision_valeur.setText(" " + gSun.precision + " ");
            		param_precision_plus.setVisibility(3);
            		if (gSun.precision==gSun.precisionMin) {
            			param_precision_moins.setVisibility(4);
            		}
            	}
            }
        };
        param_precision_moins.setOnClickListener(onClickMoins);  
        
    }
    
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
    	heure = heureLever-1;
        int nbIntervalles = heureCoucher-heureLever+1;
        int tailleIntervalle = (int) (100.0 / nbIntervalles);
        int borneSupIntervalle = tailleIntervalle;
        int nbIntervallesRestants = nbIntervalles-1;
        boolean arretboucle = false;
      
       	while(!arretboucle){
        	heure=Math.min(heure+1,heureCoucher) ;
        	if (nbIntervallesRestants != 0) {
	        	tailleIntervalle = (int) ((100.0-borneSupIntervalle)/nbIntervallesRestants);
	        }
        	
	        if(progress <= borneSupIntervalle) { 
	        	arretboucle = true;
	        } else {
	        	borneSupIntervalle = borneSupIntervalle + tailleIntervalle;
	        	nbIntervallesRestants--;
	        }
        }
    	
    	if (heure<10) {
    		mProgressText.setText("0" + heure + ":00"/*+"=" + fromTouch*/);
       	} else {
       		mProgressText.setText("" + heure + ":00"/*+"=" + fromTouch*/);
       	}
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    	mProgressText.setVisibility(3);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //Rien
    }
    
}

