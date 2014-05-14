package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChooseGameMode extends Activity{
	Button timechallenge;
	Button standard;
	Button settings;
	//Button back;
	ImageButton homeButton;
	TextView title;
	String theme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosegamemode_main);
		timechallenge = (Button)findViewById(R.id.timechallenge);
		standard = (Button) findViewById(R.id.standard);
		//settings = (Button) findViewById(R.id.settings);
		homeButton = (ImageButton) findViewById(R.id.homeButton);
		title = (TextView) findViewById(R.id.textView1);
		FontModifier.initTypeface(getAssets(), timechallenge);
		FontModifier.initTypeface(getAssets(), standard);
		//FontModifier.initTypeface(getAssets(), settings);
		FontModifier.initTypeface(getAssets(), title);
		Intent i = getIntent();
		theme = i.getStringExtra("deck_theme");
	}
	public void onClickTimeChallenge(View v){
		Intent i = new Intent(this, GamePlayActivity.class);
		i.putExtra("deck_theme", theme);
		i.putExtra("timeChallenge", true);
		this.startActivity(i);
	}
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		i.putExtra("deck_theme", theme);
		this.startActivity(i);
	}
	public void openSettings(View v){
		HelpButton.openSettings(v, this);
	}
	public void onClickStandard(View v){
		Intent i = new Intent(this, GamePlayActivity.class);
		i.putExtra("deck_theme", theme);
		i.putExtra("timeChallenge", false);
		this.startActivity(i);
	}
	public void onBackPressed(){
    	Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
}
