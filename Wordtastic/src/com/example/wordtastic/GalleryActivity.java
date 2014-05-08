package com.example.wordtastic;

import android.app.Activity;
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
	private Bitmap[] images;
	private RelativeLayout l;
	private int selected;
	private TextView deckname;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_main);
		//setUp();
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
		selected =mImageIds.length/2;
		deckname.setText("Current Deck: Animals");//+String.valueOf(selected));
		
		FontModifier.initTypeface(getAssets(), title);
		FontModifier.initTypeface(getAssets(), deckname);
		FontModifier.initTypeface(getAssets(), play);
		//FontModifier.initTypeface(getAssets(), back);
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
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
	
	//pop up settings button
	public void openSettings(View v){
		PopupMenu popupMenu = new PopupMenu(GalleryActivity.this, v);
	      popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
	    
	      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	   
	   @Override
	   public boolean onMenuItemClick(MenuItem item) {
	    return true;
	   }
	  });
	    
	      popupMenu.show();
	}
	
	public void resourceIdToBitMap(){
		int k= 0;
	    images=new Bitmap[mImageIds.length];
	    
	    for(int i: mImageIds){
	    	Bitmap originalImage = BitmapFactory.decodeResource(getResources(), i);
	    	images[k]= Bitmap.createScaledBitmap(originalImage, 200, 200, true);
	    	originalImage.recycle();
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
        
        coverFlow.setAdapter(new ImageAdapter(this, images));
        
        ImageAdapter coverImageAdapter =  new ImageAdapter(this, images);
        coverFlow.setAdapter(coverImageAdapter);
        
        coverFlow.setSpacing(-175); //set spacing between images, more negative, the closer those images
        coverFlow.setSelection(images.length/2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener(new OnItemSelectedListener(){

   		@Override
   		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
   			Log.v("id", String.valueOf(arg2));
   			selected = arg2;
   			//deckname.setText("Current Deck: "+String.valueOf(selected));
   			
   			if(selected == 0)
   			{
   				deckname.setText("Current Deck: Alphabet");
   			}
   			if(selected == 1)
   			{
   				deckname.setText("Current Deck: Fruits");
   			}
   			if(selected == 2)
   			{
   				deckname.setText("Current Deck: Plants");
   			}
   			if(selected == 3)
   			{
   				deckname.setText("Current Deck: Animals");
   			}
   			if(selected == 4)
   			{
   				deckname.setText("Current Deck: Places");
   			}
   			if(selected == 5)
   			{
   				deckname.setText("Current Deck: Furniture");
   			}
   			
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
}
