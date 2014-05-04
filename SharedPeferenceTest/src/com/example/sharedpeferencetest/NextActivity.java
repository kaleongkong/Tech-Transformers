package com.example.sharedpeferencetest;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class NextActivity extends Activity{
	ListView lv;
	EditText et;
	EditText etdeck;
	ArrayList<String> a;
	ArrayList<String> cardnamelist;
	HashSharedPreferenceMap hashmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next_activity);
		lv = (ListView)findViewById(R.id.listView1);
		a = new ArrayList<String>();
		hashmap = new HashSharedPreferenceMap(this);
		et = (EditText) findViewById(R.id.cardname);
		etdeck = (EditText) findViewById(R.id.deckedittext);
		Set<String> keys = hashmap.getAllDeckNames();
		a.addAll(keys);
	}
	public void getDeckNames(View view){
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,a));
	}
	
	public void getCardNames(View view){
		cardnamelist = new ArrayList<String>();
		String key = etdeck.getText().toString();
		Set<String> keys = hashmap.getAllCardNamesInDeck(key);
		cardnamelist.addAll(keys);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cardnamelist));
	}
	public void getCardLocation(View view){
		
		String key = et.getText().toString();
		String deck = etdeck.getText().toString();
		String value = hashmap.getCardLoc(deck, key);
		ArrayList<String> locList = new ArrayList<String>();
		locList.add(value);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,locList));
	}
}












