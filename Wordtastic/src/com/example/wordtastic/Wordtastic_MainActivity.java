package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Wordtastic_MainActivity extends Activity {
	
	Button play;
	ImgLocalStorageHandler ilsh;
	HashSharedPreferenceMap hspmap;
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
		setup();
		Log.v("deck card name list", hspmap.getAllCardNamesInDeck("Animal").toString());
	}
	
	public void setup(){
		ilsh = new ImgLocalStorageHandler(getResources());
		hspmap = new HashSharedPreferenceMap(this);
		ilsh.saveDrawableToInternal(R.drawable.cow, "cow");
		ilsh.saveDrawableToInternal(R.drawable.dolphin, "dolphin");
		ilsh.saveDrawableToInternal(R.drawable.eagle, "eagle");
		ilsh.saveDrawableToInternal(R.drawable.lion, "lion");
		ilsh.saveDrawableToInternal(R.drawable.table, "table");
		ilsh.saveDrawableToInternal(R.drawable.tree, "tree");
		
		try {
			String def = "animal";
			hspmap.saveDeckNamePreferences(def);
			hspmap.saveCardNamePreferences(def, "cow");
			hspmap.saveCardNamePreferences(def, "dolphin");
			hspmap.saveCardNamePreferences(def, "eagle");
			hspmap.saveCardNamePreferences(def, "lion");
			hspmap.saveCardNamePreferences(def, "table");
			hspmap.saveCardNamePreferences(def, "tree");
			
			hspmap.saveCardLocPreferences(def, "cow", ilsh.getImgPath()+"/cow.jpg");
			hspmap.saveCardLocPreferences(def, "dolphin", ilsh.getImgPath()+"/dolphin.jpg");
			hspmap.saveCardLocPreferences(def, "eagle", ilsh.getImgPath()+"/eagle.jpg");
			hspmap.saveCardLocPreferences(def, "lion", ilsh.getImgPath()+"/lion.jpg");
			hspmap.saveCardLocPreferences(def, "table", ilsh.getImgPath()+"/table.jpg");
			hspmap.saveCardLocPreferences(def, "tree", ilsh.getImgPath()+"/tree.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void onClickPlay(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}

}
