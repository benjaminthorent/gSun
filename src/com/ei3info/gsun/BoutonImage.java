package com.ei3info.gsun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.FrameLayout.LayoutParams;

/**
 * Class that allows to create a image button with specific characteristics
 * @author bthorent
 *
 */
public class BoutonImage extends ImageButton{

	/**
	 * Image Button Constructor
	 * @param context : corresponding to the current context in the application
	 * @param id : of the image to display as a button
	 * @param taille : width and height desired for the button
	 * @param gravity : position desired for the button (see android developers: layout parmaeters)
	 * @param margin_right : desired right margin (pixel)
	 * @param margin_top : desired top margin (pixel)
	 */
	public BoutonImage(Context context, int id, int taille, int gravity, int margin_right, int margin_top) {
		super(context);
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		//Define the image to display thanks to the method above
		this.setImageBitmap(prepareBitmap(getResources().getDrawable(id), taille, taille));
		//Don't display background under the image : only the image is visible !
		this.setBackgroundDrawable(null);
		//Definition of gravity <-> position of the image on the screen
		l.gravity=gravity;
		//Associate the layout parameters previously defined to the button
		this.setLayoutParams(l);
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
