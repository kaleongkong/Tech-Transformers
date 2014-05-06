package com.example.wordtastic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImgLocalStorageHandler {
	Resources r;
	public ImgLocalStorageHandler(Resources r){
		this.r = r;
	}
	
	public String getImgPath(){
		return Environment.DIRECTORY_DCIM+"/wordtastic";
	}
	
	public Bitmap loadImageFromInternal(String image_name){
	    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	    File imgFile = new  File(path.toString()+"/wordtastic/"+image_name+".jpg");
	    if(imgFile.exists()){
	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	        return myBitmap;
 	    }
 	    return null;
    }
	   
    public void saveDrawableToInternal(int imgid, String image_name){
	    Bitmap bm = BitmapFactory.decodeResource(r, imgid);
	    saveBitmapToInternal(bm, image_name);
	}
    
    public void saveBitmapToInternal(Bitmap bm, String image_name){
	    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/wordtastic");
   	    Log.v("path", path.toString());
		File file = new File(path, image_name+".jpg");
		if(file.exists()){
		  	 file.delete();
		 }
		path.mkdirs();
		try {
		    FileOutputStream outStream = new FileOutputStream(file);
		    bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
		    outStream.flush();
		    outStream.close();
		    bm.recycle();
		    bm = null;
		 } catch (IOException e) {
		 	// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
}
