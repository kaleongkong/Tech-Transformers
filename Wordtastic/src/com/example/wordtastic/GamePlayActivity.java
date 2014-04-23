package com.example.wordtastic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class GamePlayActivity extends Activity implements RecognitionListener{
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
	TextView speaktext;
	//***** voice input
	static final int check = 1111;
	SpeechRecognizer mSpeechRecognizer;
	//*****
	//*****for timer
	boolean Running = true;
	boolean nextRound = false;
	Runnable runnable;
	Handler handler;
	Handler delay_handler;
	int timelimit=30;
	int time;
	int delay_time;
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
		FontModifier.initTypeface(getAssets(), skip);
		mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		mSpeechRecognizer.setRecognitionListener(this);
		speaktext = (TextView) findViewById(R.id.speaktext);
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
		FontModifier.initTypeface(getAssets(), skip);
		FontModifier.initTypeface(getAssets(), tryagain);
		FontModifier.initTypeface(getAssets(), ques_num);
		FontModifier.initTypeface(getAssets(), incorrecttext);
		FontModifier.initTypeface(getAssets(), timer);
		FontModifier.initTypeface(getAssets(), scoreView);
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
		Log.v("updateResult","updateResult");
		LayerDrawable ld = new LayerDrawable(new Drawable[]{currentimg, indicator});
		iv.setImageDrawable(ld);
		//*******************************
		delay_handler = new Handler();
		delay_time = 1;
		int time_gap = 500;
		tuneDelay(delay_time, nextRound, delay_handler, time_gap);
		//********************************
		if(result){
			score++;
		}
		scoreView.setText("Score: "+score+"/"+maxScore);
	}
	
	public void tuneDelay(int period, boolean control, Handler handler, int gap){
		class TunableRunnable implements Runnable{
			int p;
			boolean c;
			int g;
			public TunableRunnable(int period, boolean control, int gap){
				p= period;
				c= control;
				g=gap;
			}
			@Override
			public void run() {
				while(c){
					try{
						Thread.sleep(g); // 1 second
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					if (p>0){
						p-=1;
					}else{
						proceedNextRound();
						c = false;
					}
				}
			}
			
		}
		TunableRunnable delay = new TunableRunnable(period, control, gap);
		delay_handler.post(delay);
	}
	
	
	public void onClickSkip(View v){
		proceedNextRound();
	}
	@SuppressLint("UseValueOf")
	public void proceedNextRound(){
		incorrecttext.setVisibility(View.GONE);
		int next = images.indexOf(currentimg)+1;
		ques_num.setText("Question"+ Integer.valueOf(next+1).toString());
		if(next<images.size()){
			currentimg = images.get(next);
			iv.setImageDrawable(currentimg);
		}else{
			Log.v("exit", "extgameplay");
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
			//mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is in the Picture?!");
			i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.wordtastic");
			mSpeechRecognizer.startListening(i);
			//startActivityForResult(i, check);
		}
	}

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBufferReceived(byte[] arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEndOfSpeech() {
		speaktext.setVisibility(View.GONE);
		
	}
	@Override
	public void onError(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEvent(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPartialResults(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReadyForSpeech(Bundle arg0) {
		speaktext.setVisibility(View.VISIBLE);
		
	}
	@Override
	public void onResults(Bundle r) {
		results = r.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		if (results.size() == 0) {
			results.add("Could not detect speech!");
		} 
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
		nextRound = b;
		Log.v("onResults","onResults");
		if(b){
			updateResult(checkmark, true);
		}else{
			updateResult(crossmark, false);
			voice.setVisibility(View.GONE);
			tryagain.setVisibility(View.VISIBLE);
			incorrecttext.setVisibility(View.VISIBLE);
			incorrecttext.setText("It sounded like: "+ firstword);
		}
		
	}
	@Override
	public void onRmsChanged(float arg0) {
		// TODO Auto-generated method stub
		
	}

}
