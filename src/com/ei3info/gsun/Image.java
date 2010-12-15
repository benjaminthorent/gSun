package com.ei3info.gsun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

/**
 * Class that allows to create a image with specific characteristics
 * @author bthorent
 *
 */
public class Image extends ImageView{

	/**
	 * Image Button Constructor
	 * @param context : corresponding to the current context in the application
	 * @param id : of the image to display as a button
	 * @param tailleX, tailleY : width and height desired for the button
	 * @param gravity : position desired for the button (see android developers: layout parmaeters)
	 * @param margin_right : desired right margin (pixel)
	 * @param margin_top : desired top margin (pixel)
	 */
	public Image(Context context, int id, int tailleX, int tailleY, int gravity, int margin_right, int margin_top) {
		super(context);
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		//Define the image to display thanks to the method above
		this.setImageBitmap(prepareBitmap(getResources().getDrawable(id), tailleX, tailleY));
		//Definition of gravity <-> position of the image on the screen
		l.gravity=gravity;
		//Associate the layout parameters previously defined to the button
		this.setLayoutParams(l);
	}
	
	/**
	 * Image Button Constructor
	 * @param context : corresponding to the current context in the application
	 * @param id : of the image to display as a button
	 * @param tailleX, tailleY : width and height desired for the button
	 * @param gravity : position desired for the button (see android developers: layout parmaeters)
	 * @param margin_right : desired right margin (pixel)
	 * @param margin_top : desired top margin (pixel)
	 */
	public Image(Context context, Bitmap image, int gravity, int margin_right, int margin_top) {
		super(context);

        int width = image.getWidth();
        int height = image.getHeight();
		
		// creates matrix for the manipulation
        Matrix matrix = new Matrix();
       

        // rotate the Bitmap
        matrix.postRotate(90);

        // recreate the new Bitmap
        Bitmap resizedPicture = Bitmap.createBitmap(image, 0, 0,
                      width, height, matrix, true);
        
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		//Define the image to display thanks to the method above
		this.setImageBitmap(resizedPicture);
		//Definition of gravity <-> position of the image on the screen
		l.gravity=gravity;
		//Associate the layout parameters previously defined to the button
		this.setLayoutParams(l);
	}
	
	/**
	 * Image Button Constructor
	 * @param context : corresponding to the current context in the application
	 * @param address : path of the image to display as a button
	 * @param tailleX, tailleY : width and height desired for the button
	 * @param gravity : position desired for the button (see android developers: layout parmaeters)
	 * @param margin_right : desired right margin (pixel)
	 * @param margin_top : desired top margin (pixel)
	 */
	public Image(Context context, int tailleX, int tailleY, int gravity, int margin_right, int margin_top) {
		super(context);
		
		//Test
		// try {
		
		/*	 
		FileInputStream fIn = EcranRecherche.mGuide.getContext().openFileInput(adresse);
        BufferedInputStream bufIn = new BufferedInputStream(fIn);
        Bitmap photoAafficher = BitmapFactory.decodeStream(bufIn);*/
        
		//TODO Shion classe
		Bitmap photoAafficher =	Fichier.getTempPicture(); 
        int width = photoAafficher.getWidth();
        int height = photoAafficher.getHeight();
        
        // creates matrix for the manipulation
        Matrix matrix = new Matrix();
       

        // rotate the Bitmap
        matrix.postRotate(90);

        // recreate the new Bitmap
        Bitmap resizedPicture = Bitmap.createBitmap(photoAafficher, 0, 0,
                      width, height, matrix, true);
        
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		//Define the image to display thanks to the method above
		this.setImageBitmap(resizedPicture);
		//Definition of gravity <-> position of the image on the screen
		l.gravity=gravity;
		//Associate the layout parameters previously defined to the button
		this.setLayoutParams(l);
		 /*}
		 catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }*/
	}
	
	
	/**
	 * Method to prepare an image in ressources as a bitmap
	 * @param drawable
	 * @param width desired
	 * @param height desired
	 * @return
	 */
	private static Bitmap prepareBitmap(Drawable drawable, int width, int height) {
	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    drawable.setBounds(0, 0, width, height);
	    Canvas canvas = new Canvas(bitmap);
	    drawable.draw(canvas);
	    return bitmap;
	}
	
}
