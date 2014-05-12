package com.example.wordtastic;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	TextView deckthemetextview;
	String theme;
	TextView title;
	Button addnew;
	Button edit;
	ImageButton homeButton;
	Button done;
	Button clear;
	private ImageView imgv;
	
	
	AlertDialog.Builder deleteCardAlertDialogBuilder;
	TextView dialogtitle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
    	theme = intent.getStringExtra("deck_theme");
    	vocab_dict = new ArrayList<String>();
        loadImgs();
        outerlayout = (LinearLayout)findViewById(R.id.scrollviewlayout);
        setup();
        
        
        
        title = (TextView) findViewById(R.id.deck);
        addnew = (Button) findViewById(R.id.add_new);
        edit = (Button) findViewById(R.id.edit);
        deckthemetextview = (TextView) findViewById(R.id.deck);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        FontModifier.initTypeface(getAssets(), title);
        FontModifier.initTypeface(getAssets(), addnew);
        FontModifier.initTypeface(getAssets(), edit);
        
        
        // fill in any details dynamically here
        
        deckthemetextview.setText(theme + " Deck");
        done = (Button) findViewById(R.id.done);
        clear = (Button) findViewById(R.id.clear);
        FontModifier.initTypeface(getAssets(), done);
        FontModifier.initTypeface(getAssets(), clear);
        
        
        
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
	        
	        /*
	        //SETTING UP DIALOG BOX FOR EACH IMAGE BUTTON
	        image1.setOnClickListener(new OnClickListener() {
	        	
	        	@Override
	        	public void onClick(View v)
	        	{
	        		deleteCardAlertDialogBuilder = new AlertDialog.Builder(AddActivity.this);

	        		// set title
	            	dialogtitle = new TextView(AddActivity.this);
	            	dialogtitle.setText("Would you like to delete this card?");
	        		//alertDialogBuilder.setTitle("Add New Picture");
	            	FontModifier.initTypeface(getAssets(), dialogtitle);
	            	deleteCardAlertDialogBuilder.setCustomTitle(dialogtitle);
	        		//alertDialogBuilder.setT
	        			// set dialog message
	            	deleteCardAlertDialogBuilder
	        			//.setMessage("Add New Picture")
	        			.setCancelable(true)
	        			.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
	        				public void onClick(DialogInterface dialog,int id) {
	        					//Intent i = new Intent(AddActivity.this, CameraPreview.class);
	        					//i.putExtra("deck_theme", theme);
	        			    	//startActivity(i);
	        					dialog.cancel();
	        				}
	        			  })
	        			.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
	        				public void onClick(DialogInterface dialog,int id) {
	        					//STUFF TO REMOVE CARD GETS PUT HERE	        					
	        				}
	        			});
	            	
	        			// create and show alert dialog
	        			AlertDialog alertDialog = deleteCardAlertDialogBuilder.create();
	        			alertDialog.show();
	        		
	        	}//end onClick
	        });//end setOnClickListener
	        */
	        
        }//end for loop
    }//end setup method
    
    public void loadImgs(){
		ilsh = new ImgLocalStorageHandler(getResources());
		cardnamelist = new ArrayList<String>();
		cardlocationlist = new ArrayList<String>();
		imglist = new ArrayList<Drawable>();
		hspmap = new HashSharedPreferenceMap(this);
		cardnamelist.addAll(hspmap.getAllCardNamesInDeck(theme));
		Log.v("deck card name list", hspmap.getAllCardNamesInDeck(theme).toString());
		for(String k:cardnamelist){
			Log.v("card location", hspmap.getCardLoc(theme, k));
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
    	TextView dialogtitle = new TextView(this);
    	dialogtitle.setText("Add New Picture");
		//alertDialogBuilder.setTitle("Add New Picture");
    	FontModifier.initTypeface(getAssets(),dialogtitle);
    	alertDialogBuilder.setCustomTitle(dialogtitle);
		//alertDialogBuilder.setT
			// set dialog message
		alertDialogBuilder
			//.setMessage("Add New Picture")
			.setCancelable(true)
			.setPositiveButton("Take a New Picture",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent i = new Intent(AddActivity.this, CameraPreview.class);
					i.putExtra("deck_theme", theme);
			    	startActivity(i);
				}
			  })
			.setNegativeButton("Upload From Device",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, 1);
				}
			});
			// create and show alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri chosenImageUri = data.getData();
            Log.v("image path", chosenImageUri.getPath());
            Bitmap mBitmap = null;
            try {
         	   mBitmap = Media.getBitmap(this.getContentResolver(), chosenImageUri);
         	   mBitmap = getResizedBitmap(mBitmap, 1000);
         	   //imgv.setImageBitmap(mBitmap); //don't need this
         	   
         	  //Bitmap bmp = intent.getExtras().get("data");
         	  Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
         	  ByteArrayOutputStream stream = new ByteArrayOutputStream();
         	  bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
         	  byte[] byteArray = stream.toByteArray();
         	   
         	   /*
         	   //CONVERT TO BYTE ARRAY AND PUT ALL THAT TRY CATCH STUFF FROM CAMERAPREVIEW IN HERE
         	   OutputStream fileOutputStream = getContentResolver().openOutputStream(chosenImageUri);
         	   fileOutputStream.write(data);
        	   fileOutputStream.flush();
        	   fileOutputStream.close();
        	   */
         	   
         	   /*
         	   Log.v("uri path", pictureUri.getPath());
         	   OutputStream fileOutputStream = getContentResolver().openOutputStream(pictureUri);
         	   fileOutputStream.write(data);
         	   fileOutputStream.flush();
         	   fileOutputStream.close();
         	   */
         	   
         	   
 		   } catch (FileNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 		   } catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 		   } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
 		   }
            
           /*
            * Intent previous = getIntent();
		         	Intent i = new Intent(c, addpic3.class);
		         	i.putExtra("pictureUri", pictureUri.toString());
		         	i.putExtra("deck_theme", previous.getStringExtra("deck_theme"));
					c.startActivity(i);
            */
        }
    }
    
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    
    
    public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}
    
    public void onClickEditButton(View v){
    	done.setVisibility(View.VISIBLE);
    	clear.setVisibility(View.VISIBLE);
    	edit.setVisibility(View.GONE);
    }

    public void onClickDoneButton(View v){
    	done.setVisibility(View.GONE);
    	clear.setVisibility(View.GONE);
    	edit.setVisibility(View.VISIBLE);
    }
    
    public void onClickClearButton(View v){
    	//DELETE THE DECK HERE
    	//THIS ISNT CORRECT IS IT?!?!?!
    	hspmap.deleteDeck(theme);
    	
    }
    
}
