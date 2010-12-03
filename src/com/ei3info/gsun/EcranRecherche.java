package com.ei3info.gsun;

import java.util.Vector;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EcranRecherche extends Activity implements OrientationListener{
	 
	private Preview mPreview;
	private Guide mGuide;
	private Bouton mBouton;
	private Bouton mBouton2;
	public static Vector<MediaPlayer> mMediaPlayer;
	 	
	 	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        final FrameLayout frameLayout = new FrameLayout(this);
	        
	        //Mediaplayer
	        mMediaPlayer = new Vector<MediaPlayer>(0);
	        	//son 1
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.bas);
            mMediaPlayer.get(0).reset();
            try {
                mMediaPlayer.get(0).setDataSource(getApplicationContext(), musicfile);
                mMediaPlayer.get(0).setLooping(true);
                mMediaPlayer.get(0).prepare();
                mMediaPlayer.get(1).start();
                mMediaPlayer.get(1).pause();
           } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
            	//son 2
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile2 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.haut);
            mMediaPlayer.get(1).reset();
            try {
                mMediaPlayer.get(1).setDataSource(getApplicationContext(), musicfile2);
                mMediaPlayer.get(1).setLooping(true);
                mMediaPlayer.get(1).prepare();
                mMediaPlayer.get(1).start();
                mMediaPlayer.get(1).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
          //son 3
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile3 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.soleil);
            mMediaPlayer.get(2).reset();
            try {
                mMediaPlayer.get(2).setDataSource(getApplicationContext(), musicfile3);
                mMediaPlayer.get(2).setLooping(true);
                mMediaPlayer.get(2).prepare();
                mMediaPlayer.get(2).start();
                mMediaPlayer.get(2).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
          //son 3
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile4 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.bas);
            mMediaPlayer.get(3).reset();
            try {
                mMediaPlayer.get(3).setDataSource(getApplicationContext(), musicfile4);
                mMediaPlayer.get(3).setLooping(true);
                mMediaPlayer.get(3).prepare();
                mMediaPlayer.get(3).start();
                mMediaPlayer.get(3).pause();
            } catch (Exception e) {
                System.out.println("Erreur survenue : " + e);
           }
            
	        // Initialisation des differents elements de l'affichage
	        mPreview = new Preview(this);
	        mGuide = new Guide(this);
	        mGuide.startListening(this, this);
	        //Fin Capteur
	        Display ecran = getWindowManager().getDefaultDisplay(); 
	        int largeur= ecran.getWidth();
	        mBouton = new Bouton(this,"Retour",15,largeur/2+20);
	        mBouton2 = new Bouton(this,"Photo",15,30);
	        mBouton.setOnClickListener(
	        	new OnClickListener() {
	    	        @Override
	    		    public void onClick(View v){
	    	        	mMediaPlayer.get(0).stop();
	    	        	mMediaPlayer.get(1).stop();
	    	        	mMediaPlayer.get(2).stop();
	    	        	mMediaPlayer.get(3).stop();
	    	        	//mMediaPlayer.get(0).release();
	    	        	//mMediaPlayer.get(1).release();
	    	        	//mMediaPlayer.get(2).release();
	    	        	//mMediaPlayer.get(3).release();
	    	        	Intent intent = new Intent(EcranRecherche.this, gSun.class);
	    				startActivity(intent);
	    				finish();
	    	        }
	        	}
	        );
	       
	       //TODO Pour Shion, méthodes à décommenter pour la prise de photo
	       mBouton2.setOnClickListener(
		        	new OnClickListener() {
		    	        @Override
		    		    public void onClick(View v){
		    	        	//mCamera.takePicture(null, mPictureCallback, mPictureCallback);
		    	        	//Toast.makeText(null, "Le petit oiseau va sortir !", 1000).show();
		    	        	//mPreview.takePicture();
		    	        	//Toast.makeText(null, "Photo prise !", 1000).show();
		    	        }
		        	}
		   );      
	       
	        frameLayout.addView(mPreview);
	        frameLayout.addView(mGuide);
	        frameLayout.addView(mBouton);
	        frameLayout.addView(mBouton2);   	
	        
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
            //Toast.makeText(this, "Go Left !", 1000).show();
        }  
       
        @Override  
        public void GoRight() {  
            //Toast.makeText(this, "Go Right !", 1000).show();  
        }  
       
        @Override  
        public void GoUp() {  
            //Toast.makeText(this, "Go Up !", 1000).show();  
        }  
       
        @Override  
        public void GoDown() {  
            //Toast.makeText(this, "Go Down !", 1000).show();  
        } 
        
        @Override  
        public void Ok() {  
            //Toast.makeText(this, "Well done !", 1000).show();  
        }

}


