package compmovil.themebylocation.models;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;

public class ThreadMusicPlayer {
	
	private ThemePerRegionManager mThemePerRegionManager;
	private Context mContext;
	private MediaPlayer mPlayer;
	private HandlerThread mPlayerThread;
	private Handler mPlayerHandler;
	
	private String mCurrentSong;
	
	private Runnable mRunnablePlaySong = new Runnable()
	{
    	@Override
    	public void run() {		
    		if (mPlayer != null) {
				try {
					if (mPlayer.isPlaying()) {					
						mPlayer.reset();
					}					
					//TODO: change 
					mPlayer.setDataSource(mContext, Uri.parse(mCurrentSong));
			        mPlayer.prepare();
			        mPlayer.start();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
		}
	};
	
	private Runnable mRunnableStopSong = new Runnable()
	{
    	@Override
    	public void run() {
   			mPlayer.stop();
   			mPlayer.reset();
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
		mPlayer = new MediaPlayer();
		if (mPlayer != null)
			mPlayer.setLooping(true);
		else
			throw new Exception("Error de inicialización: no se pudo instanciar MediaPlayer");
		
		mPlayerThread = new HandlerThread("[MusicPlayerEffector] MusicPlayer thread");
		mThemePerRegionManager.initialize();
		mPlayerThread.start();
		mPlayerHandler = new Handler(mPlayerThread.getLooper());
	}
	
	public void play(Region r){
		//TODO: avoid casting r from Region to RectangularRegion
		mCurrentSong = mThemePerRegionManager.getTheme(((RectangularRegion)r).getId());
		mPlayerHandler.post(mRunnablePlaySong);		
	}
		
	public void stop(){
		mPlayerHandler.post(mRunnableStopSong);
	}
	
	public void release(){
		mPlayerHandler.post(mRunnableRelease);
	}
	
	
	
}
