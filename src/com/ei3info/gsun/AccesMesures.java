package com.ei3info.gsun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccesMesures extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.accesmesures);
		
		Button accesmesures_retour =(Button)findViewById(R.id.accesmesures_retour);
		
		 OnClickListener onClickLister = new OnClickListener() {
			 
		    	@Override
		    	public void onClick(View v){
		    		Intent intent = new Intent(AccesMesures.this, gSun.class);
					startActivity(intent);
		    		finish();
		    	}
		 };
		
		//on affecte aux Button l'couteur d'vnement
		 accesmesures_retour.setOnClickListener(onClickLister);
	}

}
