package util;

import java.io.IOException;
import java.lang.ref.SoftReference;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;

public class AnimationsContainer {
	public int FPS = 30; // animation FPS

	// single instance procedures
	private static AnimationsContainer mInstance;

	private AnimationsContainer() {
	};

	public static AnimationsContainer getInstance() {
		if (mInstance == null)
			mInstance = new AnimationsContainer();
		return new AnimationsContainer();
	}

	// animation progress dialog frames

	/**
	 * @param imageView
	 * @return splash screen animation
	 */
	public FramesSequenceAnimation createSplashAnim(ImageView imageView,
			int[] anim) {

		return new FramesSequenceAnimation(imageView, createAnim(anim));
		// return new FramesSequenceAnimation(imageView, mSplashAnimFrames,10);
	}

	/**
	 * @param imageView
	 * @return splash screen animation
	 */
	public FramesSequenceAnimation createSplashAnim(ImageView imageView,
			int anmiResourse) {
		return new FramesSequenceAnimation(imageView, anmiParser(anmiResourse));
	}

	/**
	 * @param imageView
	 * @return splash screen animation
	 */
	public FramesSequenceAnimation createSplashAnim(ImageView imageView,
			Object mAnim, int width, int height) {
		if (mAnim == null)
			return null;
		if (mAnim instanceof AnimContainer[]) {
			return new FramesSequenceAnimation(imageView,
					(AnimContainer[]) mAnim, width, height);
		} else {
			return new FramesSequenceAnimation(imageView,
					anmiParser((Integer) mAnim), width, height);
		}

	}

	public AnimContainer[] createAnim(int[] anim) {
		AnimContainer[] res = new AnimContainer[anim.length];
		for (int i = 0; i < anim.length; i++) {
			res[i] = new AnimContainer(anim[i], 100);
		}

		return res;
	}

	public AnimContainer[] anmiParser(int anmi) {
		XmlResourceParser anmiParser = AlarmApplication.getApp().getResources()
				.getAnimation(anmi);
		// XmlPullParser anmiParser;
		int count = 0;
		try {
			while (anmiParser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (anmiParser.getEventType() == XmlResourceParser.START_TAG) {
					String name = anmiParser.getName();
					if (name.equals("item")) {
						count++;
					}
				} else if (anmiParser.getEventType() == XmlResourceParser.END_TAG) {
				} else if (anmiParser.getEventType() == XmlResourceParser.TEXT) {
				}
				anmiParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		anmiParser.close();
		AnimContainer[] anim = new AnimContainer[count];
		anmiParser = AlarmApplication.getApp().getResources()
				.getAnimation(anmi);
		try {
			count = 0;
			while (anmiParser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				// 如果是开始标签
				if (anmiParser.getEventType() == XmlResourceParser.START_TAG) {
					// 获取标签名称
					String name = anmiParser.getName();
					// 判断标签名称是否等于friend
					if (name.equals("item")) {
						int res = anmiParser.getAttributeResourceValue(
								"http://schemas.android.com/apk/res/android",
								"drawable", 100);
						int duration = anmiParser.getAttributeIntValue(
								"http://schemas.android.com/apk/res/android",
								"duration", 100);
						anim[count] = new AnimContainer(res, duration);
						count++;
					}
				} else if (anmiParser.getEventType() == XmlResourceParser.END_TAG) {
				} else if (anmiParser.getEventType() == XmlResourceParser.TEXT) {
				}
				// 下一个标签
				anmiParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		anmiParser.close();

		// int count = anmiParser.getAttributeCount();
		//
		// for (int i = 0; i < count; i++) {
		// anim[i] = new AnimContainer(anmiParser.getAttributeResourceValue(
		// "android", "drawable", 100),
		// anmiParser.getAttributeIntValue("android", "duration", 100));
		// }
		return anim;
	}

	public static class AnimContainer {
		int resBitmap;
		int duration;
		private String themeName;
		private boolean isNewType;

		public int getResBitmap() {
			return resBitmap;
		}

		public void setResBitmap(int resBitmap) {
			this.resBitmap = resBitmap;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public String getThemeName() {
			return themeName;
		}

		public void setThemeName(String themeName) {
			this.themeName = themeName;
		}

		public AnimContainer(int resBitmap, int duration, String themename) {
			super();
			this.resBitmap = resBitmap;
			this.duration = duration;
			setThemeName(themename);
			isNewType = true;
		}

		public AnimContainer(int resBitmap, int duration) {
			super();
			this.resBitmap = resBitmap;
			this.duration = duration;
			themeName = null;
			isNewType = false;
		}
	}

	/**
	 * AnimationPlayer. Plays animation frames sequence in loop
	 */
	public class FramesSequenceAnimation {
		private AnimContainer[] mAnimContainer; // animation frames
		private int mIndex; // current frame
		private boolean mShouldRun; // true if the animation should continue
									// running. Used to stop the animation
		private boolean mIsRunning; // true if the animation currently running.
									// prevents starting the animation twice
		private SoftReference<ImageView> mSoftReferenceImageView; // Used to
																	// prevent
																	// holding
																	// ImageView
																	// when it
																	// should be
																	// dead.
		private Handler mHandler;
		private OnAnimationStoppedListener mOnAnimationStoppedListener;

		private Bitmap mBitmap = null;
		private BitmapFactory.Options mBitmapOptions;

		Bitmap mBitmapBuff;
		private long mDelayMillis;

		private boolean isOverRun = true;

		public boolean isOverRun() {
			return isOverRun;
		}

		public void setOverRun(boolean isOverRun) {
			this.isOverRun = isOverRun;
		}

		public FramesSequenceAnimation(ImageView imageView,
				AnimContainer[] animContainer) {
			mHandler = new Handler();
			mAnimContainer = animContainer;
			mIndex = -1;
			mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
			mShouldRun = false;
			mIsRunning = false;
			mDelayMillis = mAnimContainer[0].duration;

			MyLogger.e("bitmap", "mAnimContainer", true);
			// mBitmapBuff = BitmapWorker.getBitmap(AlarmApplication.getApp(),
			// mAnimContainer[0].getResBitmap(),imageView.getWidth(),imageView.getHeight());
			// imageView.setImageBitmap(mBitmapBuff);//
			// .setImageResource(mFrames[0]);

			// imageView.setImageResource(mBitmapBuff);
			// use in place bitmap to save GC work (when animation images are
			// the same size & type)
			Bitmap bmp = null;
			if (imageView.getDrawable() instanceof BitmapDrawable) {
				bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
			}

			int width = imageView.getWidth();
			int height = imageView.getHeight();
			MyLogger.e("bitmap", width + " " + height);
			// Bitmap.Config config = bmp.getConfig();
			if (Build.VERSION.SDK_INT >= 11) {
				if (bmp != null) {
					mBitmap = Bitmap.createBitmap(width, height,
							bmp.getConfig());
					// MyLogger.e("bitmap", width + " " +
					// height+" "+bmp.getConfig().);
				} else {
					mBitmap = Bitmap.createBitmap(width, height,
							Config.ARGB_8888);
				}

				mBitmapOptions = new BitmapFactory.Options();
				// setup bitmap reuse options.
				mBitmapOptions.inBitmap = mBitmap;
				mBitmapOptions.inMutable = true;
				mBitmapOptions.inSampleSize = 1;

			}
			// mBitmapBuff = BitmapWorker.getBitmap(AlarmApplication.getApp(),
			// mAnimContainer[0].getResBitmap(), width, height);

			mBitmapBuff = SDResReadManager.getInstance().getResBitmap(
					mAnimContainer[0].getResBitmap(),
					mAnimContainer[0].getThemeName(), width, height);

			imageView.setImageBitmap(mBitmapBuff);
		}

		public FramesSequenceAnimation(ImageView imageView,
				AnimContainer[] animContainer, int width1, int height1) {
			mHandler = new Handler();
			mAnimContainer = animContainer;
			mIndex = -1;
			mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
			mShouldRun = false;
			mIsRunning = false;
			mDelayMillis = mAnimContainer[0].duration;

			// mBitmapBuff = BitmapWorker.getBitmap(AlarmApplication.getApp(),
			// mAnimContainer[0].getResBitmap(),imageView.getWidth(),imageView.getHeight());
			// imageView.setImageBitmap(mBitmapBuff);//
			// .setImageResource(mFrames[0]);

			// imageView.setImageResource(mBitmapBuff);
			// use in place bitmap to save GC work (when animation images are
			// the same size & type)
			/*
			 * Bitmap bmp = null; if (imageView.getDrawable() instanceof
			 * BitmapDrawable) { bmp = ((BitmapDrawable)
			 * imageView.getDrawable()).getBitmap(); }
			 * 
			 * MyLogger.e("bitmap", width + " " + height);
			 */
			// Bitmap.Config config = bmp.getConfig();
			mBitmapBuff = SDResReadManager.getInstance().getResBitmap(
					mAnimContainer[0].getResBitmap(),
					mAnimContainer[0].getThemeName(), width1, height1);
			// BitmapWorker.getBitmap(AlarmApplication.getApp(),
			// mAnimContainer[0].getResBitmap(),width1,height1);
			imageView.setImageBitmap(mBitmapBuff);// .setImageResource(mFrames[0]);

			// use in place bitmap to save GC work (when animation images are
			// the same size & type)
			if (Build.VERSION.SDK_INT >= 11) {
				Bitmap bmp = ((BitmapDrawable) imageView.getDrawable())
						.getBitmap();
				int width = bmp.getWidth();
				int height = bmp.getHeight();
				MyLogger.e("bitma", "sdk>11" + " " + width + " " + height + " "
						+ width1 + " " + height1);
				Bitmap.Config config = bmp.getConfig();
				mBitmap = Bitmap.createBitmap(width, height, config);
				mBitmapOptions = new BitmapFactory.Options();
				// setup bitmap reuse options.
				mBitmapOptions.inBitmap = mBitmap;
				mBitmapOptions.inMutable = true;
				mBitmapOptions.inSampleSize = 1;
			}
		}

		/*
		 * public void inil(ImageView imageView){ if(imageView!=null)
		 * mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
		 * else imageView = mSoftReferenceImageView.get(); mIndex = -1;
		 * mShouldRun = false; mIsRunning = false; mDelayMillis =
		 * mAnimContainer[0].duration;
		 * 
		 * mBitmapBuff = BitmapWorker.getBitmap(AlarmApplication.getApp(),
		 * mAnimContainer[0].getResBitmap());
		 * imageView.setImageBitmap(mBitmapBuff);//
		 * .setImageResource(mFrames[0]);
		 * 
		 * // use in place bitmap to save GC work (when animation images are //
		 * the same size & type) if (Build.VERSION.SDK_INT >= 11) { Bitmap bmp =
		 * ((BitmapDrawable) imageView.getDrawable()) .getBitmap(); int width =
		 * bmp.getWidth(); int height = bmp.getHeight(); Bitmap.Config config =
		 * bmp.getConfig(); mBitmap = Bitmap.createBitmap(width, height,
		 * config); mBitmapOptions = new BitmapFactory.Options(); // setup
		 * bitmap reuse options. mBitmapOptions.inBitmap = mBitmap;
		 * mBitmapOptions.inMutable = true; mBitmapOptions.inSampleSize = 1; } }
		 */
		private AnimContainer getNext() {
			mIndex++;
			if (mIndex >= mAnimContainer.length) {
				if (isOverRun) {
					mIndex = 0;
				} else {
					return null;
				}
			}
			return mAnimContainer[mIndex];
		}

		private AnimContainer getCurrent() {
			if(mIndex<0) return null;
			return mAnimContainer[mIndex];
		}

		/**
		 * Starts the animation
		 */
		public synchronized void start() {
			mShouldRun = true;
			if (mIsRunning)
				return;

			Runnable runnable = new Runnable() {
				Long lastTime = 0l;

				@Override
				public void run() {

					ImageView imageView = mSoftReferenceImageView.get();
					if (!mShouldRun || imageView == null) {
						mIsRunning = false;
						if (mOnAnimationStoppedListener != null) {
							mOnAnimationStoppedListener.AnimationStopped();
						}
						return;
					}

					mIsRunning = true;

					AnimContainer animContainerResLast = getCurrent();
					int offset  = 0;
					if(animContainerResLast!=null){
					 long lastDuration = animContainerResLast.duration;
					 offset = (int) (System.currentTimeMillis() - lastTime - lastDuration);
					}
					long mDelayMillis = 0; 
					AnimContainer animContainerRes;
					do {
						 animContainerRes = getNext();
						if (animContainerRes == null) {
							mShouldRun = false;
							mHandler.post(this);
							return;
						}
						mDelayMillis+= animContainerRes.getDuration();
					} while (offset > mDelayMillis);
 
					mHandler.postDelayed(this, mDelayMillis-offset);
					lastTime = System.currentTimeMillis();
					if (imageView.isShown()) {

						if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
							Bitmap bitmap = null;
							try {
								MyLogger.e("aaaa", "not null");
								bitmap = SDResReadManager
										.getInstance()
										.getResBitmap(
												animContainerRes.getResBitmap(),
												animContainerRes.getThemeName(),
												mBitmapOptions);
								// BitmapFactory.decodeResource(imageView.getResources(),
								// imageRes, mBitmapOptions);
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
								MyLogger.e("bitmap", "in set");
							} else {
								// imageView.setImageResource(animContainerRes
								// .getResBitmap());
								MyLogger.e("bitmap", "new set");
								if (!AlarmApplication.getApp().getUser()
										.getIsLowMemory()) {

									mBitmapBuff = SDResReadManager
											.getInstance()
											.getResBitmap(
													animContainerRes
															.getResBitmap(),
													animContainerRes
															.getThemeName(),
													imageView.getWidth(),
													imageView.getHeight());

									/*
									 * BitmapWorker.getBitmap(AlarmApplication.
									 * getApp(),
									 * animContainerRes.getResBitmap(),
									 * imageView.
									 * getWidth(),imageView.getHeight());
									 */
									imageView.setImageBitmap(mBitmapBuff);
								}
								if (mBitmap != null)
									mBitmap.recycle();
								mBitmap = null;
								// BitmapWorker.recyleBitmap(mBitmapBuff);
							}

						} else {
							if (!AlarmApplication.getApp().getUser()
									.getIsLowMemory()) {
								mBitmapBuff = SDResReadManager
										.getInstance()
										.getResBitmap(
												animContainerRes.getResBitmap(),
												animContainerRes.getThemeName(),
												imageView.getWidth(),
												imageView.getHeight());/*
																		 * BitmapWorker
																		 * .
																		 * getBitmap
																		 * (
																		 * AlarmApplication
																		 * .
																		 * getApp
																		 * (),
																		 * animContainerRes
																		 * .
																		 * getResBitmap
																		 * (),
																		 * imageView
																		 * .
																		 * getWidth
																		 * (),
																		 * imageView
																		 * .
																		 * getHeight
																		 * ());
																		 */
								imageView.setImageBitmap(mBitmapBuff);
							}
							if (mBitmap != null)
								mBitmap.recycle();
							mBitmap = null;
						}
					}

				}
			};

			mHandler.post(runnable);
		}

		/**
		 * Stops the animation
		 */
		public synchronized void stop() {

			mShouldRun = false;
		}

		public boolean isRun() {
			return mIsRunning;
		}

		/**
		 * Stops the animation
		 */
		public synchronized void recyle() {
			ImageView imageView = mSoftReferenceImageView.get();

			// if (imageView != null) {
			// imageView.setImageBitmap(null);
			// }
			// mIndex = 0;
			// if (mBitmap != null)
			// mBitmap.recycle();
			mBitmap = null;
			mBitmapBuff = null;
			// if (mBitmapBuff != null)
			// mBitmapBuff.recycle();

		}

		public void setmOnAnimationStoppedListener(
				OnAnimationStoppedListener mOnAnimationStoppedListener) {
			this.mOnAnimationStoppedListener = mOnAnimationStoppedListener;
		}

	}
}