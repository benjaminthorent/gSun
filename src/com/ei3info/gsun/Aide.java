package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Aide extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.aide);
		
		Button aide_retour =(Button)findViewById(R.id.aide_retour);
		
		 OnClickListener onClickLister = new OnClickListener() {
			 
		    	@Override
		    	public void onClick(View v){
		    		Intent intent = new Intent(Aide.this, gSun.class);
					startActivity(intent);
		    		finish();
		    	}
		 };
		
		//on affecte aux Button l'couteur d'vnement
		aide_retour.setOnClickListener(onClickLister);
	}

}
