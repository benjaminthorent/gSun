package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EcranRecherche extends Activity implements OrientationListener{
	 
	private Preview mPreview;
	 	
	 	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        final FrameLayout frameLayout = new FrameLayout(this);
	        
	        // Initialisation des differents elements de l'affichage
	        mPreview = new Preview(this);
	        Guide fleches = new Guide(this);
	        fleches.startListening(this, this);
	        //Fin Capteur
	        
	        
	        Bouton b = new Bouton(this,"Retour");
	       
	        b.setOnClickListener(
	        	new OnClickListener() {
	    	        @Override
	    		    public void onClick(View v){
	    	        	Intent intent = new Intent(EcranRecherche.this, gSun.class);
	    				startActivity(intent);
	    				finish();
	    	        }
	        	}
	        );
	       
	        frameLayout.addView(mPreview);
	        frameLayout.addView(fleches);
	        frameLayout.addView(b);
	    	
	        
	    	setContentView(frameLayout);
	    	
	    }

	 	@Override  
        public void onOrientationChanged(float azimuth,   
                float pitch, float roll) {  
            /*((TextView) findViewById(R.id.azimuth)).setText(  
                    String.valueOf(azimuth));  
            ((TextView) findViewById(R.id.pitch)).setText(  
                    String.valueOf(pitch));  
            ((TextView) findViewById(R.id.roll)).setText(  
                    String.valueOf(roll));  */
        } 
	 	
	 	@Override  
        public void GoLeft() {  
            Toast.makeText(this, "Go Left !", 1000).show();
        }  
       
        @Override  
        public void GoRight() {  
            Toast.makeText(this, "Go Right !", 1000).show();  
        }  
       
        @Override  
        public void GoUp() {  
            Toast.makeText(this, "Go Up !", 1000).show();  
        }  
       
        @Override  
        public void GoDown() {  
            Toast.makeText(this, "Go Down !", 1000).show();  
        } 
        
        @Override  
        public void Ok() {  
            Toast.makeText(this, "Well done !", 1000).show();  
        }
}
