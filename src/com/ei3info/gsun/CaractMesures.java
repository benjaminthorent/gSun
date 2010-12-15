package com.ei3info.gsun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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

    private Integer[] Imgid = {
            R.drawable.chrysanthemum, R.drawable.desert, R.drawable.jellyfish, R.drawable.icon
    };
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.caractmesures);
	
		Button caractmesures_retour = (Button)findViewById(R.id.caractmesures_retour);
		OnClickListener onClickLister = new OnClickListener() {
            public void onClick(View v){
            	switch(v.getId()){
            	case R.id.caractmesures_retour:
            		Intent intent = new Intent(CaractMesures.this, PhotoCaracterisation.class);
        			startActivity(intent);
            		finish();
            		break;
            	}
            }
		};
		caractmesures_retour.setOnClickListener(onClickLister);
		
		
		imgView = (ImageView)findViewById(R.id.ImageView01);
        imgView.setImageResource(Imgid[0]);

         gallery = (Gallery) findViewById(R.id.caractmesures_gallery);
         gallery.setAdapter(new AddImgAdp(this));

         gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id) {
                imgView.setImageResource(Imgid[position]);
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
            return Imgid.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);

            imgView.setImageResource(Imgid[position]);
            imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgView.setBackgroundResource(GalItemBg);

            return imgView;
        }
    }
}
