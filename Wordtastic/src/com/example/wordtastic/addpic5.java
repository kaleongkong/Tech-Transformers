package com.example.wordtastic;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class addpic5 extends Activity {
	Button back;
	TextView title;
	TextView deckname;
	Button backtogallery;
	TextView text;
	
	Uri pictureUri;
	Bitmap takenCameraImage;
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic5);
		
		back = (Button) findViewById(R.id.button2);
		title = (TextView) findViewById(R.id.textView1);
		deckname = (TextView) findViewById(R.id.textView3);
		backtogallery = (Button) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.textView2);
        
        FontModifier.initTypeface(getAssets(), back);
        FontModifier.initTypeface(getAssets(), title);
        FontModifier.initTypeface(getAssets(), deckname);
        FontModifier.initTypeface(getAssets(), backtogallery);
        FontModifier.initTypeface(getAssets(), text);
        getImageAndputOnView();
	}

	
	private void getImageAndputOnView(){
		Intent i = getIntent();
		pictureUri = Uri.parse(i.getStringExtra("pictureUri"));
		iv=(ImageView) findViewById(R.id.tree);
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2; // to save memory
        try {
			takenCameraImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(pictureUri), null, options);
			takenCameraImage = Bitmap.createScaledBitmap(takenCameraImage, 500,500,true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iv.setImageBitmap(takenCameraImage);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.wordtastic__main, menu);
		return true;
	}
	public void onClickBackToGallery(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}
}