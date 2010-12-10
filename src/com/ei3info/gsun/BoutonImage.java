package com.ei3info.gsun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.FrameLayout.LayoutParams;

public class BoutonImage extends ImageButton{

	public BoutonImage(Context context, int id, int taille, int gravity, int margin_right, int margin_top) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutParams l = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
    	        ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setImageBitmap(prepareBitmap(getResources().getDrawable(id), taille, taille));
		this.setBackgroundDrawable(null);
		l.gravity=gravity;
		this.setLayoutParams(l);
	}
	
	private static Bitmap prepareBitmap(Drawable drawable, int width, int height) {
	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    drawable.setBounds(0, 0, width, height);
	    Canvas canvas = new Canvas(bitmap);
	    drawable.draw(canvas);
	    return bitmap;
	}

}
