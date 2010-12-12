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
		
		Button accesmesures_photo = (Button)findViewById(R.id.accesmesures_photo);
		Button accesmesures_synthese =(Button)findViewById(R.id.accesmesures_synthese);
		Button accesmesures_retour =(Button)findViewById(R.id.accesmesures_retour);
		
		
		 OnClickListener onClickLister = new OnClickListener() {
			 
		    	@Override
		    	public void onClick(View v){
		    		switch(v.getId()){
			    	case R.id.accesmesures_retour:
				    	Intent intent1 = new Intent(AccesMesures.this, gSun.class);
						startActivity(intent1);
				    	finish();
			    	break;
			    	case R.id.accesmesures_synthese:
				    	Intent intent2 = new Intent(AccesMesures.this, SyntheseMesures.class);
						startActivity(intent2);
				    	finish();
				    break;
			    	
			    	 case R.id.accesmesures_photo:
			    		
			    		 Intent intent3 = new Intent(AccesMesures.this, CaractMesures.class);
						startActivity(intent3);
				    	finish();
				    break;			    		
			    	}
		    	}
		 };
		
		//on affecte aux Button l'ÂŽcouteur d'ÂŽvÂ�nement
		 accesmesures_photo.setOnClickListener(onClickLister);
		 accesmesures_synthese.setOnClickListener(onClickLister);
		 accesmesures_retour.setOnClickListener(onClickLister);
		 
	}

}
