package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseGameMode extends Activity{
	Button timechallenge;
	Button standard;
	Button settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosegamemode_main);
		timechallenge = (Button)findViewById(R.id.timechallenge);
		standard = (Button) findViewById(R.id.standard);
		settings = (Button) findViewById(R.id.settings);
	}
	public void onClickTimeChallenge(View v){
		Intent i = new Intent(this, GamePlayActivity.class);
		this.startActivity(i);
	}
}
