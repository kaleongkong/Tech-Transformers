package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public void onClickAddNew(View v){
    	Intent i = new Intent(this, addpic3.class);
    	startActivity(i);
    }

    
}
