package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class AffichagePhotoSynthese extends Activity {
	
	 	protected static Bouton mBoutonRetour;
	    protected static Image viewPhoto;
	    
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        // Create the FrameLayout that contains all the element in the window
	        final FrameLayout frameLayout = new FrameLayout(this);

	        // Getting screen size
	        Display ecran = getWindowManager().getDefaultDisplay();
	        int largeur= ecran.getWidth();

	        //Buttons : gravity=0x50  -> bottom of the screen
	        mBoutonRetour = new Bouton(this,"Retour",0x50,100,30,largeur/2+20);


	        

	            //Background Picture
	        	 	Image viewPhoto = new Image(this,SyntheseMesures.imageAAfficher,0x70+0x07,0,0);
	        	 	
	                

	            //Sun
	                Image viewSun = new Image(this,R.drawable.soleil_100x100,100,100,0x10+0x01,0,0);




	            //Add all the element previously defined to the Framelayout
	            frameLayout.addView(viewPhoto);
	            frameLayout.addView(viewSun);
	            frameLayout.addView(mBoutonRetour);


	          //Display the framelayout
	            this.setContentView(frameLayout);


	        //Back button action when clicked
	        mBoutonRetour.setOnClickListener(
	            new OnClickListener() {
	                @Override
	                public void onClick(View v){
	                    //Go to the Param Activity (definition of the parameters of the sun research -> previous screen)
	                    Intent intent = new Intent(AffichagePhotoSynthese.this, SyntheseMesures.class);
	                    startActivity(intent);
	                    finish();
	                }
	            }
	        );

}
}
	    
