package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class addpic5 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic5);
		
        TextView text = (TextView) findViewById(R.id.textView2);
        
//		Intent i = getIntent();
//		String name = i.getStringExtra("name");
//		CharSequence msg = String.format("Flashcard %s added to:", name);
//		text.setText(msg);
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