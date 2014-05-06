
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class addpic3<MainActivity> extends Activity {
	
	Bitmap takenCameraImage;
	ImageView iv;
	Uri pictureUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic3);
		Button back = (Button) findViewById(R.id.button3);
		TextView title = (TextView) findViewById(R.id.textView2);
		Button goNext= (Button) findViewById(R.id.button2);
		Button retake = (Button) findViewById(R.id.back);
		
		
		
		FontModifier.initTypeface(getAssets(), back);
		FontModifier.initTypeface(getAssets(), title);
		FontModifier.initTypeface(getAssets(), goNext);
		FontModifier.initTypeface(getAssets(), retake);
		
		
       
		getImageAndputOnView();
		
		goNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
	            Intent n = new Intent(addpic3.this, addpic4.class);
	            n.putExtra("pictureUri", pictureUri.toString());
	            startActivity(n);
	            
	        }
		});
	}//end onCreate
	
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
		getMenuInflater().inflate(R.menu.wordtastic__main, menu);
		return true;
	}

}

