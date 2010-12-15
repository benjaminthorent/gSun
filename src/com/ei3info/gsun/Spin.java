package com.ei3info.gsun;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class Spin extends Spinner{
	
	/**
	 * Spin Constructor
	 * @param context : corresponding to the current context in the application
	 * @param id : of the image to display as a button
	 * @param tailleX, tailleY : width and height desired for the button
	 * @param gravity : position desired for the button (see android developers: layout parmaeters)
	 * @param margin_right : desired right margin (pixel)
	 * @param margin_top : desired top margin (pixel)
	 */
	public Spin(Context context, int tailleX, int tailleY) {
		super(context);
		//Layout parameters definition for the button
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		l.height=tailleX;
		//l.width=tailleY;
		//Associate the layout parameters previously defined to the button
		this.setLayoutParams(l);
	}

}
