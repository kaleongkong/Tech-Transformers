package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GamePlayStatsActivity extends Activity{
	TextView timeused;
	TextView score;
	Button home;
	Button restart;
	boolean timeChallenge;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplaystats_main);
		timeused = (TextView)findViewById(R.id.gameplaystats_timeused);
		score = (TextView)findViewById(R.id.gameplaystats_score);
		home = (Button) findViewById(R.id.gameplaystats_home);
		restart = (Button)findViewById(R.id.gameplaystats_restart);
		
		timeChallenge = getIntent().getExtras().getBoolean("timeChallenge");
		if(timeChallenge){
			timeused.setText("Time Used: "+getIntent().getExtras().getInt("timeused"));
		}else{
			timeused.setText("");
		}
		score.setText("Score: "+getIntent().getExtras().getInt("score"));
		FontModifier.initTypeface(getAssets(), timeused);
		FontModifier.initTypeface(getAssets(), score);
		FontModifier.initTypeface(getAssets(), home);
		FontModifier.initTypeface(getAssets(), restart);
	}
	/*
	public void onClickRestart(View v){
		Intent i = new Intent(this, ChooseGameMode.class);
		startActivity(i);
	}
	*/
	public void onClickHome(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}
	protected void onStop() {
	    super.onStop();
	    setContentView(new View(this));
	    timeused = null;
		score = null;
		home = null;
		restart =null;
	}
}
