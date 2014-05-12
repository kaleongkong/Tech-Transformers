package com.example.wordtastic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("NewApi")
public class CameraPreview extends Activity {
	
	Camera camera = null;
	ShowCamera showCameraObject;
	SurfaceHolder cameraHolder;
	FrameLayout cameraFrame;
	private static final String TAG = "MyActivity";
	Bitmap takenCameraPicture;
	final Context c = this;
	Button takePictureButton;
	//Button back;
	ImageButton homeButton;
	private Uri targetResource = Media.EXTERNAL_CONTENT_URI;
	Uri pictureUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_preview);		
		createCameraSetUp();
				
		cameraFrame = (FrameLayout)findViewById(R.id.camera_screen);
		showCameraObject = new ShowCamera(this, camera);
		cameraFrame.addView(showCameraObject);
		
		
		takePictureButton = (Button)findViewById(R.id.take_picture);
		//back = (Button) findViewById(R.id.back);
		homeButton = (ImageButton) findViewById(R.id.homeButton);
		
		FontModifier.initTypeface(getAssets(), takePictureButton);
		//FontModifier.initTypeface(getAssets(), back);
		takePictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				takePicture();
				//camera.takePicture(null, null, takenPicture);
				
			}//end onClick
		}); //end setOnClickListener
		
		
	}//end onCreate

	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
	public void openSettings(View v){
		HelpButton.openSettings(v, this);
	}
	private void takePicture() {
        try {
            pictureUri = prepareImageFile("temp", "Android Camera Image");
            
            //File temppath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //temppath.mkdir();
            
            //pictureUri = Uri.fromFile(temppath);
            Log.d(TAG, "picture uri: " + pictureUri.getPath());
            camera.takePicture(null, null, takenPicture);
        } catch (Exception ex) {
            //saveResult(RESULT_CANCELED);
        }
    };
    
    private Uri prepareImageFile(String imageFileName, String imageDescription) {
        //Log.d(TAG, "prepared file name: " + imageFileName);
        ContentValues values = new ContentValues();
        values.put(MediaColumns.TITLE, imageFileName);
        if (imageDescription != null) {
            values.put(ImageColumns.DESCRIPTION, "Android Camera Image");
        }
       // Log.d(TAG, "prepared values");
        return getContentResolver().insert(targetResource, values);
    }
	//CAMERA EXTERNAL METHODS-------------------------------------------------------------------------------------------------------
	
		//sets up camera
		private void createCameraSetUp()
		{	Log.v("here","here");
			//0 is back, 1 is front
			camera = Camera.open(0);
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
	      		
		      	if(data == null)
		      	{
		         	Toast.makeText(getApplicationContext(), "Picture not taken.", Toast.LENGTH_SHORT).show();
		      	}//end if
		      	
		      	else
		      	{
		         	Toast.makeText(getApplicationContext(), "Picture taken.", Toast.LENGTH_SHORT).show(); 
		         	
		         	try {
		         		Log.v("uri path", pictureUri.getPath());
		         
						OutputStream fileOutputStream = getContentResolver().openOutputStream(pictureUri);
						fileOutputStream.write(data);
						fileOutputStream.flush();
						fileOutputStream.close();
			         	
		         	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         	Intent previous = getIntent();
		         	Intent i = new Intent(c, addpic3.class);
		         	i.putExtra("pictureUri", pictureUri.toString());
		         	i.putExtra("deck_theme", previous.getStringExtra("deck_theme"));
					c.startActivity(i);
		      	}//end else
		      	
	   		}//end onPictureTaken
		};//end PictureCallback
		
		protected void onStop() {
		    super.onStop();
		    setContentView(new View(this));
		    //camera = null;
			showCameraObject = null;
			cameraHolder = null;
			cameraFrame = null;
			takenCameraPicture = null;
			takePictureButton = null;
			//back = null;
			//pictureUri = null;
		}
	
	

}
