package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Wordtastic_MainActivity extends Activity {
	
	Button play;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordtastic__main);
		play = (Button)findViewById(R.id.play);
	}
	public void onClickPlay(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}

}
