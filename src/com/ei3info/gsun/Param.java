package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
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

public class Param extends Activity implements SeekBar.OnSeekBarChangeListener {
	SeekBar mSeekBar;
	TextView mProgressText;
	
	protected int precision=2;
	protected int precisionMax=5;
	protected int precisionMin=0;

	protected int compteur=0;
	protected int heureLever;
	protected int heureCoucher;

	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.param);
	
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
       
        final TextView param_lever = (TextView)findViewById(R.id.param_lever);
        final TextView param_coucher = (TextView)findViewById(R.id.param_coucher);
        final TextView param_levertext = (TextView)findViewById(R.id.param_levertext);
        final TextView param_couchertext = (TextView)findViewById(R.id.param_couchertext);
        final TextView param_precisiontext = (TextView)findViewById(R.id.param_precisiontext);
        
        final Button param_precision_moins = (Button)findViewById(R.id.param_precision_moins);
        final Button param_precision_valeur = (Button)findViewById(R.id.param_precision_valeur);
        final Button param_precision_plus = (Button)findViewById(R.id.param_precision_plus);
       
        final Button param_retour = (Button)findViewById(R.id.param_retour);
        final Button param_TrouverSoleil = (Button)findViewById(R.id.param_TrouverSoleil);
        
        gSun.temps = new Temps(1,1);
        gSun.posUtilisateur = new PositionUtilisateur(5.0,4.0);
        gSun.calcul = new Calculs(gSun.posUtilisateur,gSun.temps);
        
        
        OnClickListener onClickLister = new OnClickListener() {
            public void onClick(View v){
            	switch(v.getId()){
            	case R.id.param_retour:
            		Intent intent = new Intent(Param.this, gSun.class);
        			startActivity(intent);
            		finish();
            		break;
            	case R.id.param_TrouverSoleil:
            		Intent intent2 = new Intent(Param.this, EcranRecherche.class);
        			startActivity(intent2);
        			finish();
            		break;
            	}
            }
        };
        
        //on affecte aux boutons l'ecouteur d'evenement
			param_retour.setOnClickListener(onClickLister);
			param_TrouverSoleil.setOnClickListener(onClickLister);
        
	
	   //Choix type date
		    OnItemSelectedListener onItemSelectedListenerPerso = new OnItemSelectedListener() {
		    	//@Override
	        	//@SuppressWarnings("unchecked")
		    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
		    		switch ((int) parent.getItemIdAtPosition(position)) {
		    			case 0:
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
			    			break;
		    			case 1:
		    				//Solstice d'ete (21 juin)
		    	    		param_jour_spinner.setSelection(3);
		        			param_mois_spinner.setSelection(6);
			    			param_jour_spinner.setVisibility(3);
			    			param_mois_spinner.setVisibility(3);
			    			param_levertext.setVisibility(3);
			    			param_lever.setVisibility(3);
			    			mSeekBar.setVisibility(3);
			    			mProgressText.setVisibility(3);
			    			param_precisiontext.setVisibility(3);
			    	        param_precision_moins.setVisibility(3);
			    	        param_precision_valeur.setVisibility(3);
			    	        param_precision_plus.setVisibility(3);
			    			param_couchertext.setVisibility(3);
			    			param_coucher.setVisibility(3);
			    			
			    			gSun.temps.setJour(21);
			    			gSun.temps.setMois(6);
			    			
			    			heureLever = 6;
			    			heureCoucher = 23;
			    			
			    			if (heureLever<10) {
			    				param_lever.setText("0" + heureLever + ":00");
			    			} else {
			    				param_lever.setText("" + heureLever + ":00");
			    			}
			    					
			    			param_coucher.setText(heureCoucher + ":00");
			    			param_TrouverSoleil.setVisibility(3);
			    			break;
		    			case 2:	
		    				//Solstice d'hiver (21 dŽcembre)
		    	    		param_jour_spinner.setSelection(3);
		        			param_mois_spinner.setSelection(12);
		    				param_jour_spinner.setVisibility(3);
		    				param_mois_spinner.setVisibility(3);
			    			param_levertext.setVisibility(3);
			    			param_lever.setVisibility(3);
			    			mSeekBar.setVisibility(3);
			    			mProgressText.setVisibility(3);
			    			param_precisiontext.setVisibility(3);
			    	        param_precision_moins.setVisibility(3);
			    	        param_precision_valeur.setVisibility(3);
			    	        param_precision_plus.setVisibility(3);
			    			param_couchertext.setVisibility(3);
			    			param_coucher.setVisibility(3);
	
			    			gSun.temps.setJour(21);
			    			gSun.temps.setMois(12);
		    				heureLever = (int) gSun.calcul.getHeureLever();
		    				heureCoucher = (int) gSun.calcul.getHeureCoucher();
		    				
		    				if (heureLever<10) {
			    				param_lever.setText("0" + Integer.toString(heureLever) + ":00");
			    			} else {
			    				param_lever.setText("" + heureLever + ":00");
			    			}
		    				
			    			param_coucher.setText(Integer.toString(heureCoucher) + ":00");
			    			param_TrouverSoleil.setVisibility(3);
		    				break;
		    			case 3:
		    				//Equinoxes
		    	    		param_jour_spinner.setSelection(3);
		        			param_mois_spinner.setSelection(3);
			    			param_jour_spinner.setVisibility(3);
			    			param_mois_spinner.setVisibility(3);
			    			param_levertext.setVisibility(3);
			    			param_lever.setVisibility(3);
			    			mSeekBar.setVisibility(3);
			    			mProgressText.setVisibility(3);
			    			param_precisiontext.setVisibility(3);
			    	        param_precision_moins.setVisibility(3);
			    	        param_precision_valeur.setVisibility(3);
			    	        param_precision_plus.setVisibility(3);
			    			param_couchertext.setVisibility(3);
			    			param_coucher.setVisibility(3);
			    			
			    			gSun.temps.setJour(21);
			    			gSun.temps.setMois(03);
		    				
			    			heureLever = (int) gSun.calcul.getHeureLever();
		    				heureCoucher = (int) gSun.calcul.getHeureCoucher();
			    			
		    				if (heureLever<10){
		    					param_lever.setText("0" + Integer.toString(heureLever) + ":00");
		    				} else {
		    					param_lever.setText("" + Integer.toString(heureLever) + ":00");
		    				}
		    				
		    				
			    			param_coucher.setText(Integer.toString(heureCoucher) + ":00");
			    			
			    			param_TrouverSoleil.setVisibility(3);
			    			//param_heure_spinner.setVisibility(3);
			    			break;
		    			case 4:
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
    			if(param_typedate_spinner.getSelectedItemPosition()==0){
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
    			}
				
	    		switch ((int) parent.getItemIdAtPosition(position)) {
	    		case 0:
	    			param_mois_spinner.setVisibility(4);
	    			break;
	    		case 1:
		    		gSun.temps.setJour(1);
		    		param_mois_spinner.setVisibility(3);
		    		param_typedate_spinner.setSelection(4);
		    		break;
	    		case 2:
	    			gSun.temps.setJour(11);
	    			param_mois_spinner.setVisibility(3);
	    			param_typedate_spinner.setSelection(4);
	    			break;
	    		case 3:
	    			gSun.temps.setJour(21);
		    		param_mois_spinner.setVisibility(3);
		    		switch (param_mois_spinner.getSelectedItemPosition()) {
		    		case 3:
		    			param_typedate_spinner.setSelection(3);
		    			break;
		    		case 6:
		    			param_typedate_spinner.setSelection(1);
		    			break;
		    		case 9:
		    			param_typedate_spinner.setSelection(3);
		    			break;
		    		case 12:
		    			param_typedate_spinner.setSelection(2);
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
    			
	    		   			
    			if(position == 0) {
	    			//param_heure_spinner.setVisibility(4);
	    		}else {
		    		gSun.temps.setMois((int) parent.getItemIdAtPosition(position));
	    			param_levertext.setVisibility(3);
	    			param_lever.setVisibility(3);
	    			mSeekBar.setVisibility(3);
	    			mProgressText.setVisibility(3);
	    			param_precisiontext.setVisibility(3);
	    	        param_precision_moins.setVisibility(3);
	    	        param_precision_valeur.setVisibility(3);
	    	        param_precision_plus.setVisibility(3);
	    			param_couchertext.setVisibility(3);
	    			param_coucher.setVisibility(3);
	    			param_TrouverSoleil.setVisibility(3);
	    		}
	    		
    			
    			switch (position) {
    			case 0:
    				break;
    			case 3:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(3);
	    			} else {
	    				param_typedate_spinner.setSelection(4);
	    			}
    				break;
    			case 6:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(1);
	    			} else {
	    				param_typedate_spinner.setSelection(4);
	    			}
    				break;
    			case 9:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(3);
	    			} else {
	    				param_typedate_spinner.setSelection(4);
	    			}
    				break;
    			case 12:
    				if (param_jour_spinner.getSelectedItemPosition() == 3) {
	    				param_typedate_spinner.setSelection(2);
	    			} else {
	    				param_typedate_spinner.setSelection(4);
	    			}
    				break;
    			default:
    				param_typedate_spinner.setSelection(4);
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
            		precision=Math.min(precision+1, precisionMax);
            		param_precision_valeur.setText(" " + Integer.toString(precision) + " ");
            	}
            }
        };
        param_precision_plus.setOnClickListener(onClickPlus);
        
        OnClickListener onClickMoins = new OnClickListener() {
            public void onClick(View v){
            	if (v.getId() == R.id.param_precision_moins) {
            		precision=Math.max(precision-1, precisionMin);
            		param_precision_valeur.setText(" " + Integer.toString(precision) + " ");
            	}
            }
        };
        param_precision_moins.setOnClickListener(onClickMoins);
        
        
        
    }
    
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
    	int heureChoisie = heureLever-1;
        int nbIntervalles = heureCoucher-heureLever+1;
        int tailleIntervalle = (int) (100.0 / nbIntervalles);
        int borneSupIntervalle = tailleIntervalle;
        int nbIntervallesRestants = nbIntervalles-1;
        boolean arretboucle = false;
      
       	while(!arretboucle){
        	heureChoisie=Math.min(heureChoisie+1,heureCoucher) ;
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
    	
    	if (heureChoisie<10) {
    		mProgressText.setText("0" + heureChoisie + ":00"/*+"=" + fromTouch*/);
       	} else {
       		mProgressText.setText("" + heureChoisie + ":00"/*+"=" + fromTouch*/);
       	}
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    	mProgressText.setVisibility(3);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //Rien
    }
    
}

