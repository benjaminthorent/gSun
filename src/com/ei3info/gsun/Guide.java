package com.ei3info.gsun;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Manages all the information concerning directions on the Research screen
 * @author bthorent
 *
 */
public class Guide extends View {
	
	//Log parameters
	private static final boolean LOG_ENABLED = true;
	private static final String LOG_TAG = "ObjectsView";
	
	//Images displayed
	private Bitmap FlecheGauche; //Left Arrow
	private Bitmap FlecheDroite; //Right Arrow
	private Bitmap FlecheHaut;   //Up Arrow
	private Bitmap FlecheBas;    //Bottom Arrow
	private Bitmap Soleil;       //Sun
	 
	//Array to know if an element has to be displayed (1) or not (0)
	private static int[] etat_affichage = {0,0,0,0,0};  //{left,right;up,down,sun}
	 
	//Width and Height for the different pictures
	private int flechesGDh;    //Left and right arrows height
	private int flechesGDl;    //Left and right arrows width
	@SuppressWarnings("unused")
	private int flechesHBh;    //Up and Bottom arrows height
	private int flechesHBl;    //Up and Bottom arrows width
	private int Soleilh;       //Sun height
	private int Soleill;       //Sun width
	 
	//Pictures position (Left Up corner)
	private float FGX;    //Left arrow X position   
	private float FGY;    //Left arrow Y position   
	private float FDX;    //right arrow X position
	private float FDY;    //right arrow Y position
	private float FHX;    //Up arrow X position
	private float FHY;    //Up arrow Y position
	private float FBX;    //Bottom arrow X position
	private float FBY;    //Bottom arrow Y position
	private float SX;     //Sun X position 
	private float SY;     //Sun Y position
	
	//Array with the text to display
	public String[] texte_guide;  //(left,right,up,down)

	//Sensors and listener needed to display the information when needed
	private static Sensor sensor;  
    private static SensorManager sensorManager;    
    private static OrientationListener listener;
    
    private static boolean running = false;
    
    //Azimuth and pitch to reach
    private int azimuth_objectif;
    private int pitch_objectif;
    
    //Time information to help to take picture when sun was found 2 seconds ago
    private boolean temps_ok;
    private long start;
    private boolean photo_non_prise;

    /** Direction to give to the phone to reach the aim */  
    enum Direction {  
        LEFT,   
        RIGHT,  // Go right
        UP,     // Go up
        DOWN,   // Go down
        OK;     // Aim reached  
    } 
	
	
	/**
	 * Guide constructor
	 * @param context corresponding to the application context
	 */
	public Guide(Context context) {
	    
		super(context);
		
		//Setting pictures sizes
	    flechesGDh = 80;
	    flechesGDl = 60;
	    flechesHBh = 40;
	    flechesHBl = 80;
	    Soleilh = 120;
	    Soleill = 120;
	    
	    //Setting pictures files and creating corresponding bitmaps
	    FlecheGauche = prepareBitmap(getResources().getDrawable(R.drawable.fg), flechesGDl,
	    		flechesGDh);
	    FlecheDroite = prepareBitmap(getResources().getDrawable(R.drawable.fd), flechesGDl,
	    		flechesGDh);
	    FlecheHaut = prepareBitmap(getResources().getDrawable(R.drawable.fh), flechesHBl,
	    		flechesGDh);
	    FlecheBas = prepareBitmap(getResources().getDrawable(R.drawable.fb), flechesHBl,
	    		flechesGDh);
	    Soleil = prepareBitmap(getResources().getDrawable(R.drawable.soleil_100x100), Soleill,
	    		Soleilh);
	    
	    // Convert angle values to smartphone position
	    // --> The smartphone must be perpendicular to our position in reference to the given pitch that aims to the sun
	    // --> The azimuth obtained gives the angle to South whereas azimuth=0 from captors aim to North
	    azimuth_objectif = (180 + (int) gSun.calcul.getAzimut())%360;
	    pitch_objectif = (-90-(int) gSun.calcul.getHauteurSolaire());
	    //Toast.makeText(getContext(), azimuth_objectif + " et " + pitch_objectif, 1000).show();
	    
	    //Creating text information array
	    texte_guide = new String[4];
	    texte_guide[0]="";
	    texte_guide[1]="";
	    texte_guide[2]="";
	    texte_guide[3]="";
	    
	    temps_ok=false;
	    photo_non_prise=true;
		
	}
	
	// Setters
	/**
	 * To set the text to display on the screen (angle left to reach the objective position)
	 * @param texte_gauche : text corresponding to the left arrow
	 * @param texte_droit : text corresponding to the right arrow
	 * @param texte_haut : text corresponding to the up arrow
	 * @param texte_bas : text corresponding to the bottom arrow
	 */
	public void setTextes(String texte_gauche,String texte_droit,String texte_haut,String texte_bas){
		texte_guide = new String[4];
		texte_guide[0]=texte_gauche;
		texte_guide[1]=texte_droit;
		texte_guide[2]=texte_haut;
		texte_guide[3]=texte_bas;
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
	 
	    @Override
	    /**
	     * Method called when Guide is created
	     */
	    protected void onDraw(Canvas canvas) {
	    	
	    	//Define arrows positions
		    FGX = 5;
		    FGY = canvas.getHeight()-flechesGDh-20-EcranRecherche.mBouton.getHeight();
		    FDX = canvas.getWidth()-flechesGDl-5;
		    FDY = canvas.getHeight()-flechesGDh-20-EcranRecherche.mBouton.getHeight();
		    FHX = canvas.getWidth()/2-flechesHBl/2;
		    FHY = 5;
		    FBX = canvas.getWidth()/2-flechesHBl/2;
		    FBY = canvas.getHeight()-flechesHBl-15-EcranRecherche.mBouton.getHeight();
		    SX = canvas.getWidth()/2-Soleill/2;
		    SY = canvas.getHeight()/2-Soleilh/2;
		    
		    //Draw arrows according to the etat_affichage array state 
		  //Array to know if an element has to be displayed (1) or not (0) {left,right;up,down,sun}
	    	if (etat_affichage[0]==1) {
	            canvas.drawBitmap(FlecheGauche, FGX, FGY, null);
	        };
	        if (etat_affichage[1]==1) {
	            canvas.drawBitmap(FlecheDroite, FDX, FDY, null);
	        };
	        if (etat_affichage[2]==1) {
	            canvas.drawBitmap(FlecheHaut, FHX, FHY, null);
	        };
	        if (etat_affichage[3]==1) {
	            canvas.drawBitmap(FlecheBas, FBX, FBY, null);
	        };
	        if (etat_affichage[4]==1) {
	            canvas.drawBitmap(Soleil, SX, SY, null);
	        };
	        
	        //Define Color and style for text
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.RED);
			//draw texts
			canvas.drawText(texte_guide[0], 8+flechesGDl/2, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte gauche
			canvas.drawText(texte_guide[1], canvas.getWidth()-18-flechesGDl/2, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte droit
			canvas.drawText(texte_guide[2], canvas.getWidth()/2-5, flechesHBl + 15, paint);  //Texte haut
			canvas.drawText(texte_guide[3], canvas.getWidth()/2-5, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte bas
			
			//Display arrows and text
	    	super.onDraw(canvas);
	    }
	 
	   
	 
	   
	  
	    /** 
	     * Returns true if the manager is listening to orientation changes 
	     */  
	    public boolean isListening() {  
	        return running;  
	    }  
	   
	    /** 
	     * Unregisters listeners 
	     */  
	    public void stopListening() {  
	        running = false;  
	        try {  
	            if (sensorManager != null && getSensorEventListener() != null) {  
	                sensorManager.unregisterListener(getSensorEventListener());  
	            }  
	        } catch (Exception e) {}  
	    }  
	
	    /** 
	     * Registers a listener and start listening 
	     */  
	    public void startListening(Context context,  
	            OrientationListener orientationListener) {  
	        sensorManager = (SensorManager) context  
	                .getSystemService(Context.SENSOR_SERVICE);  
	        List<Sensor> sensors = sensorManager.getSensorList(  
	                Sensor.TYPE_ORIENTATION);  
	        if (sensors.size() > 0) {  
	            sensor = sensors.get(0);  
	            running = sensorManager.registerListener(  
	                    getSensorEventListener(), sensor,   
	                    SensorManager.SENSOR_DELAY_FASTEST);  
	            listener = orientationListener;  
	        }  
	    }  
	   
	    /** 
	     * The listener that listen to events from the orientation listener 
	     */  
	    private SensorEventListener sensorEventListener = new SensorEventListener() {  
	   
	        /** Initialize all the element */  
	    	//Directions as defined in the parameters (UP,DOWN,LEFT,RIGHT,SUN_FOUND)
	        private Direction currentDirection = null;  
	        private Direction oldDirection = null;  
	        //Current azimuth and pitch
	        private float azimuth;  
	        private float pitch;  
	        private float roll;   
	        //Desired precision for azimuth and pitch
	        private int precision_azimuth;
            private	int precision_pitch; 
	   
            /**
             * Method called when accuracy changes
             */
	        public void onAccuracyChanged(Sensor sensor, int accuracy) {}  
	   
	        /**
	         * Method called when sensor return different informations (moving the smartphone)
	         */
	        public void onSensorChanged(SensorEvent event) {  
	   
	        	//Getting azimuth, pitch and roll
	        	azimuth = event.values[0];     // azimuth  
	            pitch = event.values[1];     // pitch  
	            roll = event.values[2];        // roll  
	            
	   
	            //Link between Param screen and accuracy value
	            //Defining accuracy
	            //From param parameters and choices : values = 1,2,3 -> accuracy(¬∞) = 4,7,10
	            switch(gSun.precision){
	            case 1 :
	            	precision_azimuth = 10;
	            	precision_pitch = 10;
	            case 2 :
	            	precision_azimuth = 7;
	            	precision_pitch = 7;
	            case 3 :
	            	precision_azimuth = 4;
	            	precision_pitch = 4;
	            }
	            
	            //Actions according to the azimuth and pitch
	            if (azimuth > azimuth_objectif+precision_azimuth) {  
	                // GoLeft  
	            	//Define text to display
	            	texte_guide[0]=String.valueOf((int)(Math.abs(azimuth-azimuth_objectif)));
                    texte_guide[1]="";
                    texte_guide[2]="";
                    texte_guide[3]="";
                    //To stop counting time allowing to take picture after a given time on the correct position
                    temps_ok=false;
                    //Define direction
	                currentDirection = Direction.LEFT;  
	            } else if (azimuth < azimuth_objectif-precision_azimuth) {  
	                // GoRight 
	            	//Define text to display
	            	texte_guide[0]="";
                    texte_guide[1]=String.valueOf((int)(Math.abs(azimuth-azimuth_objectif)));
                    texte_guide[2]="";
                    texte_guide[3]="";
                    //To stop counting time allowing to take picture after a given time on the correct position
                    temps_ok=false;
                    //Define direction
	                currentDirection = Direction.RIGHT;  
	            } else {  
	            	// Azimuth OK
	            	if (pitch > pitch_objectif+precision_pitch) {  
	            		// GoDown
		            	//Define text to display
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]=String.valueOf((int)(Math.abs(pitch-pitch_objectif)));
                        texte_guide[3]="";
                        //To stop counting time allowing to take picture after a given time on the correct position
                        temps_ok=false;
                        //Define direction
		                currentDirection = Direction.DOWN;
	            	} else if (pitch < pitch_objectif-precision_pitch) {
	            		// GoUp 
		            	//Define text to display
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]="";
                        texte_guide[3]=String.valueOf((int)(Math.abs(pitch-pitch_objectif)));
                        //To stop counting time allowing to take picture after a given time on the correct position
                        temps_ok=false;
                        //Define direction
		                currentDirection = Direction.UP;
	            	} else {
	            		// Objective reached !
		            	//Define text to display
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]="";
                        texte_guide[3]="";
                        //To start counting time allowing to take picture after a given time on the correct position
                        if(!temps_ok){
                        	temps_ok=true;
                        	start = System.currentTimeMillis();
                        }else{
                        	//After 2 seconds in the correct position
                        	if((Math.abs(System.currentTimeMillis()-start)>2000)&&(photo_non_prise)){
                        		Toast.makeText(getContext(), "Action !", 1000).show();
                        		//Take the picture corresponding to the preview
                        		temps_ok=false;
    		    	        	EcranRecherche.mPreview.takePicture();
    		    	        	photo_non_prise=false;
    		    	        	//TODO Améliorer le sleep
    		    	        	try{
    		    	        		Thread.currentThread();
    		    	        		Thread.sleep(3000);
    		    	        	}catch(InterruptedException e){
    		    	        		e.printStackTrace();
    		    	        	}
    		    	        	//Stop sound
    		    	        	EcranRecherche.mMediaPlayer.get(0).stop();
    		    	        	EcranRecherche.mMediaPlayer.get(1).stop();
    		    	        	EcranRecherche.mMediaPlayer.get(2).stop();
    		    	        	EcranRecherche.mMediaPlayer.get(3).stop();
                        		//Go to next activity where shadow or sun state has to be defined by user
                        		Intent intent = new Intent(Intent.ACTION_VIEW, null, getContext(), DefinitionPhoto.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
                                getContext().getApplicationContext().startActivity(intent);
                        	}
                        }
                        //Define direction
		            	currentDirection = Direction.OK;
	            	}
	            } 
	            
	            //If Direction as defined previously changed since the previous change in sensors
	            if (currentDirection != null && !currentDirection.equals(oldDirection)) {  
	                switch (currentDirection) {  
	                    case LEFT :   
	                        listener.GoLeft();
	                        //Define elements to display
	                        etat_affichage[0]=1;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //Define sounds to play
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;  
	                    case RIGHT :   
	                        listener.GoRight(); 
	                        //Define elements to display
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=1;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //Define sounds to play
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;  
	                    case UP:   
	                        listener.GoUp(); 
	                        //Define elements to display
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=1;
	                        etat_affichage[4]=0;
	                        //Define sounds to play
	                        EcranRecherche.mMediaPlayer.get(0).start();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).start();
	                        break;  
	                    case DOWN:   
	                        listener.GoDown();
	                        //Define elements to display
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=1;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //Define sounds to play
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).start();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break; 
	                    case OK:   
	                        listener.Ok();
	                        //Define elements to display
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=1;
	                        //Define sounds to play
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).start();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;
	                } 
	                //Define current Direction as old Direction for the next iteration
	                oldDirection = currentDirection;
	                
	            }
	            
	            //Display new state for elements
	            invalidate();
	   
	            // forwards orientation to the OrientationListener  
	            listener.onOrientationChanged(azimuth, pitch, roll); 
	        }  
	   
	    };  
	   
	   /**
	    * Utility that displays debug messages.
	    * 
	    * @param log The message to display
	    */
	   @SuppressWarnings("unused")
	   private static void log(String log) {
	       if (LOG_ENABLED) {
	           Log.d(LOG_TAG, log);
	       }
	   }

	/**
	 * @param sensorEventListener the sensorEventListener to set
	 */
	public void setSensorEventListener(SensorEventListener sensorEventListener) {
		this.sensorEventListener = sensorEventListener;
	}

	/**
	 * @return the sensorEventListener
	 */
	public SensorEventListener getSensorEventListener() {
		return sensorEventListener;
	}
}
