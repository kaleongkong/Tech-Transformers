package com.example.wordtastic;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class MenuClick implements OnMenuItemClickListener{
	Activity screen;
	
	public MenuClick(Activity a){
		screen = a;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		   Log.d("menuitemclick", "menuitemclick has been called");
		   Log.d("itemid", "itemid is"+String.valueOf(item.getItemId()));
		   if(item.getItemId()==2131230772){
			   Log.d("about", "about has been clicked");
			   HelpButton.openAbout(screen);
		   } else if (item.getItemId()==2131230771){
			   HelpButton.openInstructions(screen);
		   }
	    return true;		
    }
	
}