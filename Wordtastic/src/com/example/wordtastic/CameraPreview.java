package com.example.wordtastic;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraPreview extends Activity {
	
	Camera camera = null;
	ShowCamera showCameraObject;
	SurfaceHolder cameraHolder;
	FrameLayout cameraFrame;
	private static final String TAG = "MyActivity";
	Bitmap takenCameraPicture;
	Uri imageURI;
	final Context c = this;
	Button takePictureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);
		

				
		createCameraSetUp();
				
		cameraFrame = (FrameLayout)findViewById(R.id.camera_screen);
		showCameraObject = new ShowCamera(this, camera);
		cameraFrame.addView(showCameraObject);
		
		
		takePictureButton = (Button)findViewById(R.id.take_picture);
		
		takePictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				camera.takePicture(null, null, takenPicture);
				Intent i = new Intent(c, addpic3.class);
				c.startActivity(i);
				
			}//end onClick
		}); //end setOnClickListener
		
		
	}//end onCreate

	
	
	//CAMERA EXTERNAL METHODS-------------------------------------------------------------------------------------------------------
	
		//sets up camera
		private void createCameraSetUp()
		{	Log.v("here","here");
			//0 is back, 1 is front
			camera = Camera.open();
		}//end createCameraSetUp
		
		
		@Override
		protected void onResume() 
		{
			System.out.println("Resume");
			super.onResume();
			try 
			{
				createCameraSetUp();
				cameraFrame.addView(showCameraObject);
				camera.startPreview();
			}//end try
			
			catch (RuntimeException e) 
			{
				e.printStackTrace();
			}//end catch
		}//end onResume
		
		@Override
		protected void onPause() 
		{
			super.onPause();
			System.out.println("Pause");
			cameraFrame.removeView(showCameraObject);
			camera.release();
		}//end onPause

		
		//class created in order to preview the camera
		public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback 
		{

			public ShowCamera(Context context, Camera camera) 
			{
				super(context);
				cameraHolder = getHolder();
				cameraHolder.addCallback(this);
			}//end constructor

			public void surfaceCreated(SurfaceHolder holder) 
			{
				try 
				{
					camera.setDisplayOrientation(90);
					camera.setPreviewDisplay(holder);
					camera.startPreview();
				}//end try 
				
				catch (IOException e) 
				{
					Log.d(TAG, "Error setting camera preview: " + e.getMessage());
				}//end catch
			}//end surfaceCreated

			//if preview can change or rotate, take care of those events here
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
			}//end surfaceChanged

			//take care of releasing Camera preview in the activity
			public void surfaceDestroyed(SurfaceHolder arg0) {
			}//end surfaceDestroyed
		}//end ShowCamera class
		 
		
		
		private PictureCallback takenPicture = new PictureCallback() {

	      	@Override
	      	public void onPictureTaken(byte[] data, Camera camera) {

		      	takenCameraPicture = BitmapFactory.decodeByteArray(data , 0, data.length);
		      	
		      	if(takenCameraPicture == null)
		      	{
		         	Toast.makeText(getApplicationContext(), "Picture not taken.", Toast.LENGTH_SHORT).show();
		      	}//end if
		      	
		      	else
		      	{
		         	Toast.makeText(getApplicationContext(), "Picture taken.", Toast.LENGTH_SHORT).show();    	
		      	}//end else
		      	
	   		}//end onPictureTaken
		};//end PictureCallback
		
	
	
	
	

}
