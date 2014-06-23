package com.wordtastic.wordtastic;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FontModifier {
	public static void initTypeface(AssetManager am, TextView v) {
		Typeface sniglet = Typeface.createFromAsset(am, "fonts/Sniglet-Regular.ttf");
		v.setTypeface(sniglet);
	}
	public static void initTypeface(AssetManager am, Button v) {
		Typeface sniglet = Typeface.createFromAsset(am, "fonts/Sniglet-Regular.ttf");
		v.setTypeface(sniglet);
	}
	
}
