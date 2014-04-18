package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Wordtastic_MainActivity extends Activity {
	
	Button play;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordtastic_main);
		//initTypeface();
		TextView titleText = (TextView) findViewById(R.id.textView1);
		TextView subTitleText = (TextView) findViewById(R.id.textView2);
		FontModifier.initTypeface(getAssets(),titleText);
		FontModifier.initTypeface(getAssets(),subTitleText);
		play = (Button)findViewById(R.id.play);
		FontModifier.initTypeface(getAssets(),play);
	}
	/*
	private void initTypeface() {
		Typeface sniglet = Typeface.createFromAsset(getAssets(), "fonts/Sniglet-Regular.ttf");
		TextView titleText = (TextView) findViewById(R.id.textView1);
		titleText.setTypeface(sniglet);
	}*/
	public void onClickPlay(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}

}
