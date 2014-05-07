package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddCardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);
		//Button back = (Button)findViewById(R.id.back);
		Button upload = (Button)findViewById(R.id.upload);
		ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
		Button take_pic = (Button)findViewById(R.id.take_pic);
		TextView title = (TextView) findViewById(R.id.add_new_card);
		//FontModifier.initTypeface(getAssets(), back);
		FontModifier.initTypeface(getAssets(), upload);
		FontModifier.initTypeface(getAssets(), take_pic);
		FontModifier.initTypeface(getAssets(), title);
	}

	public void onClickTakePicture(View v){
		Intent i = new Intent(this, CameraPreview.class);
		startActivity(i);
	}
	public void onClickHomeButton(View v){
		Intent i = new Intent(this, GalleryActivity.class);
		this.startActivity(i);
	}

}
