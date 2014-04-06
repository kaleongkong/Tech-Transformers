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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplaystats_main);
		timeused = (TextView)findViewById(R.id.gameplaystats_timeused);
		score = (TextView)findViewById(R.id.gameplaystats_score);
		home = (Button) findViewById(R.id.gameplaystats_home);
		restart = (Button)findViewById(R.id.gameplaystats_restart);
		timeused.setText("Time Used: "+getIntent().getExtras().getInt("timeused"));
		score.setText("Score: "+getIntent().getExtras().getInt("score"));
	}
	public void onClickRestart(View v){
		Intent i = new Intent(this, ChooseGameMode.class);
		startActivity(i);
	}
	public void onClickHome(View v){
		Intent i = new Intent(this, Wordtastic_MainActivity.class);
		startActivity(i);
	}
}
