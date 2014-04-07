package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GalleryActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_main);
	}
	public void onClickPlay(View v){
		Intent i = new Intent(this, ChooseGameMode.class);
		startActivity(i);
	}
	public void onClickEditDeck(View v){
		Intent i = new Intent(this, AddActivity.class);
		startActivity(i);
	}
}
