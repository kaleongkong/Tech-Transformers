package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GalleryActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_main);
		TextView title = (TextView) findViewById(R.id.gallery);
		TextView deckname = (TextView)findViewById(R.id.deckname);
		Button play = (Button)findViewById(R.id.galleryplay);
		Button back = (Button)findViewById(R.id.back);
		Button newdeck = (Button) findViewById(R.id.newdeck);
		Button editdeck = (Button) findViewById(R.id.editdeck);
		FontModifier.initTypeface(getAssets(), title);
		FontModifier.initTypeface(getAssets(), deckname);
		FontModifier.initTypeface(getAssets(), play);
		FontModifier.initTypeface(getAssets(), back);
		FontModifier.initTypeface(getAssets(), newdeck);
		FontModifier.initTypeface(getAssets(), editdeck);
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
