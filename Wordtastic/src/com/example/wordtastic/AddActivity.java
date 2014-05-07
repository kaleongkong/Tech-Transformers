package com.example.wordtastic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddActivity extends Activity {
	LinearLayout outerlayout;
	ImgLocalStorageHandler ilsh;
	HashSharedPreferenceMap hspmap;
	ArrayList<String> cardnamelist;
	ArrayList<String> cardlocationlist;
	ArrayList<Drawable> imglist;
	ArrayList<String> vocab_dict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TextView title = (TextView) findViewById(R.id.deck);
        //TextView c1 = (TextView) findViewById(R.id.cardcow);
        //TextView c2 = (TextView) findViewById(R.id.carddaddy);
        //TextView c3 = (TextView) findViewById(R.id.cardtable);
        //TextView c4= (TextView) findViewById(R.id.cardtree);
        Button addnew = (Button) findViewById(R.id.add_new);
        Button delete = (Button) findViewById(R.id.delete);
        //Button back = (Button) findViewById(R.id.back);
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        FontModifier.initTypeface(getAssets(), title);
        //FontModifier.initTypeface(getAssets(), c1);
        //FontModifier.initTypeface(getAssets(), c2);
        //FontModifier.initTypeface(getAssets(), c3);
        //FontModifier.initTypeface(getAssets(), c4);
        FontModifier.initTypeface(getAssets(), addnew);
        FontModifier.initTypeface(getAssets(), delete);
        //FontModifier.initTypeface(getAssets(), back);
        
        vocab_dict = new ArrayList<String>();
        // fill in any details dynamically here
        loadImgs();
        outerlayout = (LinearLayout)findViewById(R.id.scrollviewlayout);
        setup();
        
        
        
    }
    @SuppressLint("NewApi")
	public void setup(){
    	
        int i = 0;
        for(Drawable d: imglist){
        	LinearLayout innerlayout1 = new LinearLayout(this);
            innerlayout1.setOrientation(LinearLayout.HORIZONTAL);
            
	        ImageButton image1 = new ImageButton(this);
	        image1.setImageDrawable(d);
	        image1.setBackground(null);
	        TextView textView = new TextView(this);
	        textView.setText(vocab_dict.get(i));
	        textView.setPadding(150, 50, 0, 0);
	        textView.setTextSize(20);
	        FontModifier.initTypeface(getAssets(), textView);
	        outerlayout.addView(innerlayout1);
	        innerlayout1.addView(image1);
	        innerlayout1.addView(textView);
	        i++;
        }
    }
    
    public void loadImgs(){
		ilsh = new ImgLocalStorageHandler(getResources());
		cardnamelist = new ArrayList<String>();
		cardlocationlist = new ArrayList<String>();
		imglist = new ArrayList<Drawable>();
		hspmap = new HashSharedPreferenceMap(this);
		cardnamelist.addAll(hspmap.getAllCardNamesInDeck("animal"));
		Log.v("deck card name list", hspmap.getAllCardNamesInDeck("animal").toString());
		for(String k:cardnamelist){
			Log.v("card location", hspmap.getCardLoc("animal", k));
			imglist.add(new BitmapDrawable(getResources(),Bitmap.createScaledBitmap(ilsh.loadImageFromInternal(k),400,400,true)));
			vocab_dict.add(k);
		}
	}
    public void onClickAddNew(View v){
    	/*
    	Intent i = new Intent(this, AddCardActivity.class);
    	startActivity(i);
    	*/
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Add New Picture");
 
			// set dialog message
		alertDialogBuilder
			//.setMessage("Add New Picture")
			.setCancelable(true)
			.setPositiveButton("Take a New Picture",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent i = new Intent(AddActivity.this, CameraPreview.class);
			    	startActivity(i);
				}
			  })
			.setNegativeButton("Upload From Device",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});
			// create and show alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
    }
    
    public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}

    
}
