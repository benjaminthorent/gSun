package com.ei3info.gsun;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
public class Preview extends SurfaceView implements SurfaceHolder.Callback{
	SurfaceHolder mHolder;
	Camera mCamera;
 
	Preview(Context context) {
		super(context);
 
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		
		mHolder = getHolder();
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(this);
	}
 
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		mCamera = Camera.open();
		try {
		   mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
			exception.printStackTrace();
		}
	}
 
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		Parameters params = mCamera.getParameters();
        mCamera.setParameters(params);
        mCamera.startPreview();
	}
	
	//TODO Pour Shion, méthodes à décommenter pour la prise de photo
	/*
	Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePhotoTask().execute(data);
			camera.startPreview();
		}
	};
	
 	public void takePicture() {
		mCamera.takePicture(null, null, photoCallback);
	}
 	
	class SavePhotoTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... jpeg) {
			File photo=new File(Environment.DIRECTORY_PICTURES,
													"photo.jpg");
 
			if (photo.exists()) {
				photo.delete();
			}
 
			try {
				FileOutputStream fos=new FileOutputStream(photo.getPath());
				fos.write(jpeg[0]);
				fos.close();
			}
			catch (java.io.IOException e) {
				Log.e("PictureDemo", "Exception in photoCallback", e);
			}
			return(null);
		}
	}
	*/
}
