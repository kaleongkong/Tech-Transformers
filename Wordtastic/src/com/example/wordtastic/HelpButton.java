package com.example.wordtastic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;

public class HelpButton {

	//pop up settings button
	@SuppressLint("NewApi")

	public static void openSettings(View v, Activity a){
		Log.d("help", "help has been clicked");
		PopupMenu popupMenu = new PopupMenu(a, v);
		popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
	    popupMenu.setOnMenuItemClickListener(new MenuClick(a));	    
		popupMenu.show();
	}
	
	public static void openAbout(Activity a) {
		Log.d("about", "openAbout has been called");
		//Log.d("context", "context = "+ c.toString());
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);

		alertDialogBuilder.setTitle("About");

		alertDialogBuilder
			.setMessage("This is a CS160 project developed by:\nDominic Kong \nPatrick Lin \nMeghana Seshradi \nJane Sima")
			.setCancelable(true)
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();					
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	public static void openInstructions(Activity a){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);

		alertDialogBuilder.setTitle("Instructions");

		alertDialogBuilder
			.setMessage("Setting Up a Game:\nIn the gallery, scroll to the deck you wish to play. "
					+ "Click 'Start Game' and choose your game mode. Time challenge has 30 second limit."
					+ "\n\nPlaying a Game:\nClick the microphone button and say what you see in the image."
					+ "\n\nCreating New Cards/Decks:\nblah")
			.setCancelable(true)
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();					
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}	
}