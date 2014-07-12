package compmovil.themebylocation.models;

import android.content.Context;
import android.util.Log;

import compmovil.themebylocation.models.states.IdleState;
import compmovil.themebylocation.models.states.PlayerState;

public class MusicPlayerEffector implements Effector {
	
	private Context mContext;

	private ThemePerRegionManager mThemePerRegionManager;
	private ThreadMusicPlayer mThreadedMusicPlayer;

	private PlayerState mPlayerState;
		
	
		

	public MusicPlayerEffector(Context ctx, ThemePerRegionManager tprmanager) {
		mThemePerRegionManager = tprmanager;
		mContext = ctx;
		mThreadedMusicPlayer = new ThreadMusicPlayer(mThemePerRegionManager, mContext);
		mPlayerState = new IdleState(mThreadedMusicPlayer);
		Log.i("THEMELOCATION","Creado MusicPlayerEffector");
	}

	@Override
	public void initialize() throws Exception {
		Log.i("THEMELOCATION","inicializando MusicPlayerEffector");
		mPlayerState = mPlayerState.initialize();
		Log.i("THEMELOCATION","inicializado MusicPlayerEffector (OK)");
	}

	@Override
	public void onEnterRegion(Region region){
		Log.i("THEMELOCATION","onEnterRegion MusicPlayerEffector");
		mPlayerState = mPlayerState.play(region);
	}

	@Override
	public void onExitRegion() {
		Log.i("THEMELOCATION","onExitRegion MusicPlayerEffector");
		mPlayerState = mPlayerState.stop();
	}

	@Override
	public void stopEffector() {
		Log.i("THEMELOCATION","stop MusicPlayerEffector");
		mPlayerState = mPlayerState.release();
	}

	
}
