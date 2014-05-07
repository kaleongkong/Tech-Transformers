package com.example.wordtastic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    //private FileInputStream fis;
       
/*    private Integer[] mImageIds = {
   		 R.drawable.baldeagle600x450,
   		 R.drawable.dolphins,
   		 R.drawable.lion_600x450,
   		 R.drawable.bunny,
   		 R.drawable.tiger,
   		 R.drawable.rhinoceros,
   		 R.drawable.chinchilla
    };*/

    private ImageView[] mImages;
    private Bitmap[] images;
    public ImageAdapter(Context c, Bitmap[] images) {
     mContext = c;
     mImages = new ImageView[images.length];
     this.images = images;
    }
    @SuppressWarnings("deprecation")
	public boolean createReflectedImages() {
         //The gap we want between the reflection and the original image
         final int reflectionGap = images.length/2;
         
         
         int index = 0;
         for (Bitmap image : images) {
       	  //Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
       	  int width = image.getWidth();
       	  int height = image.getHeight();
          
    
       	  //This will not scale but will flip on the Y axis
       	  Matrix matrix = new Matrix();
       	  matrix.preScale(1, -1);
          
       	  //Create a Bitmap with the flip matrix applied to it.
       	  //We only want the bottom half of the image
       	  Bitmap reflectionImage = Bitmap.createBitmap(image, 0, height/2, width, height/2, matrix, false);
          
              
	           //Create a new bitmap with same width but taller to fit reflection
	           Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Config.ARGB_8888);
	         
	          //Create a new Canvas with the bitmap that's big enough for
	          //the image plus gap plus reflection
	          Canvas canvas = new Canvas(bitmapWithReflection);
	          //Draw in the original image
	          canvas.drawBitmap(image, 0, 0, null);
	          //Draw in the gap
	          Paint deafaultPaint = new Paint();
	          canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
	          //Draw in the reflection
	          canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
         
	          //Create a shader that is a linear gradient that covers the reflection
	          Paint paint = new Paint(); 
	          LinearGradient shader = new LinearGradient(0, image.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP); 
	          //Set the paint to use this shader (linear gradient)
	          paint.setShader(shader); 
	          //Set the Transfer mode to be porter duff and destination in
	          paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
	          //Draw a rectangle using the paint with our linear gradient
	          canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint); 
	          
	          ImageView imageView = new ImageView(mContext);
	          imageView.setImageBitmap(bitmapWithReflection);
	          imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 180));
	          imageView.setScaleType(ScaleType.MATRIX);
	          mImages[index++] = imageView;
         
         }
      return true;
 }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {

     //Use this code if you want to load from resources
        ImageView i = new ImageView(mContext);
        //i.setImageResource(images[position]);
        i.setImageBitmap(images[position]);
        i.setLayoutParams(new CoverFlow.LayoutParams(4*130, 4*130)); //control the size of images
        i.setScaleType(ImageView.ScaleType.FIT_XY ); 
        
        //Make sure we set anti-aliasing otherwise we get jaggies
        BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
        drawable.setAntiAlias(true);
        return i;
     
     //return mImages[position];
    }
  /** Returns the size (0.0f to 1.0f) of the views 
     * depending on the 'offset' to the center. */ 
     public float getScale(boolean focused, int offset) { 
       /* Formula: 1 / (2 ^ offset) */ 
         return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
     } 

}
