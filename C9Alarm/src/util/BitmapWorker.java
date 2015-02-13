package util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.util.LruCache;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;

public class BitmapWorker {
	 private static final String IMAGE_CACHE_DIR = "images";
	 public static final String EXTRA_IMAGE = "extra_image";
	public BitmapCache mImageCache;
	

	private static BitmapWorker mBitmapWorker;
	
	public BitmapWorker(Context c) {
		super();
		inial(c);
	}

	public void inial(Context c){
		
	     mImageCache = new  BitmapCache();
	}
	
	public static BitmapWorker getInstance(Context c){
		if(mBitmapWorker==null)mBitmapWorker = new BitmapWorker(c);
		return mBitmapWorker;
	}
      
	private static Bitmap error;

	private static int sizeBound = 1;
	
	
	public static void recyleBitmap(Bitmap b) {
		if (b != null) {
		b.recycle();
			b = null;
		}
	}
	
	public static void recyleBitmap(Bitmap[] b) {
		for(int i =0;i<b.length;i++){
		if (b[i] != null) {
			b[i].recycle();
			b[i] = null;
		}
		}
	}

/*	private static synchronized void errorBitmap(Context c) {
		if (error == null || error.isRecycled()) {
			InputStream is = c.getResources().openRawResource(R.drawable.error);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 4;
			try {
				error = BitmapFactory.decodeStream(is, null, options);
			} catch (Exception e) {
				e.printStackTrace();
				error = Bitmap.createBitmap(1, 1, Config.RGB_565);
			}

		}

	}

	// static HashMap<String,SoftReference<Bitmap>> sb = new
	// HashMap<String,SoftReference<Bitmap>>;
	public  Bitmap getBitmap(Context c, int res) {
		
		
		//ImageResizer.decodeSampledBitmapFromResource(res, resId, reqWidth, reqHeight, cache)
		
	//	BitmapWorker.testBitmap(c);
		InputStream is = c.getResources().openRawResource(res);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		// options.inSampleSize = 4 ;
		//options.inSampleSize = sizeBound;
		Bitmap btp1;
		try {
			btp1 = BitmapFactory.decodeStream(is, null, options);

		} catch (Exception e) {
			e.printStackTrace();
			btp1 = error;
			sizeBound *= 2;
		}
		return btp1;
	}*/

	public static  Bitmap getBitmap(InputStream is,
			BitmapFactory.Options options) {
		
		//BitmapWorker.testBitmap(c);
		Bitmap btp1 = null;
		try {
			btp1 = BitmapFactory.decodeStream(is, null, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return btp1;
	}
	
//	public static  Bitmap getBitmap(Context c, int res,
//			BitmapFactory.Options options) {
//		
//		//BitmapWorker.testBitmap(c);
//		InputStream is = c.getResources().openRawResource(res);
//		Bitmap btp1 = null;
//		try {
//			btp1 = BitmapFactory.decodeStream(is, null, options);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return btp1;
//	}
	
	public static BitmapFactory.Options getSampleSizeOption(InputStream is, int width, int height){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		
		options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeStream(is, null, options);  
	    options.inSampleSize = computeSampleSize(options,Math.min(width, height) ,width*height);  
	  //  MyLogger.e("bitmap","w:"+width+" h:"+height+"size:"+ options.inSampleSize);
	    options.inJustDecodeBounds = false; 
	    return options;
	}
	
	public static  Bitmap getBitmap(Context c,int res,BitmapFactory.Options options) {
		//BitmapWorker.testBitmap(c);
		
		InputStream is = c.getResources().openRawResource(res);
		return getBitmap(is,options);
	}

	public static  Bitmap getBitmap(Context c, int res, int width, int height) {
	//	MyLogger.e("bitmap", "getBitmap by  res"+width+" "+height);
		//BitmapWorker.testBitmap(c);
		InputStream is = c.getResources().openRawResource(res);
		BitmapFactory.Options options  = getSampleSizeOption(is, width, height);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		is = c.getResources().openRawResource(res);
		return getBitmap(is,options);
	/*	BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		
		options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeStream(is, null, options);  
	    try {
			is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	    is = c.getResources().openRawResource(res);
	    options.inSampleSize = computeSampleSize(options,Math.min(width, height) ,width*height);  
	    MyLogger.e("bitmap","w:"+width+" h:"+height+"size:"+ options.inSampleSize,true);
	    options.inJustDecodeBounds = false;  
	    
	    
	    
		Bitmap btp1;
		try {
			btp1 = BitmapFactory.decodeStream(is, null, options);
		
			//sizeBound = 1;
		} catch (Exception e) {
			e.printStackTrace();
			btp1 = error;
			//sizeBound *= 2;
		}
		return btp1;*/

	}
	private static void addInBitmapOptions(BitmapFactory.Options options,
			BitmapCache cache) {
	    // inBitmap only works with mutable bitmaps, so force the decoder to
	    // return mutable bitmaps.
	    options.inMutable = true;

	    if (cache != null) {
	        // Try to find a bitmap to use for inBitmap.
	        Bitmap inBitmap = cache.getBitmapFromReusableSet(options);

	        if (inBitmap != null) {
	            // If a suitable bitmap has been found, set it as the value of
	            // inBitmap.
	    //    	MyLogger.e("aaaa", "has bitmap");
	            options.inBitmap = inBitmap;
	        }else{
	    //    	MyLogger.e("aaaa", "no bitmap");
	        }
	    }
	}


	
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		boolean isLowMemory = AlarmApplication.getApp().getUser().getIsLowMemory();
		float offset = isLowMemory==true?-0.25f:0.1f;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) (offset+(Math
				.sqrt(w * h / maxNumOfPixels)));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;

		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	class BitmapCache{
		
		private LruCache<String, Bitmap> mMemoryCache;

		protected BitmapCache() {
		  
		    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		   
		    final int cacheSize = maxMemory / 8;
		    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
		        @Override
		        protected int sizeOf(String key, Bitmap bitmap) {
		      
		            return bitmap.getByteCount() / 1024;
		        }
		    };
		}

		public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		    if (getBitmapFromMemCache(key) == null) {
		        mMemoryCache.put(key, bitmap);
		      //  ms.add(new SoftReference<Bitmap>(bitmap));
		    }
		}

		public Bitmap getBitmapFromMemCache(String key) {
		    return mMemoryCache.get(key);
		}
		
		List<SoftReference<Bitmap>> ms = new LinkedList<SoftReference<Bitmap>>();
		protected Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
	        Bitmap bitmap = null;

	    if (ms != null && !ms.isEmpty()) {
	        synchronized (ms) {
	            final Iterator<SoftReference<Bitmap>> iterator
	                    = ms.iterator();
	            Bitmap item;

	            while (iterator.hasNext()) {
	                item = iterator.next().get();

	                if (null != item && item.isMutable()) {
	                    // Check to see it the item can be used for inBitmap.
	                    if (canUseForInBitmap(item, options)) {
	                        bitmap = item;

	                        // Remove from reusable set so it can't be used again.
	                        iterator.remove();
	                        break;
	                    }
	                } else {
	                    // Remove from the set if the reference has been cleared.
	                    iterator.remove();
	                }
	            }
	        }
	    }
	    return bitmap;
	}
		
		 boolean canUseForInBitmap(
		        Bitmap candidate, BitmapFactory.Options targetOptions) {

		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		        // From Android 4.4 (KitKat) onward we can re-use if the byte size of
		        // the new bitmap is smaller than the reusable bitmap candidate
		        // allocation byte count.
		        int width = targetOptions.outWidth / targetOptions.inSampleSize;
		        int height = targetOptions.outHeight / targetOptions.inSampleSize;
		        int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
		        return byteCount <= candidate.getAllocationByteCount();
		    }

		    // On earlier versions, the dimensions must match exactly and the inSampleSize must be 1
		    return candidate.getWidth() == targetOptions.outWidth
		            && candidate.getHeight() == targetOptions.outHeight
		            && targetOptions.inSampleSize == 1;
		}
		
		/**
		 * A helper function to return the byte usage per pixel of a bitmap based on its configuration.
		 */
		 int getBytesPerPixel(Config config) {
		    if (config == Config.ARGB_8888) {
		        return 4;
		    } else if (config == Config.RGB_565) {
		        return 2;
		    } else if (config == Config.ARGB_4444) {
		        return 2;
		    } else if (config == Config.ALPHA_8) {
		        return 1;
		    }
		    return 1;
		}
	}
}
