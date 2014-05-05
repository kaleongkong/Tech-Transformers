package com.example.sharedpeferencetest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

@SuppressLint("NewApi")
public class HashSharedPreferenceMap {
	private Context context;
	private static final String HEADER ="wordtastic_";
	private static final String DECKHEADER = HEADER+"_deck_";
	private static final String CARDHEADER = DECKHEADER+"_card_";
	private static final String LOCHEADER = CARDHEADER+"_loc_";
	
	
	private SharedPreferences sharedStringPreferences;
	private String rootdeck;
	private HashSet<String> decknames; 
	private HashSet<String> cardnames;
	
	public HashSharedPreferenceMap(Context c){
		context = c;
		sharedStringPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		rootdeck = HEADER+"deck";
		decknames = (HashSet<String>) sharedStringPreferences.getStringSet(rootdeck, new HashSet<String>());
	}
	
	public void saveDeckNamePreferences(String value) throws Exception {
		if(value==null||value==""){
			value = "default";
		}
		value = value.toLowerCase();
		value = DECKHEADER+value;
		Editor editor = sharedStringPreferences.edit();
		decknames = (HashSet<String>) sharedStringPreferences.getStringSet(rootdeck, new HashSet<String>());
		if(!decknames.contains(value)){
			decknames.add(value);
			savePreferences(rootdeck, decknames);
			Log.v("string",rootdeck);
			editor.commit();
		}else{
			throw new Exception("Deck Name is already Used");
		}
	}
	
	//if deck requested doesn't exist, throw exception
	//if deck isn't specified, default deck will be chosen
	public void saveCardNamePreferences(String deck, String card) throws Exception {
		if(deck==null ||deck==""){
			deck="default";
		}
		String cardskey = DECKHEADER+deck;
		decknames = (HashSet<String>) sharedStringPreferences.getStringSet(rootdeck, new HashSet<String>());
		Editor editor = sharedStringPreferences.edit();
		if(decknames.contains(cardskey)){
			String cardname = cardskey+"_"+card;
			cardnames = (HashSet<String>) sharedStringPreferences.getStringSet(cardskey, new HashSet<String>());
			if(!cardnames.contains(cardname)){
				cardnames.add(cardname);
				Log.v("cardskey",cardskey);
				savePreferences(cardskey, cardnames);
				editor.commit();
			}else{
				throw new Exception("Card Name is already used");
			}
		}else{
			throw new Exception("Requested Deck doesn't exsist!");
		}
	}
	
	public void saveCardLocPreferences(String deck, String card, String loc) throws Exception {
		if(card==null){
			card="";
		}
		String cardskey = DECKHEADER+deck;
		String cardname = cardskey+"_"+card;
		String location = loc;
		String existed_loc = loadSavedStringPreferences(card);
		if(existed_loc.equals("AVAILABLE")){
			savePreferences(cardname, location);
		}else{
			throw new Exception("Card Name is already used");
		}
	}
	
	public Set<String> getAllDeckNames(){
		return getAllKey();
	}
	public Set<String> getAllCardNamesInDeck(String s){
		String key = DECKHEADER+s;
		Log.v("get cardskey",key);
		Set<String> cardnameset = new HashSet<String>();
		Set<String> keys = sharedStringPreferences.getStringSet(key, new HashSet<String>());
		for(String k:keys){
			String[] temp = k.split("_");
			cardnameset.add(temp[temp.length-1]);
		}
		return cardnameset;
	}
	public String getCardLoc(String deck, String card){
		String key = DECKHEADER+deck+"_"+card;
		return sharedStringPreferences.getString(key, "This card doesn't exist");
	}
	
	public void clearAll(){
		Editor editor = sharedStringPreferences.edit();
		editor.clear();
		editor.commit();
	}
	
	public void savePreferences(String key, String value) {
		Editor editor = sharedStringPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String loadSavedStringPreferences(String k) {
		String name = sharedStringPreferences.getString(k, "AVAILABLE");
		return name;
	}
	public boolean loadSavedBooleanPreferences(String k) {
		boolean checkBoxValue = sharedStringPreferences.getBoolean(k, false);
		return checkBoxValue;
	}

	public void savePreferences(String key, boolean value) {
		Editor editor = sharedStringPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	
	private void savePreferences(String key, HashSet<String> value) {
		Editor editor = sharedStringPreferences.edit();
		editor.putStringSet(key, value);
		editor.commit();
	}

	public Set<String> getAllKey(){
		Set<String> decknameset = new HashSet<String>();
		Set<String> keys = sharedStringPreferences.getStringSet(rootdeck, new HashSet<String>());
		for(String k:keys){
			String[] temp = k.split("_");
			decknameset.add(temp[temp.length-1]);
		}
		return decknameset;
	}

}
