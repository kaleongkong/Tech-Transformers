package com.wordtastic.wordtastic;

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
		   if(item.getItemId()==2131230770){
			   Log.d("instructions", "about has been clicked");
			   HelpButton.openInstructions(screen);
		    }
		   return true;
			}
}
	