package com.ei3info.gsun;

import java.util.Vector;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
//import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class EcranRecherche extends Activity implements OrientationListener{
	 
	protected static Preview mPreview;
	protected static Guide mGuide;
	protected static Bouton mBouton;
	protected static BoutonImage mBouton2;
	public static Vector<MediaPlayer> mMediaPlayer;
	 	
		/**
		 * Standard class that is called when an EcranRecherche is created (creation and definition of every element present in the window)
		 * @param savedInstanceState type Bundle, standard param.
		 */
	 	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        // Create the FrameLayout that contains all the element in the window
	        final FrameLayout frameLayout = new FrameLayout(this);
	        
	        // Creation the MediaPlayer that allows to play music (sounds giving the direction to follow UP/DOWN/SUN FOUND)
	        mMediaPlayer = prepareMediaplayer();
            
	        // Preview creation (camera display), Guide creation (arrows and text informations to find the sun), Buttons creation (Saving, Back, info)
	        mPreview = new Preview(this);
	        mGuide = new Guide(this);
	        mGuide.startListening(this, this);
	        	// Getting screen size
		        //Display ecran = getWindowManager().getDefaultDisplay(); 
		        //int largeur= ecran.getWidth();
	        mBouton = new Bouton(this,"Retour",0x50+0x01,80,30,0);
	        mBouton2 = new BoutonImage(this,R.drawable.info,35,0x30+0x05,0,0);
	        
	        //Back button action when clicked
	        mBouton.setOnClickListener(
	        	new OnClickListener() {
	    	        @Override
	    		    public void onClick(View v){
	    	        	//Stop MediaPLayer
	    	        	mMediaPlayer.get(0).stop();
	    	        	mMediaPlayer.get(1).stop();
	    	        	mMediaPlayer.get(2).stop();
	    	        	mMediaPlayer.get(3).stop();
	    	        	//Go to the Param Activity (definition of the parameters of the sun research -> previous screen)
	    	        	Intent intent = new Intent(EcranRecherche.this, Param.class);
	    				startActivity(intent);
	    				finish();
	    	        }
	        	}
	        );
	       
	       //Creation of the help text
	       AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(R.string.aide_texte)
	            .setCancelable(false)
	            .setTitle(R.string.aide_titre)
	            .setPositiveButton("Retour", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	                }
	            });
	        final AlertDialog alert = builder.create();
	       
	      //Help button action when clicked (display previous help text)
	       mBouton2.setOnClickListener(
		        	new OnClickListener() {
		    	        @Override
		    		    public void onClick(View v){
		    	        	alert.show();
		    	        }
		        	}
		   ); 
	       
	       	//Add all the element previously defined to the Framelayout
	        frameLayout.addView(mPreview);
	        frameLayout.addView(mGuide);
	        frameLayout.addView(mBouton);
	        frameLayout.addView(mBouton2);
	        
	        //Display the framelayout
	    	setContentView(frameLayout);
	    	
	    }
        
	 	/**
	 	 * Method that creates the Vector of Mediaplayers with all the necessary sounds for the application
	 	 * @return Vector that contains all tha mediaplayers containing the sounds needed in the application [down,up,sun_reache,down]
	 	 */
	 	
        public Vector<MediaPlayer> prepareMediaplayer(){
        	Vector<MediaPlayer> mp;
        	mp = new Vector<MediaPlayer>(0);
        	
        	//sound 1 : Go down
            mp.add(new MediaPlayer());
            Uri musicfile = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.bas);
            mp.get(0).reset();
            try {
                mp.get(0).setDataSource(getApplicationContext(), musicfile);
                mp.get(0).setLooping(true);
                mp.get(0).prepare();
                mp.get(1).start();
                mp.get(1).pause();
           } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
            	//sound 2 : Go up
            mp.add(new MediaPlayer());
            Uri musicfile2 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.haut);
            mp.get(1).reset();
            try {
                mp.get(1).setDataSource(getApplicationContext(), musicfile2);
                mp.get(1).setLooping(true);
                mp.get(1).prepare();
                mp.get(1).start();
                mp.get(1).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
            
          //sound 3 : Sun reached
            mp.add(new MediaPlayer());
            Uri musicfile3 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.soleil);
            mp.get(2).reset();
            try {
                mp.get(2).setDataSource(getApplicationContext(), musicfile3);
                mp.get(2).setLooping(true);
                mp.get(2).prepare();
                mp.get(2).start();
                mp.get(2).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
            
          //sound 4 : Go down
            mp.add(new MediaPlayer());
            Uri musicfile4 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.bas);
            mp.get(3).reset();
            try {
                mp.get(3).setDataSource(getApplicationContext(), musicfile4);
                mp.get(3).setLooping(true);
                mp.get(3).prepare();
                mp.get(3).start();
                mp.get(3).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
        	return mp;
        }
        
        //Methods needed because of "implement" but that do not need to be defined in our case   
        @Override  
        public void onOrientationChanged(float azimuth, float pitch, float roll) {} 
	 	@Override  
        public void GoLeft() {}
        @Override  
        public void GoRight() {}
        @Override  
        public void GoUp() {}
        @Override  
        public void GoDown() {}
        @Override  
        public void Ok() {}

}




