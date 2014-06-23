package com.wordtastic.wordtastic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GalleryActivity extends Activity{
	private Integer[] mImageIds = {
			 R.drawable.abcblocks,
			 R.drawable.fruitbowl,
			 R.drawable.tree,
			 R.drawable.lion,
			 R.drawable.globe_cartoon,
			 R.drawable.table
	    };
	CoverFlow coverFlow;
	private ArrayList<Bitmap> images;
	private RelativeLayout l;
	private int selected;
	private String setlecteddeck;
	private TextView deckname;
	private HashSharedPreferenceMap hspm; 
	private ImgLocalStorageHandler ilsh;
	private ArrayList<String> decknamelist;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_main);
		//setUp();
		hspm = new HashSharedPreferenceMap(this);
		ilsh = new ImgLocalStorageHandler(getResources());
		getAllDeckName();
		l = (RelativeLayout) findViewById(R.id.activity_gallery_main);
		Log.v("hello", String.valueOf(l));
		TextView title = (TextView) findViewById(R.id.gallery);
		Log.v("hello", String.valueOf(title));
		deckname = (TextView)findViewById(R.id.deckname);
		Button play = (Button)findViewById(R.id.galleryplay);
		//Button back = (Button)findViewById(R.id.back);
		ImageButton home = (ImageButton)findViewById(R.id.homeButton);
		Button newdeck = (Button) findViewById(R.id.newdeck);
		Button editdeck = (Button) findViewById(R.id.editdeck);
		selected =(mImageIds.length-1)/2;
		setlecteddeck = decknamelist.get(selected);
		deckname.setText("Current Deck: "+setlecteddeck);
		
		
		FontModifier.initTypeface(getAssets(), title);
		FontModifier.initTypeface(getAssets(), deckname);
		FontModifier.initTypeface(getAssets(), play);
		FontModifier.initTypeface(getAssets(), newdeck);
		FontModifier.initTypeface(getAssets(), editdeck);
		loadBitMaps();
    	setUpCoverFlow();
    	
	}
	
	
	
	
	private void getAllDeckName(){
		decknamelist = new ArrayList<String>();
		decknamelist.addAll(hspm.getAllDeckNames());
	}
	
	public void onClickPlay(View v){
		Intent i = new Intent(this, ChooseGameMode.class);
		i.putExtra("deck_theme", setlecteddeck);
		startActivity(i);
	}
	public void onClickEditDeck(View v){
		Intent i = new Intent(this, AddActivity.class);
		i.putExtra("deck_theme", setlecteddeck);
		startActivity(i);
	}
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
	
	
	public void onClickNewDeck(View v){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Add New Deck");
		alert.setMessage("Type the name of the new deck below");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			// Canceled.
			dialog.cancel();
		  }
		});

		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  // Do something with value!
			  try {
				hspm.saveDeckNamePreferences(value);
				coverFlow.setVisibility(View.GONE);
				decknamelist.add(value);
				loadBitMaps();
		    	setUpCoverFlow();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		});

		alert.show();
	}
	
	//pop up settings button
	@SuppressLint("NewApi")
	public void openSettings(View v){
		HelpButton.openSettings(v, this);
	}
//	      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {	   
//	   @Override
//	   public boolean onMenuItemClick(MenuItem item) {
//	    return true;
//	   }
//	  });
	    

	
	public void loadBitMaps(){
		int k= 0;
		//images = new Bitmap[decknamelist.size()];
		images = new ArrayList<Bitmap>();
		for(String n: decknamelist){
			Log.v("dynamic", n);
			ArrayList<String> cardnameset = new ArrayList<String>();
			cardnameset.addAll(hspm.getAllCardNamesInDeck(n));
			if(cardnameset.size()>0){
				images.add(Bitmap.createScaledBitmap(ilsh.loadImageFromInternal(cardnameset.get(0)),200,200,true));
			}else{
				images.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.notavailable),200,200,true));
			}
			k++;
		}
	}
    
	protected void onStop() {
	    super.onStop();
	    setContentView(new View(this));
	    coverFlow = null;
	    images = null;
	    l = null;
	  }
	
	
	@SuppressWarnings("deprecation")
	public void setUpCoverFlow(){
        
        coverFlow = new CoverFlow(this);
        Bitmap[] imagesarray = new Bitmap[images.size()];
        
        coverFlow.setAdapter(new ImageAdapter(this, images.toArray(imagesarray)));
        
        ImageAdapter coverImageAdapter =  new ImageAdapter(this, images.toArray(imagesarray));
        coverFlow.setAdapter(coverImageAdapter);
        
        coverFlow.setSpacing(-175); //set spacing between images, more negative, the closer those images
        coverFlow.setSelection(images.size()/2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener(new OnItemSelectedListener(){

   		@Override
   		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
   			Log.v("id", String.valueOf(arg2));
   			selected = arg2;
   			setlecteddeck = decknamelist.get(selected);
   			deckname.setText("Current Deck: "+ setlecteddeck);
   		}

   		@Override
   		public void onNothingSelected(AdapterView<?> arg0) {
   			// TODO Auto-generated method stub
   			
   		}});
        coverFlow.setOnItemClickListener(new OnItemClickListener(){

	   		@Override
	   		public void onItemClick(AdapterView<?> a, View v, int num1, long num2) {
	   			
	   		}
   	 	});
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated
        lp.setMargins(0, height/4, 0, (int)(height/4));
        l.addView(coverFlow, lp);
        
    }
	
	public void onBackPressed(){
		Intent i = new Intent(this, Wordtastic_MainActivity.class);
		this.startActivity(i);
	}
}
