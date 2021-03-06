package com.ei3info.gsun;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
 
/**
 * Class that enable to display the camera display
 * @author bthorent
 *
 */
public class Preview2 extends SurfaceView implements SurfaceHolder.Callback{
	SurfaceHolder mHolder;
	Camera mCamera;
	boolean test = false;
 
	/**
	 * Preview Constructor that creates the Preview holder
	 * @param context of the current application
	 */
	Preview2(Context context) {
		super(context);
 
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		
		mHolder = getHolder();
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(this);
		
	}
 
	/**
	 * Method called when a Preview is created
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		mCamera = Camera.open();
		try {
		   mCamera.setDisplayOrientation(90);
		   mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
			exception.printStackTrace();
		}
	}
 
	/**
	 * Method called when the Preview Holder is destroyed (application or activity ended)
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	/**
	 * Method called when the Preview Holder is changed (change of orientation,...)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		Parameters params = mCamera.getParameters();
        mCamera.setParameters(params);
        mCamera.startPreview();
	}
	
	/**
	 * Method to PictureCallback needed to save a picture (needed when taking a picture -> see take picture method)
	 */
	Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			
				Fichier.setCaracPicture(AccesMesures.repCourant.getName(), data);
			
		}
	};
	
	/**
	 * Method that actually take a picture corresponding to the current state of the preview
	 */
 	public void takePicture() {
		mCamera.takePicture(null, null, photoCallback);
	}
	
}

