package com.example.storagetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

   private EditText et;
   private String data;
   private String file = "mydata";
   private ImageView imgv;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      et = (EditText)(findViewById(R.id.editText1));
      imgv = (ImageView)findViewById(R.id.imageView1);

   }
   
   public void save(View view){
      data = et.getText().toString();
      try {
         FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
         fOut.write(data.getBytes());
         fOut.close();
         Toast.makeText(getBaseContext(),"file saved",
         Toast.LENGTH_SHORT).show();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   public void read(View view){
      try{
         FileInputStream fin = openFileInput(file);
         int c;
         String temp="";
         while( (c = fin.read()) != -1){
            temp = temp + Character.toString((char)c);
         }
         et.setText(temp);
         Toast.makeText(getBaseContext(),"file read",
         Toast.LENGTH_SHORT).show();

      }catch(Exception e){

      }
   }
   public void readImgFromDevice(View view){
	   Log.v("hello", "hello");
	   Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
	   photoPickerIntent.setType("image/*");
	   startActivityForResult(photoPickerIntent, 1);
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
        	   imgv.setImageBitmap(mBitmap);
        	   
		   } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
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
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}
