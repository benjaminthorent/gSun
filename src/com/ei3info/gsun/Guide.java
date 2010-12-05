package com.ei3info.gsun;

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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    
    private int azimuth_objectif = (int) gSun.calcul.getAzimut();
    private int pitch_objectif = (int) gSun.calcul.getHauteurSolaire();
    
    private boolean temps_ok;
    private long start;

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
	    flechesGDh = 80;
	    flechesGDl = 60;
	    flechesHBh = 40;
	    flechesHBl = 80;
	    Soleilh = 120;
	    Soleill = 120;
	    
	 
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
	    
	    azimuth_objectif = (int) gSun.calcul.getAzimut();
	    pitch_objectif = (int) gSun.calcul.getHauteurSolaire();
	    
	    texte_guide = new String[4];
	    texte_guide[0]="";
	    texte_guide[1]="";
	    texte_guide[2]="";
	    texte_guide[3]="";
	    
	    temps_ok=false;
		
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
		    FGY = canvas.getHeight()-flechesGDh-20-EcranRecherche.mBouton.getHeight();
		    FDX = canvas.getWidth()-flechesGDl-5;
		    FDY = canvas.getHeight()-flechesGDh-20-EcranRecherche.mBouton.getHeight();
		    FHX = canvas.getWidth()/2-flechesHBl/2;
		    FHY = 5;
		    FBX = canvas.getWidth()/2-flechesHBl/2;
		    FBY = canvas.getHeight()-flechesHBl-15-EcranRecherche.mBouton.getHeight();
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
			canvas.drawText(texte_guide[0], 8+flechesGDl/2, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte gauche
			canvas.drawText(texte_guide[1], canvas.getWidth()-18-flechesGDl/2, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte droit
			canvas.drawText(texte_guide[2], canvas.getWidth()/2-5, flechesHBl + 15, paint);  //Texte haut
			canvas.drawText(texte_guide[3], canvas.getWidth()/2-5, canvas.getHeight()-flechesHBl-25-EcranRecherche.mBouton.getHeight(), paint);  //Texte bas
			
			//Affichage
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
	   
	            //TODO A lier à la précision obtenue dans Param
	            int precision_azimuth = 10;
	            int precision_pitch = 10;
	            
	            if (azimuth > azimuth_objectif+precision_azimuth) {  
	                // GoLeft  
	            	texte_guide[0]=String.valueOf((int)(Math.abs(azimuth-azimuth_objectif)));
                    texte_guide[1]="";
                    texte_guide[2]="";
                    texte_guide[3]="";
                    temps_ok=false;
	                currentDirection = Direction.LEFT;  
	            } else if (azimuth < azimuth_objectif-precision_azimuth) {  
	                // GoRight 
	            	texte_guide[0]="";
                    texte_guide[1]=String.valueOf((int)(Math.abs(azimuth-azimuth_objectif)));
                    texte_guide[2]="";
                    texte_guide[3]="";
                    temps_ok=false;
	                currentDirection = Direction.RIGHT;  
	            } else {  
	            	// Azimuth OK
	            	if (pitch > pitch_objectif+precision_pitch) {  
	            		// GoDown
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]=String.valueOf((int)(Math.abs(pitch-pitch_objectif)));
                        texte_guide[3]="";
                        temps_ok=false;
		                currentDirection = Direction.DOWN;
	            	} else if (pitch < pitch_objectif-precision_pitch) {
	            		// GoUp 
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]="";
                        texte_guide[3]=String.valueOf((int)(Math.abs(pitch-pitch_objectif)));
                        temps_ok=false;
		                currentDirection = Direction.UP;
	            	} else {
	            		// Objective reached !
	            		texte_guide[0]="";
                        texte_guide[1]="";
                        texte_guide[2]="";
                        texte_guide[3]="";
                        if(!temps_ok){
                        	temps_ok=true;
                        	start = System.currentTimeMillis();
                        }else{
                        	if(Math.abs(System.currentTimeMillis()-start)>5000){
                        		Toast.makeText(getContext(), "Action !", 1000).show();
                        		//TODO Rajouter prise de photo et envoi vers nouvelle activité
                        	}
                        }
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
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;  
	                    case RIGHT :   
	                        listener.GoRight(); 
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=1;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;  
	                    case UP:   
	                        listener.GoUp(); 
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=1;
	                        etat_affichage[4]=0;
	                        EcranRecherche.mMediaPlayer.get(0).start();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).start();
	                        break;  
	                    case DOWN:   
	                        listener.GoDown();
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=1;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=0;
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).start();
	                        EcranRecherche.mMediaPlayer.get(2).pause();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break; 
	                    case OK:   
	                        listener.Ok();
	                        etat_affichage[0]=0;
	                        etat_affichage[1]=0;
	                        etat_affichage[2]=0;
	                        etat_affichage[3]=0;
	                        etat_affichage[4]=1;
	                        EcranRecherche.mMediaPlayer.get(0).pause();
	                        EcranRecherche.mMediaPlayer.get(1).pause();
	                        EcranRecherche.mMediaPlayer.get(2).start();
	                        EcranRecherche.mMediaPlayer.get(3).pause();
	                        break;
	                }  
	                oldDirection = currentDirection;
	                
	            }
	            
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
