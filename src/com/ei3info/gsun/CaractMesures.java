package com.ei3info.gsun;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class CaractMesures extends Activity{

    private Gallery gallery;
    private ImageView imgView;

    
   private ArrayList<Bitmap> images;
   
    /**private ArrayList<Bitmap> images = new ArrayList<Bitmap>();**/
    
    	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.caractmesures);
	
		images = Fichier.getAllCaracPic(AccesMesures.repCourant.getName());
	    /**Bitmap bmp = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
	    Drawable drawable2 = getResources().getDrawable(R.drawable.image_accueil_100x100);
	    drawable2.setBounds(0,0,100,100);
	    Canvas canvas2 = new Canvas(bmp);
	    drawable2.draw(canvas2);
	    images.add(bmp);**/
	    
		Button caractmesures_photo = (Button)findViewById(R.id.caractmesures_photo);
		Button caractmesures_retour = (Button)findViewById(R.id.caractmesures_retour);
		
		OnClickListener onClickLister = new OnClickListener() {
            public void onClick(View v){
            	switch(v.getId()){
            	case R.id.caractmesures_retour:
            		Intent intent = new Intent(CaractMesures.this, AccesMesures.class);
        			startActivity(intent);
            		finish();
            		break;
	            case R.id.caractmesures_photo:
	        		Intent intent2 = new Intent(CaractMesures.this, gSun.class);
	    			startActivity(intent2);
	        		finish();
	        		break;
	        	}
            }
		};
		caractmesures_retour.setOnClickListener(onClickLister);
		caractmesures_photo.setOnClickListener(onClickLister);
		
		
		imgView = (ImageView)findViewById(R.id.ImageView01);
        imgView.setImageBitmap(images.get(0));

         gallery = (Gallery) findViewById(R.id.caractmesures_gallery);
         gallery.setAdapter(new AddImgAdp(this));

         gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imgView.setImageBitmap(images.get(position));
            }
        });
	}
	
    public class AddImgAdp extends BaseAdapter {
        int GalItemBg;
        private Context cont;

        public AddImgAdp(Context c) {
            cont = c;
            TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
            GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
            typArray.recycle();
        }

        public int getCount() {
            return images.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);

            imgView.setImageBitmap(images.get(position));
            imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgView.setBackgroundResource(GalItemBg);

            return imgView;
        }
    }
}
