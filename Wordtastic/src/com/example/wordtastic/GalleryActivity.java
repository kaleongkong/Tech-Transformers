package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GalleryActivity extends Activity{
	private Integer[] mImageIds = {
			 R.drawable.baldeagle600x450,
			 R.drawable.dolphins,
			 R.drawable.tree,
			 R.drawable.lion_600x450,
			 R.drawable.cow,
			 R.drawable.table
	    };
	private Bitmap[] images;
	public RelativeLayout l;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_main);
		l = (RelativeLayout) findViewById(R.id.activity_gallery_main);
		Log.v("hello", String.valueOf(l));
		TextView title = (TextView) findViewById(R.id.gallery);
		Log.v("hello", String.valueOf(title));
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
		resourceIdToBitMap();
    	setUpCoverFlow();
    	
	}
	public void onClickPlay(View v){
		Intent i = new Intent(this, ChooseGameMode.class);
		startActivity(i);
	}
	public void onClickEditDeck(View v){
		Intent i = new Intent(this, AddActivity.class);
		startActivity(i);
	}
	public void resourceIdToBitMap(){
		int k= 0;
	    images=new Bitmap[mImageIds.length];
	    for(int i: mImageIds){
	    	Bitmap originalImage = BitmapFactory.decodeResource(getResources(), i);
	    	images[k]= originalImage;
	    	k++;
	    }
	}
    
	@SuppressWarnings("deprecation")
	public void setUpCoverFlow(){
        CoverFlow coverFlow;
        coverFlow = new CoverFlow(this);
        
        coverFlow.setAdapter(new ImageAdapter(this, images));
        
        ImageAdapter coverImageAdapter =  new ImageAdapter(this, images);
        coverFlow.setAdapter(coverImageAdapter);
        
        coverFlow.setSpacing(-175); //set spacing between images, more negative, the closer those images
        coverFlow.setSelection(images.length/2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener(new OnItemSelectedListener(){

   		@Override
   		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
   				long arg3) {
   			Log.v("id", String.valueOf(arg2));
   			
   		}

   		@Override
   		public void onNothingSelected(AdapterView<?> arg0) {
   			// TODO Auto-generated method stub
   			
   		}});
        coverFlow.setOnItemClickListener(new OnItemClickListener(){

	   		@Override
	   		public void onItemClick(AdapterView<?> a, View v, int num1, long num2) {
	   			//Log.v("id", String.valueOf(num1));
	   		}
   	 	});
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated
        lp.setMargins(0, height/3, 0, (int)(height/4));
        l.addView(coverFlow, lp);
        
    }
}
