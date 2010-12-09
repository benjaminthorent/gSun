package com.ei3info.gsun;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.TableRow.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class SyntheseMesures extends Activity {

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.synthesemesures);
	        
	        
	        
	        TableLayout tl = (TableLayout)findViewById(R.id.synthese_tableau);
	        /* Create a new row to be added. */
	        TableRow tr = new TableRow(this);
	        tr.setLayoutParams(new LayoutParams(
	                       LayoutParams.FILL_PARENT,
	                       LayoutParams.WRAP_CONTENT));
	             /* Create a Button to be the row-content. */
	             TextView b = new TextView(this);
	             TextView c = new TextView(this);
	             c.setText("col2");
	            
	             
	             b.setText("col1");
	             b.setLayoutParams(new LayoutParams(
	                       LayoutParams.FILL_PARENT,
	                       LayoutParams.WRAP_CONTENT));
	             c.setLayoutParams(new LayoutParams(
	             LayoutParams.FILL_PARENT,
	             LayoutParams.WRAP_CONTENT));
	             /* Add Button to row. */
	             tr.addView(b);
	             tr.addView(c);
	             
	             
	   /* Add row to TableLayout. */
	            tl.addView(tr,new TableLayout.LayoutParams(
	            LayoutParams.FILL_PARENT,
	            LayoutParams.WRAP_CONTENT));
	            
	            
	            Button synthese_retour = (Button)findViewById(R.id.synthese_retour);
	            
	            OnClickListener onClickLister = new OnClickListener() {
	   			 
			    	@Override
			    	public void onClick(View v){
			    		//switch(v.getId()){
				    	//case R.id.synthese_retour :
					    	Intent intent1 = new Intent(SyntheseMesures.this, AccesMesures.class);
							startActivity(intent1);
					    	finish();
				    	//break;
				    	//}
			    	}
	            };
			    
	            synthese_retour.setOnClickListener(onClickLister);
	    
	    }  
}
