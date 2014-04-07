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
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
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
	TextView incorrecttext;
	ArrayList<Drawable> images;
	ArrayList<String> results;
	ArrayList<String> vocab_dict;
	Drawable checkmark;
	Drawable crossmark;
	Drawable currentimg;
	String currentans;
	//***** voice input
	static final int check = 1111;
	//*****
	//*****for timer
	boolean Running = true;
	Runnable runnable;
	Handler handler;
	int timelimit=30;
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
		incorrecttext = (TextView) findViewById(R.id.incorrecttext);
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
		results = new ArrayList<String>();
		vocab_dict = new ArrayList<String>();
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
		images.add(getResources().getDrawable(R.drawable.tree));
		images.add(getResources().getDrawable(R.drawable.cow));
		vocab_dict.add("tree");
		vocab_dict.add("cow");
	}
	public void onClickTryAgain(View v){
		iv.setImageDrawable(currentimg);
		tryagain.setVisibility(View.GONE);
		voice.setVisibility(View.VISIBLE);
		incorrecttext.setVisibility(View.GONE);
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
		
		incorrecttext.setVisibility(View.GONE);
		if(state.equals("Next")){
			voice.setVisibility(View.VISIBLE);
			tryagain.setVisibility(View.GONE);
			skip.setText("Skip");
		}
		int next = images.indexOf(currentimg)+1;
		ques_num.setText("Question"+new Integer(next+1).toString());
		if(next<images.size()){
			currentimg = images.get(next);
			iv.setImageDrawable(currentimg);
		}else{
			
			exitGamePlay();
		}
		
	}
	private void exitGamePlay(){
		Intent i = new Intent(this, GamePlayStatsActivity.class);
		i.putExtra("score", score);
		i.putExtra("timeused", timelimit-time);
		resetData();
		Running = false;
		startActivity(i);
	}
	private void resetData(){
		time = timelimit;
		score = 0;
	}
	
	//****Voice Input***
	public void onClickVoiceInput (View v){
		
		checkAnswer();
	}
	public void checkAnswer(){
		if(results == null || results.size()==0){
			Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is in the Picture?!");
			startActivityForResult(i, check);
		}
		
		
		//**
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == check && resultCode == RESULT_OK){
			results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		}
		super.onActivityResult(requestCode, resultCode, data);
		boolean b = false;
		int pos = images.indexOf(currentimg);
		String firstword = null;
		for(String s: results){
			firstword = results.get(0);
			if(s.equals(vocab_dict.get(pos))){
				b = true;
			}
		}
		
		results=new ArrayList<String>();
		if(b){
			updateResult(checkmark, true);
		}else{
			updateResult(crossmark, false);
			tryagain.setVisibility(View.VISIBLE);
			incorrecttext.setVisibility(View.VISIBLE);
			incorrecttext.setText("It sounded like: "+ firstword);
			skip.setText("Skip");
		}
		
	}

}
