package com.ei3info.gsun;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
//import android.widget.Toast;

public class EcranRecherche extends Activity implements OrientationListener{
	 
	private Preview mPreview;
	private Guide mGuide;
	private Bouton mBouton;
	public static Vector<MediaPlayer> mMediaPlayer;
	 	
	 	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        final FrameLayout frameLayout = new FrameLayout(this);
	        /*
	        //Mediaplayer
	        mMediaPlayer = new Vector(0);
	        	//son 1
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.test);
            mMediaPlayer.get(0).reset();
            try {
                mMediaPlayer.get(0).setDataSource(getApplicationContext(), musicfile);
                mMediaPlayer.get(0).prepare();
           } catch (IllegalArgumentException e) {
                e.printStackTrace();
           } catch (SecurityException e) {
                e.printStackTrace();
           } catch (IllegalStateException e) {
                e.printStackTrace();
           } catch (IOException e) {
                e.printStackTrace();
           }
            	//son 2
            mMediaPlayer.add(new MediaPlayer());
            Uri musicfile2 = Uri.parse("android.resource://" +
                    getPackageName() + "/" + R.raw.test2);
            mMediaPlayer.get(1).reset();
            try {
                mMediaPlayer.get(1).setDataSource(getApplicationContext(), musicfile2);
                mMediaPlayer.get(1).prepare();
           } catch (IllegalArgumentException e) {
                e.printStackTrace();
           } catch (SecurityException e) {
                e.printStackTrace();
           } catch (IllegalStateException e) {
                e.printStackTrace();
           } catch (IOException e) {
                e.printStackTrace();
           }*/
            
	        // Initialisation des differents elements de l'affichage
	        mPreview = new Preview(this);
	        mGuide = new Guide(this);
	        mGuide.startListening(this, this);
	        //Fin Capteur
	        
	        mBouton = new Bouton(this,"Retour");
	       
	        mBouton.setOnClickListener(
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
	        frameLayout.addView(mGuide);
	        frameLayout.addView(mBouton);
	    	
	        
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
