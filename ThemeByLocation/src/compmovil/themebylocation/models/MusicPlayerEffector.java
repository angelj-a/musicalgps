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
	private boolean mIsThreadRunning;
	

	private enum States { NOTREADY, PREPARED, PLAYING, STOPPED};
	private States mState;
	
	private Runnable mRunnablePlaySong = new Runnable()
	{
    	@Override
    	public void run() {		
    		if (mPlayer != null) {
    			if (mState == States.STOPPED){
    				try {
						mPlayer.prepare();
					}
    				catch (IllegalStateException e) {}
    				catch (IOException e) {}
    				mState = States.PREPARED;
    			}
				if (mPlayer.isPlaying()) {					
					//TODO: to be able to choose a song
					mPlayer.seekTo(0);
					mState = States.PREPARED;
				}
				mPlayer.start();
				mState = States.PLAYING;
			}		
		}
	};
	
	private Runnable mRunnableStopSong = new Runnable()
	{
    	@Override
    	public void run() {
    		if (mPlayer != null) {
    			mPlayer.stop();
    			mState = States.STOPPED;
    		}
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
			
		

	public MusicPlayerEffector(Context ctx) {
		Log.i("THEMELOCATION","Creado MusicPlayerEffector");
		mContext = ctx;
		mIsThreadRunning = false;
		mState = States.NOTREADY;
	}

	@Override
	public void initialize() throws Exception {
		mPlayer = MediaPlayer.create(mContext, R.raw.region105);
		if (mPlayer != null)
			mPlayer.setLooping(true);
		else
			throw new Exception("Error de inicialización: no se pudo instanciar MediaPlayer");

		mPlayerThread = new HandlerThread("[MusicPlayerEffector] MusicPlayer thread");
		mPlayerThread.start();
		mPlayerHandler = new Handler(mPlayerThread.getLooper());
		mIsThreadRunning = true;		
		
		mState = States.PREPARED;
	}

	@Override
	public void onEnterRegion(Region region){
		Log.i("THEMELOCATION","onEnterRegion MusicPlayerEffector");
		mPlayerHandler.post(mRunnablePlaySong);
		assert(mState == States.PLAYING);
	}

	@Override
	public void onExitRegion() {
		Log.i("THEMELOCATION","onExitRegion MusicPlayerEffector");
		mPlayerHandler.post(mRunnableStopSong);
		assert(mState == States.STOPPED);
	}

	@Override
	public void stopEffector() {
		Log.i("THEMELOCATION","stop MusicPlayerEffector");
		if (mIsThreadRunning) {
			if (mState != States.STOPPED)
				mPlayerHandler.post(mRunnableStopSong);
			mPlayerHandler.post(mRunnableRelease);
			//mPlayerThread.getLooper().quitSafely();
			mIsThreadRunning = false;
			mState = States.NOTREADY;
		}
	}
	

}
