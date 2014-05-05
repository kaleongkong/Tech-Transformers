
package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic3);
		Button back = (Button) findViewById(R.id.button3);
		TextView title = (TextView) findViewById(R.id.textView2);
		Button goNext= (Button) findViewById(R.id.button2);
		Button retake = (Button) findViewById(R.id.back);
		goNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
	            Intent n = new Intent(addpic3.this, addpic4.class);
	            startActivity(n);
	            
	        }
			});
		
		
		FontModifier.initTypeface(getAssets(), back);
		FontModifier.initTypeface(getAssets(), title);
		FontModifier.initTypeface(getAssets(), goNext);
		FontModifier.initTypeface(getAssets(), retake);
		
		
        //retrieve it on the other end:
		Intent intent = getIntent();
		takenCameraImage = Bitmap.createScaledBitmap((Bitmap) intent.getParcelableExtra("BitmapImage"),500,500,true);
		iv=(ImageView) findViewById(R.id.tree);
		iv.setImageBitmap(takenCameraImage);
	}//end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wordtastic__main, menu);
		return true;
	}

}

