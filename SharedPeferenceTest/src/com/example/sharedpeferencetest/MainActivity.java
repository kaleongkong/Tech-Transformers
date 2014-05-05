package com.example.sharedpeferencetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{

	EditText decktext;
	EditText cardnametext;
	EditText cardloctext;
	HashSharedPreferenceMap hashmap;
	TextView exception;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		decktext = (EditText) findViewById(R.id.deckname);
		cardnametext = (EditText) findViewById(R.id.cardname);
		cardloctext = (EditText) findViewById(R.id.cardlocation);
		exception = (TextView) findViewById(R.id.exception);
		hashmap = new HashSharedPreferenceMap(this);
	}
	
	public void saveDeckName(View v){
		String value = decktext.getText().toString();
		try {
			hashmap.saveDeckNamePreferences(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			exception.setText(e.getMessage());
		}
	}
	public void saveCardName(View v){
		String key = decktext.getText().toString();
		String value = cardnametext.getText().toString();
		try {
			hashmap.saveCardNamePreferences(key,value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			exception.setText(e.getMessage());
			
		}
	}
	public void saveCardLocation(View v){
		String deck = decktext.getText().toString();
		String key = cardnametext.getText().toString();
		String value = cardloctext.getText().toString();
		try {
			hashmap.saveCardLocPreferences(deck,key,value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			exception.setText(e.getMessage());
		}
	}
	public void saveAllInfo(View v) throws Exception{
		saveDeckName(v);
		saveCardName(v);
		saveCardLocation(v);
	}
	
	public void clear(View v){
		hashmap.clearAll();
		exception.setText("");
	}
	public void loadNextActivity(View v){
		Intent i = new Intent(this, NextActivity.class);
		startActivity(i);
	}

	private void saveEditText(EditText ed){
		String value = ed.getText().toString();
		try {
			hashmap.saveDeckNamePreferences(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			exception.setText(e.getMessage());
		}
	}
}
