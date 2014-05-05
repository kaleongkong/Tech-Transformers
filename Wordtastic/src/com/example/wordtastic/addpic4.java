package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class addpic4 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpic4);
		TextView title = (TextView) findViewById(R.id.name);
		Button back = (Button) findViewById(R.id.button2);
		Button goNext= (Button) findViewById(R.id.back);
		FontModifier.initTypeface(getAssets(), back);
		FontModifier.initTypeface(getAssets(), goNext);
		FontModifier.initTypeface(getAssets(), goNext);
		goNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
//				EditText text = (EditText)findViewById(R.id.name);
//				String value = text.getText().toString();
				
				Intent n = new Intent(addpic4.this,addpic5.class);
//				n.putExtra("name", value);
		        startActivity(n);
	        }
			});
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.wordtastic__main, menu);
		return true;
	}
	

}