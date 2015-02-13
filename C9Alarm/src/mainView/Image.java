package mainView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Image {

	private Bitmap mBitmap;
	
	private Image(int w, int h){
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	}
	
	private Image(String path){
		mBitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream(path));
		
	}
	
	public static Image createImage(int w, int h){
		return new Image(w, h);
	}
	
	public static Image createImage(String path){
		return new Image(path);
	}
	
	public Bitmap getBitmap(){
		return mBitmap;
	}
	
	
	public int getWidth(){
		return mBitmap.getWidth();
	}
	
	public int getHeight(){
		return mBitmap.getHeight();
	}
	
	public Graphics getGraphics(){
		return new Graphics(new Canvas(mBitmap));
	}
	
	public void destroy(){
	    

	}
	
}
