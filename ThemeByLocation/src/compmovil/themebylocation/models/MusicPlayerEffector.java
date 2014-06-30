package compmovil.themebylocation.models;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import compmovil.themebylocation.R;

public class MusicPlayerEffector implements Effector {
	
	private Context mContext;
	private MediaPlayer mPlayer;
	private HandlerThread mPlayerThread;
	private Handler mPlayerHandler;
	private boolean mIsRunning;
	
	private Runnable mRunnablePlaySong = new Runnable()
	{
    	@Override
    	public void run() {		
    		if (mPlayer != null) {
				if (mPlayer.isPlaying()) {					
					//TODO: be able to choose a song
					//mPlayer.stop();
					//try {
					//	mPlayer.prepare();
					//} catch (IllegalStateException e) {
					//	e.printStackTrace();
					//} catch (IOException e) {
					//	e.printStackTrace();
					//}
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
    		if (mPlayer != null)
    			mPlayer.stop();
    	}
	};
			
		

	public MusicPlayerEffector(Context ctx) {
		// TODO Auto-generated constructor stub
		Log.i("THEMELOCATION","Creado MusicPlayerEffector");
		mContext = ctx;
		mIsRunning = false;
	}

	@Override
	public void initialize() {
		mPlayer = MediaPlayer.create(mContext, R.raw.region105);
		if (null != mPlayer)
			mPlayer.setLooping(true);
		mPlayerThread = new HandlerThread("[MusicPlayerEffector] MusicPlayer thread");
	}

	@Override
	public void onEnterRegion(Region region){
		Log.i("THEMELOCATION","onEnterRegion MusicPlayerEffector");
		if (!mIsRunning) {
			mPlayerThread.start();
			mPlayerHandler = new Handler(mPlayerThread.getLooper());
			mIsRunning = true;
		}
		mPlayerHandler.post(mRunnablePlaySong);

	}

	@Override
	public void onExitRegion() {
		Log.i("THEMELOCATION","onExitRegion MusicPlayerEffector");
		mPlayerHandler.post(mRunnableStopSong);
		
	}

	@Override
	public void stop() {
		Log.i("THEMELOCATION","stop MusicPlayerEffector");
		mPlayerHandler.post(mRunnableStopSong);
		mPlayer.release();		
	}
	

}
