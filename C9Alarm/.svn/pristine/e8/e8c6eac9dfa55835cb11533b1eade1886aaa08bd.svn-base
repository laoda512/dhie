package com.tavx.C9Alarm;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources.NotFoundException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.os.Vibrator;
import android.view.animation.AccelerateInterpolator;
import android.widget.VideoView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alam.connector.MyLogger;

public class Media {
	Context c;

	private Vibrator mVibrator;
	SoundPool mSoundPool;
	private Map<Object, Integer> soundList;
	private static final long[] sVibratePattern = new long[] { 500, 500 };
	AudioManager am;
	
	
	public static final float MAX_VOL = 1f;
	private Media(Context c) {
		this.c = c;
		mVibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);

		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		mMediaPlayer.setLooping(true);
		
		
	
		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundList = new HashMap<Object, Integer>();

		am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		am.setStreamVolume(AudioManager.STREAM_ALARM,
				am.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);
	}

	public void loadSoundPoolRes(int resId, int key) {
		soundList.put(key, mSoundPool.load(c, resId, 0));
	}
	
	public int loadSoundPoolRes(String path) {
		if(soundList.containsKey(path)){
			return soundList.get(path);
		}else{
			int key =  mSoundPool.load(path, 0);
			soundList.put(path, key);
			return key;
		}
	}
	
	public <T> void playSound(T res) {
		if(mSoundPlayer!=null){
			try {
				if(mSoundPlayer.isPlaying()==true){
					mSoundPlayer.stop();
					mSoundPlayer.release();
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}else{
			mSoundPlayer = new MediaPlayer();
			mSoundPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mSoundPlayer.setLooping(false);
		}
		try {
			if(res instanceof String)
			mSoundPlayer.setDataSource((String) res);
			else{
				AssetFileDescriptor afd = c.getResources().openRawResourceFd(
						(Integer) res);
				mSoundPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
			}
			
			mSoundPlayer.prepare();
			mSoundPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mSoundPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mSoundPlayer.release();
				mSoundPlayer = null;
			}
		});
	}

	public void playSound(int key) {
		//mSoundPool.play(soundList.get(key), 1.0f, 1.0f, 0, 0, 1.0f);
		mSoundPool.play(key, 1.0f, 1.0f, 0, 0, 1.0f);
	}

	public static Media getInstance(Context c) {
		if (m == null)
			m = new Media(c);
		return m;

	}

	/* MediaPlayer对象 */
	private volatile MediaPlayer mMediaPlayer = null;
	private MediaPlayer mSoundPlayer = null;
	private static Media m;

	/* 音乐的路径 */
	private static final String MUSIC_PATH = new String("/sdcard/");

	// 停止按钮

	public void Stop() {
		
		if(downAnimator!=null&&downAnimator.isRunning()){
			downAnimator.cancel();
			downAnimator =null;
		}
		/* 是否正在播放 */
		try {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		}
		
		mMediaPlayer = new MediaPlayer();
		// cancel the vibrator
		mVibrator.cancel();
	}

	public void playMusic(String path, boolean vibrate) {
		if (vibrate) {
			mVibrator.vibrate(sVibratePattern, 0);
		}
		try {

			mMediaPlayer.prepare();
		} catch (Exception e) {
			MyLogger.e("alarm", e.toString());
		}

		mMediaPlayer.start();
	}

	
	
	public void playMusic(int res, boolean music, boolean vibrate,
			boolean slowLoud) {
		// Stop();

		/* 重置MediaPlayer */
		// mMediaPlayer.reset();
		/* 设置要播放的文件的路径 */
		// mMediaPlayer.release();

		if (vibrate) {
			mVibrator.vibrate(sVibratePattern, 0);
		}
		if (music) {
			try {
				mMediaPlayer.release();
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				AssetFileDescriptor afd = c.getResources().openRawResourceFd(
						res);
				mMediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
				mMediaPlayer.prepare();
				

			} catch (Exception e) {
				MyLogger.e("alarm", e.toString());
			}
			if(slowLoud){
			mMediaPlayer.setVolume(0.05f, 0.05f);
			dropUpAnim();
			}else{
				
				try {
					if(downAnimator!=null&&downAnimator.isRunning()){
						downAnimator.cancel();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mMediaPlayer.setVolume(MAX_VOL, MAX_VOL);
			}
			mMediaPlayer.start();
		}
	}
	
	
	public <T>void playMusic(T path,boolean music, boolean vibrate,
			boolean slowLoud, final OnCompletionListener mOnCompletionListener) {
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setAudioStreamType(AudioManager.STREAM_ALARM);
			MyLogger.e("aaaa", ""+path);
			if(path!=null&&path instanceof String){
				mp.setDataSource((String) path);
			}else{
				AssetFileDescriptor afd = c.getResources().openRawResourceFd(
						(Integer) path);
				mp.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
				
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		playMusic(mp, music, vibrate, slowLoud, mOnCompletionListener);
		
	
	}
	
	public <T>void playMusic(MediaPlayer mp,boolean music, boolean vibrate,
			boolean slowLoud, final OnCompletionListener mOnCompletionListener) {
		if (music) {
			try {
				mMediaPlayer.release();
				mMediaPlayer =mp;
				mMediaPlayer.prepare();
				
				//mMediaPlayer.setLooping(true);
				//mMediaPlayer.seto
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						mMediaPlayer.start();
						if(mOnCompletionListener!=null)
						mOnCompletionListener.onCompletion(null);
					}
				});

			} catch (Exception e) {
				MyLogger.e("alarm", e.toString());
			}
			if(slowLoud){
			mMediaPlayer.setVolume(0.05f, 0.05f);
			dropUpAnim();
			}else{
				
				try {
					if(downAnimator!=null&&downAnimator.isRunning()){
						downAnimator.cancel();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mMediaPlayer.setVolume(MAX_VOL, MAX_VOL);
			}
			mMediaPlayer.start();
		}
		if (vibrate) {
			mVibrator.vibrate(sVibratePattern, 0);
		}
	}
//	public void playMusic(int res, boolean music, boolean vibrate,
//			boolean slowLoud, final OnCompletionListener mOnCompletionListener) {
//		// Stop();
//
//		if (vibrate) {
//			mVibrator.vibrate(sVibratePattern, 0);
//		}
//		if (music) {
//			try {
//				mMediaPlayer.release();
//				mMediaPlayer = new MediaPlayer();
//				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//				
//				AssetFileDescriptor afd = c.getResources().openRawResourceFd(
//						res);
//				mMediaPlayer.setDataSource(afd.getFileDescriptor(),
//						afd.getStartOffset(), afd.getLength());
//				afd.close();
//				
//				mMediaPlayer.prepare();
//				
//				//mMediaPlayer.setLooping(true);
//				//mMediaPlayer.seto
//				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//					
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mMediaPlayer.start();
//						mOnCompletionListener.onCompletion(null);
//					}
//				});
//
//			} catch (Exception e) {
//				MyLogger.e("alarm", e.toString());
//			}
//			if(slowLoud){
//			mMediaPlayer.setVolume(0.05f, 0.05f);
//			dropUpAnim();
//			}else{
//				
//				try {
//					if(downAnimator!=null&&downAnimator.isRunning()){
//						downAnimator.cancel();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				mMediaPlayer.setVolume(MAX_VOL, MAX_VOL);
//			}
//			mMediaPlayer.start();
//		}
//
//		// }catch (IOException e){}
//	}

public void setVideoView(VideoView videoView,String path,final boolean music, final boolean vibrate,
		final boolean slowLoud){
	
	videoView.setVideoPath(path);
	videoView.setOnPreparedListener(new OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			playMusic(mp,music,vibrate,slowLoud,null);
			mp.setLooping(true);  
			/*mMediaPlayer = mp;
             mp.setLooping(true);  
             if(music){
             mp.setAudioStreamType(AudioManager.STREAM_ALARM);
             if(slowLoud)
            	 dropUpAnim();
             }
             else{
            	 mp.setVolume(0, 0);
             }
             
             mp.start();  */
		}
	});
	
	
	
}
	ValueAnimator downAnimator;

	void dropUpAnim() {
		if(downAnimator!=null&&downAnimator.isRunning()){
			downAnimator.cancel();
		}
		downAnimator = ObjectAnimator.ofFloat(new animWrapper(), "vol", 0.05f,
				MAX_VOL);
		downAnimator.setDuration(15000);
		downAnimator.setInterpolator(new AccelerateInterpolator(5));
		downAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
					
				try {
					if (mMediaPlayer != null ||mMediaPlayer.isPlaying()) {
						mMediaPlayer.setVolume(
								(Float) animation.getAnimatedValue(),
								(Float) animation.getAnimatedValue());
					}
				} catch (Exception e) {
					e.printStackTrace(); 
					downAnimator.cancel();
					
				}
			}
		});
		downAnimator.start();
	}
}



class animWrapper {
	float vol;
}

/* 过滤文件类型 */
class MusicFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		// 这里还可以设置其他格式的音乐文件
		return (name.endsWith(".mp3"));
	}
}
