package com.ei3info.gsun;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class SyntheseMesures extends Activity {

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.synthesemesures);
            
	        
	        Button l1c1 = (Button)findViewById(R.id.synthese_l1c1);
            l1c1.setText("Paris");

            @SuppressWarnings("unused")
			ImageView image = (ImageView)findViewById(R.drawable.soleil);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("hello");
            //builder.setView(image);
            builder.setCancelable(false);
            builder.setPositiveButton("Retour", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    }
                });
            final AlertDialog alert = builder.create();
            
            l1c1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alert.show();
                }
                });
            /**l1c1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //ouverture de la femetre widget  	 
               	 Intent intent = new Intent(HelloTableLayout.this, EcranBis.class);
	 				startActivity(intent);
	 		    	finish();
                }
            });**/
   
            TextView l1c2 = (TextView)findViewById(R.id.synthese_l1c2);
            l1c2.setText("11/09");
            TextView l1c3 = (TextView)findViewById(R.id.synthese_l1c3);
            l1c3.setText("15h");
            
           final Spinner etat_spinner=(Spinner) findViewById(R.id.synthese_l1c4);
			ArrayAdapter<CharSequence> etat_adapter = ArrayAdapter.createFromResource(this, R.array.etat_array, android.R.layout.simple_spinner_item);
			etat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			etat_spinner.setAdapter(etat_adapter);
            
            //ImageView l1c4 = (ImageView)findViewById(R.id.synthese_l1c4);
            //l1c4.setImageResource(R.drawable.soleil);
            TextView l1c5 = (TextView)findViewById(R.id.synthese_l1c5);
            l1c5.setText("7¡");
	            
	            
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
