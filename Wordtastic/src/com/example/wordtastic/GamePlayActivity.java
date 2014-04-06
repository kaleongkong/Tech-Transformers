package com.example.wordtastic;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class GamePlayActivity extends Activity{
	ImageButton voice;
	Button skip;
	Button tryagain;
	ImageView iv;
	TextView ques_num;
	TextView timer;
	TextView scoreView;
	ArrayList<Drawable> images;
	HashMap<Integer, String> vocab_dict;
	Drawable checkmark;
	Drawable crossmark;
	Drawable currentimg;
	String currentans;
	//*****for timer
	boolean Running = true;
	Runnable runnable;
	Handler handler;
	int timelimit=5;
	int time;
	Thread thread;
	//*******
	int score;
	int maxScore;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay_main);
		voice = (ImageButton) findViewById(R.id.soundinput);
		skip = (Button) findViewById(R.id.skip);
		tryagain = (Button) findViewById(R.id.tryagain);
		iv = (ImageView) findViewById(R.id.imageView);
		ques_num = (TextView) findViewById(R.id.ques_num);
		//*** timer
		timer = (TextView) findViewById(R.id.time);
		time = timelimit;
		int minite = time/60;
		int second = time%60;
		timer.setText(minite+":"+second);
		//****
		scoreView = (TextView) findViewById(R.id.score);
		score = 0;
		images = new ArrayList<Drawable>();
		vocab_dict = new HashMap<Integer, String>();
		checkmark = scaleDrawable(R.drawable.greencheckmark, 400, 400);
		crossmark = scaleDrawable(R.drawable.redcrossmark, 400,400);
		settingUp();
		
		currentimg = images.get(0);
		currentans = "dolphin";
		iv.setImageDrawable(currentimg);
		maxScore = images.size();
		scoreView.setText("Score: "+score+"/"+maxScore);
		//**** timer
		handler = new Handler();
		runnable = new Runnable(){
			public void run(){
				while(Running){
					try{
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					handler.post(new Runnable(){
						public void run(){
							if (time>0){
								time-=1;
								int minite = time/60;
								int second = time%60;
								timer.setText(minite+":"+second);
							}else{
								exitGamePlay();
								Running = false;
							}
						}
					});
				}
			}
		};
		thread = new Thread(runnable);
		thread.start();
		//****
	}
	public Drawable scaleDrawable(int id, int width, int height){
		Bitmap b = BitmapFactory.decodeResource(getResources(), id);
		Bitmap resized = Bitmap.createScaledBitmap(b, width, height, false);
		return new BitmapDrawable(getResources(), resized);
	}
	public void settingUp(){
		images.add(getResources().getDrawable(R.drawable.dolphins));
		images.add(getResources().getDrawable(R.drawable.lion_600x450));
		vocab_dict.put(R.drawable.dolphins, "dolphin");
		vocab_dict.put(R.drawable.lion_600x450,"lion");
	}
	
	public void onClickVoiceInput (View v){
		if(checkAnswer(currentans)){
			updateResult(checkmark, true);
		}else{
			updateResult(crossmark, false);
			tryagain.setVisibility(View.VISIBLE);
		}
	}
	public void onClickTryAgain(View v){
		iv.setImageDrawable(currentimg);
		tryagain.setVisibility(View.GONE);
		voice.setVisibility(View.VISIBLE);
	}
	
	private void updateResult(Drawable indicator, boolean result){
		LayerDrawable ld = new LayerDrawable(new Drawable[]{currentimg, indicator});
		iv.setImageDrawable(ld);
		if(images.indexOf(currentimg)==images.size()-1){
			skip.setText("Finish");
		}else{
			skip.setText("Next");
		}
		if(result){
			score++;
		}
		scoreView.setText("Score: "+score+"/"+maxScore);
		voice.setVisibility(View.GONE);
	}
	public void onClickSkip(View v){
		String state = skip.getText().toString();
		if(state.equals("Next")){
			voice.setVisibility(View.VISIBLE);
			tryagain.setVisibility(View.GONE);
		}
		int next = images.indexOf(currentimg)+1;
		if(next<images.size()){
			currentimg = images.get(next);
			iv.setImageDrawable(currentimg);
			ques_num.setText("Question"+next);
		}else{
			Running = false;
			exitGamePlay();
		}
		
	}
	private void exitGamePlay(){
		Intent i = new Intent(this, GamePlayStatsActivity.class);
		i.putExtra("score", score);
		i.putExtra("timeused", timelimit-time);
		resetData();
		startActivity(i);
	}
	private void resetData(){
		time = timelimit;
		score = 0;
	}
	public boolean checkAnswer(String ans){
		return true;
	}

}
