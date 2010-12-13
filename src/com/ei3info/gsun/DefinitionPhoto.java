package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.graphics.BitmapFactory;


public class DefinitionPhoto extends Activity {
	
	protected static Bouton mBoutonSave;
	protected static Bouton mBoutonRetour;
	protected static ImageView photoFondEcran;
	protected static ImageView imageSoleil;
	private Bitmap soleil;
	private int Soleilh;       //Sun height
	private int Soleill;       //Sun width
	
	/**
	 * Method to prepare an image in ressources as a bitmap
	 * @param drawable
	 * @param width desired
	 * @param height desired
	 * @return
	 */
	private static Bitmap prepareBitmap(Drawable drawable, int width, int height) {
		 Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		 drawable.setBounds(100-width/2,100+height/2,100+width/2,100-height/2);
		 Canvas canvas = new Canvas(bitmap);
		 drawable.draw(canvas);
		 return bitmap;
	}
	
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
        int hauteur= ecran.getHeight();
        
        //Buttons
        mBoutonRetour = new Bouton(this,"Retour",0,100,30,largeur/2+20);
        mBoutonSave = new Bouton(this,"Enregistrer",0,100,30,-(largeur/2)+20);
        
        
		try {
			
			//Background Picture
				FileInputStream fIn = EcranRecherche.mGuide.getContext().openFileInput("photo1nom");
				BufferedInputStream bufIn = new BufferedInputStream(fIn);
				Bitmap photoAafficher = BitmapFactory.decodeStream(bufIn);
			
				int width = photoAafficher.getWidth();
	        	int height = photoAafficher.getHeight();
			
	        	// calculate the scale 
	        	float scaleWidth = ((float) largeur) / width;
	        	float scaleHeight = ((float) hauteur) / height;
				// creates matrix for the manipulation
	        	Matrix matrix = new Matrix();
	        	// resize the bit map
	        	matrix.postScale(scaleWidth,scaleHeight);
			
	        	// rotate the Bitmap
	        	matrix.postRotate(90);
	 
	        	// recreate the new Bitmap
	        	Bitmap resizedPicture = Bitmap.createBitmap(photoAafficher, 0, 0, 
	                          width, height, matrix, true); 
	        
	        	ImageView viewPhoto = new ImageView(this);
				viewPhoto.setImageBitmap(resizedPicture);
			
			//Sun
				Soleilh = 120;
			    Soleill = 120;
			    soleil = prepareBitmap(getResources().getDrawable(R.drawable.soleil_100x100), Soleill,
			    		Soleilh);
			    ImageView viewSun = new ImageView(this);
				viewSun.setImageBitmap(soleil);
			    
			
			//Add all the element previously defined to the Framelayout
			frameLayout.addView(viewPhoto);
			frameLayout.addView(viewSun);
	        frameLayout.addView(mBoutonSave);
	        frameLayout.addView(mBoutonRetour);
	        
	        
	      //Display the framelayout
			this.setContentView(frameLayout);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Back button action when clicked
        mBoutonRetour.setOnClickListener(
        	new OnClickListener() {
    	        @Override
    		    public void onClick(View v){
    	        	//Go to the Param Activity (definition of the parameters of the sun research -> previous screen)
    	        	Intent intent = new Intent(DefinitionPhoto.this, EcranRecherche.class);
    				startActivity(intent);
    				finish();
    	        }
        	}
        );
       
        //Saving button action when clicked
       mBoutonSave.setOnClickListener(
	        	new OnClickListener() {
	    	        @Override
	    		    public void onClick(View v){
	    	        	//Go to the Enregistrement Activity (screen where the user must say whether the sun is visible or not)
	    	        	Intent intent2 = new Intent(DefinitionPhoto.this, gSun.class); //TODO "gSun" must be changed to "Enregistrement"
	    				startActivity(intent2);
	    				finish();
	    	        }
	        	}
	   );
        
	

}
	
}
