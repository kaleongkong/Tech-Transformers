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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class addpic4 extends Activity {
	Uri pictureUri;
	Bitmap takenCameraImage;
	ImageView iv;
	EditText cardnameet;
	HashSharedPreferenceMap hspm;
	ImgLocalStorageHandler ilsh;
	static String  theme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic4);
		TextView title = (TextView) findViewById(R.id.name);
		
		
		//Button back = (Button) findViewById(R.id.button2);
		ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
		Button goNext= (Button) findViewById(R.id.confirm);
		cardnameet = (EditText) findViewById(R.id.editText1);
		FontModifier.initTypeface(getAssets(), goNext);
		FontModifier.initTypeface(getAssets(), goNext);
		goNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
				Intent n = new Intent(addpic4.this,addpic5.class);
				try {
					hspm.saveCardNamePreferences(theme, cardnameet.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				ilsh.saveBitmapToInternal(takenCameraImage, cardnameet.getText().toString());
				n.putExtra("pictureUri", pictureUri.toString());
				n.putExtra("cardname", cardnameet.getText().toString());
				n.putExtra("deck_theme", theme);
		        startActivity(n);
	        }
		});
		getImageAndputOnView();
		hspm = new HashSharedPreferenceMap(this);
		ilsh = new ImgLocalStorageHandler(getResources());

	}
	
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
	
	private Bitmap rotateBitmap90(Bitmap bm){
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		return Bitmap.createBitmap(bm , 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	}
	
	private void getImageAndputOnView(){
		Intent i = getIntent();
		pictureUri = Uri.parse(i.getStringExtra("pictureUri"));
		theme = i.getStringExtra("deck_theme");
		iv=(ImageView) findViewById(R.id.tree);
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16; // to save memory
        try {
			takenCameraImage = rotateBitmap90(BitmapFactory.decodeStream(getContentResolver().openInputStream(pictureUri), null, options));
			takenCameraImage = Bitmap.createScaledBitmap(takenCameraImage, 600, 800, true);
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
	protected void onStop() {
	    super.onStop();
	    setContentView(new View(this));
	    takenCameraImage = null;
		iv = null;
		pictureUri = null;
		cardnameet = null;
		hspm = null;
		ilsh = null;
	}

}