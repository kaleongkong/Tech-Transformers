package com.example.wordtastic;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class addpic5 extends Activity {
	Button back;
	TextView title;
	TextView deckname;
	Button backtogallery;
	Button backtodeckpage;
	ImageButton homeButton;
	TextView text;
	String cardname;
	Uri pictureUri;
	Bitmap takenCameraImage;
	ImageView iv;
	String theme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic5);
		Intent previous = getIntent();
		theme = previous.getStringExtra("deck_theme");
		//back = (Button) findViewById(R.id.button2);
		title = (TextView) findViewById(R.id.textView1);
		deckname = (TextView) findViewById(R.id.textView3);
		backtogallery = (Button) findViewById(R.id.backToGallery);
		backtodeckpage = (Button) findViewById(R.id.backToDeckPage);
        text = (TextView) findViewById(R.id.textView2);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        deckname.setText("Deck: "+theme);
        //FontModifier.initTypeface(getAssets(), back);
        FontModifier.initTypeface(getAssets(), title);
        FontModifier.initTypeface(getAssets(), deckname);
        FontModifier.initTypeface(getAssets(), backtogallery);
        FontModifier.initTypeface(getAssets(), text);
        getImageAndputOnView();
        text.setText("Flashcard "+cardname+" was added to:");
        
	}

	
	private void getImageAndputOnView(){
		Intent i = getIntent();
		pictureUri = Uri.parse(i.getStringExtra("pictureUri"));
		cardname = i.getStringExtra("cardname");
		iv=(ImageView) findViewById(R.id.tree);
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16; // to save memory
        try {
			takenCameraImage = rotateBitmap90(BitmapFactory.decodeStream(getContentResolver().openInputStream(pictureUri), null, options));
			takenCameraImage = Bitmap.createScaledBitmap(takenCameraImage, 600, 800, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		iv.setImageBitmap(takenCameraImage);
	}
	
	private Bitmap rotateBitmap90(Bitmap bm){
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		return Bitmap.createBitmap(bm , 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
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
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
	public void onClickBackToDeckPage(View v){
		Intent i = new Intent(this, AddActivity.class);
		i.putExtra("deck_theme", theme);
		this.startActivity(i);
	}
	
	protected void onStop() {
	    super.onStop();
	    setContentView(new View(this));
	    takenCameraImage = null;
		iv = null;
		pictureUri = null;
	}
}