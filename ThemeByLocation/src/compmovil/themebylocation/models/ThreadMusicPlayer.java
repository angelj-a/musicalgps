package compmovil.themebylocation.models;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;

import compmovil.themebylocation.R;

public class ThreadMusicPlayer {
	
	private ThemePerRegionManager mThemePerRegionManager;
	private Context mContext;
	private MediaPlayer mPlayer;
	private HandlerThread mPlayerThread;
	private Handler mPlayerHandler;
	
	private Integer mCurrentSong;
	
	private Runnable mRunnablePrepare = new Runnable()
	{
		@Override
		public void run() {
			try {
				mPlayer.prepare();
			}
			catch (IllegalStateException e) {}
			catch (IOException e) {}
		}			
	};
	
	private Runnable mRunnablePlaySong = new Runnable()
	{
    	@Override
    	public void run() {		
    		if (mPlayer != null) {
				if (mPlayer.isPlaying()) {					
					//TODO: to be able to choose a song
					mPlayer.seekTo(0);
				}
				mPlayer.start();
			}		
		}
	};
	
	private Runnable mRunnableStopSong = new Runnable()
	{
    	@Override
    	public void run() {
   			mPlayer.stop();
    	}
	};
	
	private Runnable mRunnableRelease = new Runnable()
	{
		@Override
		public void run() {
			mPlayer.release();
			mPlayerHandler.getLooper().quit();
		}
	};
	
	
	
	public ThreadMusicPlayer(ThemePerRegionManager tprmanager, Context ctx){
		mThemePerRegionManager = tprmanager;
		mContext = ctx;
	}
	
	public void run() throws Exception{
		mPlayer = MediaPlayer.create(mContext, R.raw.region105);
		if (mPlayer != null)
			mPlayer.setLooping(true);
		else
			throw new Exception("Error de inicialización: no se pudo instanciar MediaPlayer");
		
		mPlayerThread = new HandlerThread("[MusicPlayerEffector] MusicPlayer thread");
		mPlayerThread.start();
		mPlayerHandler = new Handler(mPlayerThread.getLooper());
	}
	
	public void play(Region r){
		//TODO: use region r
		mCurrentSong = mThemePerRegionManager.getTheme(((RectangularRegion)r).getId());
		mPlayerHandler.post(mRunnablePlaySong);		
	}
		
	public void stop(){
		mPlayerHandler.post(mRunnableStopSong);
		mPlayerHandler.post(mRunnablePrepare);
	}
	
	public void release(){
		mPlayerHandler.post(mRunnableRelease);
	}
	
	
	
}
