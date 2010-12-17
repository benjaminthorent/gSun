package com.ei3info.gsun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class PhotoCaracterisation extends Activity {
	
	protected static Preview2 mPreview;
	protected static Bouton mBouton;
	protected static Bouton mBouton2;
	protected static BoutonImage mBouton3;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create the FrameLayout that contains all the element in the window
        final FrameLayout frameLayout = new FrameLayout(this);
        
        // Preview creation (camera display), Guide creation (arrows and text informations to find the sun), Buttons creation (Saving, Back, info)
        mPreview = new Preview2(this);
        
        // Getting screen size
        Display ecran = getWindowManager().getDefaultDisplay();
        int largeur= ecran.getWidth();
        //Buttons : gravity=0x50  -> bottom of the screen
        mBouton = new Bouton(this,"Retour",0x50,100,30,largeur/2+20);
        mBouton2 = new Bouton(this,"Photo",0x50,100,30,-(largeur/2)+20);
        mBouton3 = new BoutonImage(this,R.drawable.info,35,0x30+0x05,0,0);
        
        //Back button action when clicked
        mBouton.setOnClickListener(
        	new OnClickListener() {
    	        @Override
    		    public void onClick(View v){
    	        	//Go to the Param Activity (definition of the parameters of the sun research -> previous screen)
    	        	Intent intent = new Intent(PhotoCaracterisation.this, CaractMesures.class);
    				startActivity(intent);
    				finish();
    	        }
        	}
        );
        
      //Photo button action when clicked
        mBouton2.setOnClickListener(
        	new OnClickListener() {
    	        @Override
    		    public void onClick(View v){
    	        	//Take the picture corresponding to the preview
    	        	mPreview.takePicture();
    	        	try{
    	        		Thread.currentThread();
    	        		Thread.sleep(3000);
    	        	}catch(InterruptedException e){
    	        		e.printStackTrace();
    	        	}
    	        	//Go to the Param Activity (definition of the parameters of the sun research -> previous screen)
    	        	Intent intent = new Intent(PhotoCaracterisation.this, CaractMesures.class);
    				startActivity(intent);
    				finish();
    	        }
        	}
        );
        
        //Creation of the help text
	       AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(R.string.aide_texte_ecran_prisePhotoCaract)
	            .setCancelable(false)
	            .setTitle(R.string.aide_titre)
	            .setPositiveButton("Retour", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	                }
	            });
	        final AlertDialog alert = builder.create();
	       
	      //Help button action when clicked (display previous help text)
	       mBouton3.setOnClickListener(
		        	new OnClickListener() {
		    	        @Override
		    		    public void onClick(View v){
		    	        	alert.show();
		    	        }
		        	}
		   );
        
      //Add all the element previously defined to the Framelayout
        frameLayout.addView(mPreview);
        frameLayout.addView(mBouton);
        frameLayout.addView(mBouton2);
        frameLayout.addView(mBouton3);
        
        //Display the framelayout
    	setContentView(frameLayout);
       

	}
}
