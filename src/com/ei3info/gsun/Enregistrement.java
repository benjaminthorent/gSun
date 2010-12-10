package com.ei3info.gsun;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Enregistrement extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		try {
			FileInputStream fIn = EcranRecherche.mGuide.getContext().openFileInput("photo1nom");
			BufferedInputStream bufIn = new BufferedInputStream(fIn);
			Bitmap photoAafficher = BitmapFactory.decodeStream(bufIn);
			ImageView ViewPhoto = new ImageView(this);
			ViewPhoto.setImageBitmap(photoAafficher);
			this.setContentView(ViewPhoto);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}

