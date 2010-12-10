package com.ei3info.gsun;

import java.util.Vector;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EcranRecherche extends Activity implements OrientationListener{
	 
	protected static Preview mPreview;
	protected static Guide mGuide;
	protected static Bouton mBouton;
	protected static Bouton mBouton2;
	protected static BoutonImage mBouton3;
	public static Vector<MediaPlayer> mMediaPlayer;
	 	
	 	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        final FrameLayout frameLayout = new FrameLayout(this);
	        
	        //Initialisation Mediaplayer
	        mMediaPlayer = prepareMediaplayer();
            
	        // Initialisation des differents elements de l'affichage
	        mPreview = new Preview(this);
	        mGuide = new Guide(this);
	        mGuide.startListening(this, this);
	        //Fin Capteur
	        Display ecran = getWindowManager().getDefaultDisplay(); 
	        int largeur= ecran.getWidth();
	        mBouton = new Bouton(this,"Retour",100,30,largeur/2+20);
	        mBouton2 = new Bouton(this,"Enregistrer",100,30,-(largeur/2)+20);
	        mBouton3 = new BoutonImage(this,R.drawable.info,35,0x30+0x05,0,0);
	        
	        mBouton.setOnClickListener(
	        	new OnClickListener() {
	    	        @Override
	    		    public void onClick(View v){
	    	        	mMediaPlayer.get(0).stop();
	    	        	mMediaPlayer.get(1).stop();
	    	        	mMediaPlayer.get(2).stop();
	    	        	mMediaPlayer.get(3).stop();
	    	        	Intent intent = new Intent(EcranRecherche.this, Param.class);
	    				startActivity(intent);
	    				finish();
	    	        }
	        	}
	        );
	       
	       mBouton2.setOnClickListener(
		        	new OnClickListener() {
		    	        @Override
		    		    public void onClick(View v){
		    	        	mPreview.takePicture();
		    	        	/*while(!mPreview.test){
		    	        		Toast.makeText(mGuide.getContext(), "attente", 1000);
		    	        	}*/
		    	        	//TODO Enregistrer au bon endroit & Réaliser traitement image (soleil au centre)
		    	        	try{
		    	        		Thread.currentThread();
		    	        		Thread.sleep(500);
		    	        	}catch(InterruptedException e){
		    	        		e.printStackTrace();
		    	        	}
		    	        	Intent intent2 = new Intent(EcranRecherche.this, Enregistrement.class);
		    				startActivity(intent2);
		    				finish();
		    	        }
		        	}
		   ); 
	       
	       //Génération de l'aide
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
	        
	       mBouton3.setOnClickListener(
		        	new OnClickListener() {
		    	        @Override
		    		    public void onClick(View v){
		    	        	alert.show();
		    	        }
		        	}
		   ); 
	       
	        frameLayout.addView(mPreview);
	        frameLayout.addView(mGuide);
	        frameLayout.addView(mBouton);
	        frameLayout.addView(mBouton2);
	        frameLayout.addView(mBouton3); 
	        
	    	setContentView(frameLayout);
	    	
	    }
        
        public Vector<MediaPlayer> prepareMediaplayer(){
        	Vector<MediaPlayer> mp;
        	mp = new Vector<MediaPlayer>(0);
        	
        	//son 1
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
            	//son 2
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
          //son 3
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
          //son 3
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
        
        //Méthodes n'ayant pas besoin d'être implantées dans notre cas
        
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




