package com.ei3info.gsun;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AccesMesures extends Activity{
	
	protected static File repCourant;
	@SuppressWarnings({ "unchecked" })
	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.accesmesures);
		
		Button accesmesures_photo = (Button)findViewById(R.id.accesmesures_photo);
		Button accesmesures_synthese =(Button)findViewById(R.id.accesmesures_synthese);
		Button accesmesures_retour =(Button)findViewById(R.id.accesmesures_retour);
		
		final Spinner accesmesures_rep = (Spinner)findViewById(R.id.accesmesures_rep);
		ArrayList<String> listeRepertoires = new ArrayList<String>();
		listeRepertoires = Fichier.getAllDir();
		
		
		
	    @SuppressWarnings("rawtypes")
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, listeRepertoires);
	    accesmesures_rep.setAdapter(spinnerArrayAdapter);
	    
	    OnItemSelectedListener ecouteSpinner = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 AccesMesures.repCourant = Fichier.File((String) accesmesures_rep.getSelectedItem());
				 
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				AccesMesures.repCourant = Fichier.File("defaut");
			}
	    	
	    };
	    accesmesures_rep.setOnItemSelectedListener(ecouteSpinner);
	    
	    

	    	
		
		
		 OnClickListener onClickLister = new OnClickListener() {
			 
		    	@Override
		    	public void onClick(View v){
		    		switch(v.getId()){
			    	case R.id.accesmesures_retour:
				    	Intent intent1 = new Intent(AccesMesures.this, gSun.class);
						startActivity(intent1);
				    	finish();
			    	break;
			    	case R.id.accesmesures_synthese:
			    		AccesMesures.repCourant = Fichier.File((String) accesmesures_rep.getSelectedItem());
			    		//Toast.makeText(getBaseContext(), AccesMesures.repCourant.getAbsolutePath(), 1000).show();
			    		 
				    	Intent intent2 = new Intent(AccesMesures.this, SyntheseMesures.class);
						startActivity(intent2);
				    	finish();
				    break;
			    	
			    	 case R.id.accesmesures_photo:
			    		 AccesMesures.repCourant = Fichier.File((String) accesmesures_rep.getSelectedItem());
			    		 //Toast.makeText(getBaseContext(), AccesMesures.repCourant.getAbsolutePath(), 1000).show();
			    		 
			    		Intent intent3 = new Intent(AccesMesures.this, CaractMesures.class);
						startActivity(intent3);
				    	finish();
				    break;			    		
			    	}
		    	}
		 };
		
		//on affecte aux Button l'ÂŽcouteur d'ÂŽvÂ�nement
		 accesmesures_photo.setOnClickListener(onClickLister);
		 accesmesures_synthese.setOnClickListener(onClickLister);
		 accesmesures_retour.setOnClickListener(onClickLister);
		 
	}

}
