package com.ei3info.gsun;

//import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.net.Uri;
import android.util.Log;
import android.view.View;

public class Guide extends View {
	
	
	private static final boolean LOG_ENABLED = true;
	private static final String LOG_TAG = "ObjectsView";
	
	
	private Bitmap FlecheGauche;
	private Bitmap FlecheDroite;
	private Bitmap FlecheHaut;
	private Bitmap FlecheBas;
	private Bitmap Soleil;
	 
	
	private static int[] etat_affichage = {0,0,0,0,0};
	 
	
	private int flechesGDh;    
	private int flechesGDl;    
	@SuppressWarnings("unused")
	private int flechesHBh;    
	private int flechesHBl;    
	private int Soleilh;    
	private int Soleill;    
	 
	
	private float FGX;    
	private float FGY;    
	private float FDX;    
	private float FDY;    
	private float FHX;    
	private float FHY;    
	private float FBX;    
	private float FBY;   
	private float SX; 
	private float SY; 
	
	public String[] texte_guide;
	
	private static Sensor sensor;  
    private static SensorManager sensorManager;  
     
    private static OrientationListener listener;
    
    private static boolean running = false;
    

    /** Direction to give to the phone to reach the aim */  
    enum Direction {  
        LEFT,   
        RIGHT,  // Go right
        UP,     // Go up
        DOWN,   // Go down
        OK;     // Aim reached  
    } 
	
	
	
	public Guide(Context context) {
	    
		super(context);
	    flechesGDh = 60;
	    flechesGDl = 30;
	    flechesHBh = 30;
	    flechesHBl = 60;
	    Soleilh = 100;
	    Soleill = 100;
	    
	 
	    FlecheGauche = prepareBitmap(getResources().getDrawable(R.drawable.fg), flechesGDl,
	    		flechesGDh);
	    FlecheDroite = prepareBitmap(getResources().getDrawable(R.drawable.fd), flechesGDl,
	    		flechesGDh);
	    FlecheHaut = prepareBitmap(getResources().getDrawable(R.drawable.fh), flechesHBl,
	    		flechesGDh);
	    FlecheBas = prepareBitmap(getResources().getDrawable(R.drawable.fb), flechesHBl,
	    		flechesGDh);
	    Soleil = prepareBitmap(getResources().getDrawable(R.drawable.soleil), Soleill,
	    		Soleilh);
	    
	   
	    texte_guide = new String[4];
	    texte_guide[0]="gauche";
	    texte_guide[1]="droite";
	    texte_guide[2]="haut";
	    texte_guide[3]="bas";
		
	}
	
	// Setters
	
	public void setTextes(String texte_gauche,String texte_droit,String texte_haut,String texte_bas){
		texte_guide = new String[4];
		texte_guide[0]=texte_gauche;
		texte_guide[1]=texte_droit;
		texte_guide[2]=texte_haut;
		texte_guide[3]=texte_bas;
	}
	
	
	 
		private static Bitmap prepareBitmap(Drawable drawable, int width, int height) {
		    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		    drawable.setBounds(0, 0, width, height);
		    Canvas canvas = new Canvas(bitmap);
		    drawable.draw(canvas);
		    return bitmap;
		}
	 
	    @Override
	    protected void onDraw(Canvas canvas) {
		    FGX = 5;
		    FGY = canvas.getHeight()-flechesGDh-10;
		    FDX = canvas.getWidth()-flechesGDl-5;
		    FDY = canvas.getHeight()-flechesGDh-10;
		    FHX = canvas.getWidth()/2-flechesHBl/2;
		    FHY = 5;
		    FBX = canvas.getWidth()/2-flechesHBl/2;
		    FBY = canvas.getHeight()-flechesHBl-5;
		    SX = canvas.getWidth()/2-Soleill/2;
		    SY = canvas.getHeight()/2-Soleilh/2;
		    
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
	        
	        
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.RED);
			//draw texts
			canvas.drawText(texte_guide[0], 10, canvas.getHeight()-flechesHBl-15, paint);  //Texte gauche
			canvas.drawText(texte_guide[1], canvas.getWidth()-38, canvas.getHeight()-flechesHBl-15, paint);  //Texte droit
			canvas.drawText(texte_guide[2], canvas.getWidth()/2-12, flechesHBl + 15, paint);  //Texte haut
			canvas.drawText(texte_guide[3], canvas.getWidth()/2-8, canvas.getHeight()-flechesHBl-15, paint);  //Texte bas
			
			//Affichage
	    	super.onDraw(canvas);
	    }
	 
	   
	  /*@Override
	   public boolean onTouchEvent(MotionEvent event) {
	
	       final int action = event.getAction();
	       final float x = event.getX();
	       final float y = event.getY();
	       
	
	       switch (action) {
	           case MotionEvent.ACTION_DOWN:
	               if (x >= 0 && x <= 300 && y >= 0
	                       && y <= 300) {
	               	log("Start moving");
	                  return true;
	               }
	               break;
	
	           case MotionEvent.ACTION_MOVE:
	           	
	           	etat_affichage[0] = 1;
	           	
	               optimizedInvalidate();
	               
	               return true;
	
	           case MotionEvent.ACTION_UP:
	           	log("Stop moving");
	           	etat_affichage[0] = 0;
	               log("Maj");
	               invalidate();
	               return true;
	       }
	       return super.onTouchEvent(event);
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
	            if (sensorManager != null && sensorEventListener != null) {  
	                sensorManager.unregisterListener(sensorEventListener);  
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
	                    sensorEventListener, sensor,   
	                    SensorManager.SENSOR_DELAY_FASTEST);  
	            listener = orientationListener;  
	        }  
	    }  
	   
	    /** 
	     * The listener that listen to events from the orientation listener 
	     */  
	    private SensorEventListener sensorEventListener = new SensorEventListener() {  
	   
	        /** The side that is currently up */  
	        private Direction currentDirection = null;  
	        private Direction oldDirection = null;  
	        private float azimuth;  
	        private float pitch;  
	        private float roll;  
	   
	        public void onAccuracyChanged(Sensor sensor, int accuracy) {}  
	   
	        public void onSensorChanged(SensorEvent event) {  
	   
	            azimuth = event.values[0];     // azimuth  
	            pitch = event.values[1];     // pitch  
	            roll = event.values[2];        // roll  
	   
	            //TODO A lier ˆ la classe qui rŽcupre les position objectif
	            int azimuth_objectif = 180;
	            int pitch_objectif = -30;
	            int precision_azimuth = 20;
	            int precision_pitch = 20;
	            
	            /*if (azimuth > azimuth_objectif+precision_azimuth) {  
	                // top side up  
	                currentDirection = Direction.LEFT;  
	            } else if (azimuth < azimuth_objectif-precision_azimuth) {  
	                // bottom side up  
	                currentDirection = Direction.RIGHT;  
	            } else if (roll > 45) {  
	                // right side up  
	                //currentDirection = Direction.UP;  
	            } else if (roll < -45) {  
	                // left side up  
	                //currentDirection = Direction.DOWN;  
	            }  else if(azimuth<=azimuth_objectif+precision_azimuth && azimuth>=azimuth_objectif-precision_azimuth) {
	                // left side up
	            	currentDirection = Direction.OK;
	            }*/
	            
	            if (azimuth > azimuth_objectif+precision_azimuth) {  
	                // GoLeft  
	                currentDirection = Direction.LEFT;  
	            } else if (azimuth < azimuth_objectif-precision_azimuth) {  
	                // GoRight 
	                currentDirection = Direction.RIGHT;  
	            } else {  
	            	// Azimuth OK
	            	if (pitch > pitch_objectif+precision_pitch) {  
	            		// GoDown
		                currentDirection = Direction.DOWN;
	            	} else if (pitch < pitch_objectif-precision_pitch) {
	            		// GoUp 
		                currentDirection = Direction.UP;
	            	} else {
	            		// Objective reached !
		            	currentDirection = Direction.OK;
	            	}
	            } 
	            
	   
	            if (currentDirection != null && !currentDirection.equals(oldDirection)) {  
	                switch (currentDirection) {  
	                    case LEFT :   
	                        listener.GoLeft();
	                        etat_affichage[0]=1;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //EcranRecherche.mMediaPlayer.get(0).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).stop();
	                        break;  
	                    case RIGHT :   
	                        listener.GoRight(); 
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=1;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //EcranRecherche.mMediaPlayer.get(0).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).stop();
	                        break;  
	                    case UP:   
	                        listener.GoUp(); 
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=1;
	                        etat_affichage[4]=0;
	                        //EcranRecherche.mMediaPlayer.get(0).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).stop();
	                        //EcranRecherche.mMediaPlayer.get(0).start();
	                        break;  
	                    case DOWN:   
	                        listener.GoDown();
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=1;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        //EcranRecherche.mMediaPlayer.get(0).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).start();
	                        break; 
	                    case OK:   
	                        listener.Ok();
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=1;
	                        //EcranRecherche.mMediaPlayer.get(0).stop();
	                        //EcranRecherche.mMediaPlayer.get(1).stop();
	                        break;
	                }  
	                oldDirection = currentDirection;  
	                //Optimized invalidate ?
	                invalidate();
	            }
	   
	            // forwards orientation to the OrientationListener  
	            listener.onOrientationChanged(azimuth, pitch, roll); 
	        }  
	   
	    };  

	    
	   
	   
	  
	   @SuppressWarnings("unused")
	   private void optimizedInvalidate() {
	      
	       
	       final int l = (int)FGX;
	       final int t = (int)FGY;
	       
	       final int b = (int)(FGX+flechesGDl);
	       final int r = (int)(FGY+flechesGDh);
	      
	       invalidate(l, t, b, r);
	   }
	   
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
}
