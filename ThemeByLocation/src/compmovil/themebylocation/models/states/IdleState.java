package compmovil.themebylocation.models.states;

import compmovil.themebylocation.models.Region;
import compmovil.themebylocation.models.ThreadMusicPlayer;

public class IdleState implements PlayerState {
	
	private ThreadMusicPlayer mThreadedMusicPlayer;
	
	public IdleState(ThreadMusicPlayer threadMP){
		mThreadedMusicPlayer = threadMP;
	}
	

	@Override
	public PlayerState initialize() throws Exception {
		mThreadedMusicPlayer.run();
		return new OutsideRegionState(mThreadedMusicPlayer);
	}

	@Override
	public PlayerState play(Region region) {
		return this;
	}

	@Override
	public PlayerState stop() {
		return this;
	}

	@Override
	public PlayerState release() {
		return this;
	}

}
