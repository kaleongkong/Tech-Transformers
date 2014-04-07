package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class addpic3<MainActivity> extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic3);
		
		Button goNext= (Button) findViewById(R.id.button2);
		goNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
	            Intent n = new Intent(addpic3.this,addpic4.class);
	            startActivity(n);
	        }
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wordtastic__main, menu);
		return true;
	}

}
