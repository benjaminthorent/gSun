package com.ei3info.gsun;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Param extends Activity implements SeekBar.OnSeekBarChangeListener {
	SeekBar mSeekBar;
    TextView mProgressText;
	
	protected int jour;
	protected int mois;
	protected int compteur = 0;
	protected int heureLever = 7;
	protected int heureCoucher = 20;
	
	
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
        
        //Spinner choix heure
        /* final Spinner param_heure_spinner=(Spinner) findViewById(R.id.param_heure);
        ArrayAdapter<CharSequence> param_heure_adapter = ArrayAdapter.createFromResource(this, R.array.param_heure_array, android.R.layout.simple_spinner_item);
        param_heure_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        param_heure_spinner.setAdapter(param_heure_adapter); */
        
        //Bouton retour menu
        //ImageButton param_retour =(ImageButton)findViewById(R.id.param_retour);
       
        final TextView param_lever = (TextView)findViewById(R.id.param_lever);
        final TextView param_coucher = (TextView)findViewById(R.id.param_coucher);
        final TextView param_levertext = (TextView)findViewById(R.id.param_levertext);
        //final TextView param_leverpoint = (TextView)findViewById(R.id.param_leverpoint);
        final TextView param_couchertext = (TextView)findViewById(R.id.param_couchertext); 
        //final TextView param_coucherpoint = (TextView)findViewById(R.id.param_coucherpoint);
        
        
        
        //Retour à l'accueil
        /*OnClickListener onClickLister = new OnClickListener() {
	 
	    	@Override
	    	public void onClick(View v){
	    		if (v.getId() == R.id.param_retour) {
		    		Intent intent = new Intent(Param.this, gSun.class);
					startActivity(intent);
		    		finish();
	    		}
	    	}	
	    };
	    
	    param_retour.setOnClickListener(onClickLister);*/
	
	    
	    
	    //Choix type date
	    OnItemSelectedListener onItemSelectedListenerPerso = new OnItemSelectedListener() {
	    	//@Override
        	//@SuppressWarnings("unchecked")
	    	public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
	    		switch ((int) parent.getItemIdAtPosition(position)) {
	    			case 0:
	    				//param_heure_spinner.setSelection(0);
	    				//param_heure_spinner.setVisibility(4);
	    				param_jour_spinner.setVisibility(4);
	    				param_mois_spinner.setVisibility(4);
		    			param_levertext.setVisibility(4);
		    			//param_leverpoint.setVisibility(4);
		    			param_lever.setVisibility(4);
		    			param_couchertext.setVisibility(4);
		    			//param_coucherpoint.setVisibility(4);
		    			param_coucher.setVisibility(4);
	    				break;
	    			case 1:
	    				//Solstice d'été
	    	    		param_jour_spinner.setSelection(3);
	        			param_mois_spinner.setSelection(6);
		    			param_jour_spinner.setVisibility(3);
		    			param_mois_spinner.setVisibility(3);
		    			param_levertext.setVisibility(3);
		    			//param_leverpoint.setVisibility(3);
		    			param_lever.setVisibility(3);
		    			param_couchertext.setVisibility(3);
		    			//param_coucherpoint.setVisibility(3);
		    			param_coucher.setVisibility(3);
		    			
		    			jour = 21;
		    			mois = 6;
		    			param_lever.setText("8:00");
		    			param_coucher.setText("19:00");
		    			//param_heure_spinner.setVisibility(3);
		    			break;
	    			case 2:	
	    				//Solstice d'hiver
	    	    		param_jour_spinner.setSelection(3);
	        			param_mois_spinner.setSelection(12);
	    				param_jour_spinner.setVisibility(3);
	    				param_mois_spinner.setVisibility(3);
		    			param_levertext.setVisibility(3);
		    			//param_leverpoint.setVisibility(3);
		    			param_lever.setVisibility(3);
		    			param_couchertext.setVisibility(3);
		    			//param_coucherpoint.setVisibility(3);
		    			param_coucher.setVisibility(3);
	    				jour = 21;
	    				mois = 12;
		    			param_lever.setText("8:00");
		    			param_coucher.setText("19:00");
		    			//param_heure_spinner.setVisibility(3);
	    				break;
	    			case 3:
	    				//Équinoxes
	    	    		param_jour_spinner.setSelection(3);
	        			param_mois_spinner.setSelection(3);
		    			param_jour_spinner.setVisibility(3);
		    			param_mois_spinner.setVisibility(3);
		    			param_levertext.setVisibility(3);
		    			//param_leverpoint.setVisibility(3);
		    			param_lever.setVisibility(3);
		    			param_couchertext.setVisibility(3);
		    			//param_coucherpoint.setVisibility(3);
		    			param_coucher.setVisibility(3);
		    			jour = 21;
		    			mois = 3;
		    			param_lever.setText("8:00");
		    			param_coucher.setText("19:00");
		    			//param_heure_spinner.setVisibility(3);
		    			break;
	    			case 4:	
	    	    		//choixdate_perso_jour_spinner.setSelection(0);
	        			//choixdate_perso_mois_spinner.setSelection(0);
	    				param_jour_spinner.setVisibility(3);
		    			//choixdate_perso_mois_spinner.setVisibility(4);
		    			//param_levertext.setVisibility(4);
		    			//param_lever.setVisibility(4);
		    			//param_couchertext.setVisibility(4);
		    			//param_coucher.setVisibility(4);
		    			//param_heure_spinner.setVisibility(4);
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
    			if(param_typedate_spinner.getSelectedItemPosition()==0 || param_typedate_spinner.getSelectedItemPosition()==4){
    	    		param_levertext.setVisibility(4);
    	    		//param_leverpoint.setVisibility(4);
        			param_lever.setVisibility(4);
        			param_couchertext.setVisibility(4);
        			//param_coucherpoint.setVisibility(4);
        			param_coucher.setVisibility(4);
        			//param_heure_spinner.setVisibility(4);
    				//param_heure_spinner.setVisibility(4);
    			}
	    		//param_levertext.setVisibility(3);
    			//param_lever.setVisibility(3);
    			//param_couchertext.setVisibility(3);
    			//param_coucher.setVisibility(3);
    			//param_heure_spinner.setVisibility(3);
				//param_heure_spinner.setVisibility(3);
				
	    		switch ((int) parent.getItemIdAtPosition(position)) {
	    		case 0:
	    			param_mois_spinner.setVisibility(4);
	    			break;
	    		case 1:
		    		jour = 1;
		    		param_mois_spinner.setVisibility(3);
		    		param_typedate_spinner.setSelection(4);
		    		break;
	    		case 2:
	    			jour = 11;
	    			param_mois_spinner.setVisibility(3);
	    			param_typedate_spinner.setSelection(4);
	    			break;
	    		case 3:
		    		jour = 21;
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
    			//param_leverpoint.setVisibility(4);
    			param_lever.setVisibility(4);
    			param_couchertext.setVisibility(4);
    			//param_coucherpoint.setVisibility(4);
    			param_coucher.setVisibility(4);
    			//param_heure_spinner.setVisibility(4);
    			
	    		   			
    			if(position == 0) {
	    			//param_heure_spinner.setVisibility(4);
	    		}else {
		    		mois = (int) parent.getItemIdAtPosition(position);
	    			param_levertext.setVisibility(3);
	    			//param_leverpoint.setVisibility(3);
	    			param_lever.setVisibility(3);
	    			param_couchertext.setVisibility(3);
	    			//param_coucherpoint.setVisibility(3);
	    			param_coucher.setVisibility(3);
	    			//param_heure_spinner.setVisibility(3);
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
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        int heu = heureLever-1;
        int nbMorceaux = heureCoucher - heureLever;
        int tailleMorceau = (int) (100.0 / nbMorceaux);
        int marche = tailleMorceau;
        boolean arretboucle = false;
        
        while(!arretboucle){
	        if(progress < marche) {
	        	heu++ ;
	        	arretboucle = true;
	        } else {
	        	heu++ ;
	        	marche = marche + tailleMorceau;
	        }
        }
        
        
    	mProgressText.setText("" + heu + ":00."/*+"=" + fromTouch*/);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    	//
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }
	    
}