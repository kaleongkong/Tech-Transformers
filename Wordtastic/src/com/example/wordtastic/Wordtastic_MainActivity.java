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
		ilsh = new ImgLocalStorageHandler(getResources());
		hspmap = new HashSharedPreferenceMap(this);
		//if(hspmap.getAllCardNamesInDeck("animal").size()==0){
		    
			hspmap.clearAll(); //COMMENT OUT ONCE THE ENTIRE PROJECT IS FINISHED!
			setup();
		//}
		//Log.v("deck card name list", hspmap.getAllCardNamesInDeck("animal").toString());
	}
	
	private void setup(){
		int[] drawableImgAnimal = {R.drawable.duck, R.drawable.squirrel, R.drawable.cat, R.drawable.lion, R.drawable.monkey, R.drawable.chicken};
		String[] cardNameAnimal = {"duck","squirrel","cat","lion","monkey","chicken"};
		int[] drawableImgFruits = {R.drawable.fruitbowl};
		String[] cardNameFruits = {"fruits"};
		int[] drawableImgPlaces = {R.drawable.globe_cartoon};
		String[] cardNamePlaces = {"places"};
		int[] drawableImgPlants = {R.drawable.tree};
		String[] cardNamePlants = {"tree"};
		int[] drawableImgFurnitures = {R.drawable.table};
		String[] cardNameFurnitures = {"table"};
		int[] drawableImgAlphabets = {R.drawable.abcblocks};
		String[] cardNameAlphabets = {"alphabets"};
		setupDeck("animals", drawableImgAnimal, cardNameAnimal);
		setupDeck("fruits", drawableImgFruits, cardNameFruits);
		setupDeck("places", drawableImgPlaces, cardNamePlaces);
		setupDeck("plants", drawableImgPlants, cardNamePlants);
		setupDeck("furnitures", drawableImgFurnitures, cardNameFurnitures);
		setupDeck("alphabets", drawableImgAlphabets, cardNameAlphabets);
	}
	private void setupDeck(String theme, int[] drawableImg, String[] cardname){
		
		for(int i=0; i<cardname.length; i++){
			ilsh.saveDrawableToInternal(drawableImg[i], cardname[i]);
		}
		try{
			hspmap.saveDeckNamePreferences(theme);
			for(int i=0; i<cardname.length;i++){
				hspmap.saveCardNamePreferences(theme, cardname[i]);
				hspmap.saveCardLocPreferences(theme, cardname[i], ilsh.getImgPath()+"/"+cardname[i]+".jpg");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void onClickPlay(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		startActivity(i);
	}
	protected void onStop() {
	    super.onStop();
	    setContentView(new View(this));
	    play = null;
		ilsh = null;
		hspmap = null;
	}
}
