package com.example.wordtastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TextView title = (TextView) findViewById(R.id.deck);
        TextView c1 = (TextView) findViewById(R.id.cardcow);
        TextView c2 = (TextView) findViewById(R.id.carddaddy);
        TextView c3 = (TextView) findViewById(R.id.cardtable);
        TextView c4= (TextView) findViewById(R.id.cardtree);
        Button addnew = (Button) findViewById(R.id.add_new);
        Button delete = (Button) findViewById(R.id.delete);
        Button back = (Button) findViewById(R.id.back);
        FontModifier.initTypeface(getAssets(), title);
        FontModifier.initTypeface(getAssets(), c1);
        FontModifier.initTypeface(getAssets(), c2);
        FontModifier.initTypeface(getAssets(), c3);
        FontModifier.initTypeface(getAssets(), c4);
        FontModifier.initTypeface(getAssets(), addnew);
        FontModifier.initTypeface(getAssets(), delete);
        FontModifier.initTypeface(getAssets(), back);
    }
    public void onClickAddNew(View v){
    	Intent i = new Intent(this, AddCardActivity.class);
    	startActivity(i);
    }

    
}
