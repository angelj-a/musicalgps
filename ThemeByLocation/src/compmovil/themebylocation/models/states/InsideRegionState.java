package compmovil.themebylocation.models.states;

import compmovil.themebylocation.models.Region;
import compmovil.themebylocation.models.ThreadMusicPlayer;

public class InsideRegionState implements PlayerState {

	private ThreadMusicPlayer mThreadedMusicPlayer;
	private OutsideRegionState mStateOutsideReg;
	
	public InsideRegionState(ThreadMusicPlayer threadMP, OutsideRegionState orstate){
		mThreadedMusicPlayer = threadMP; 
		mStateOutsideReg = orstate;
	}
	
	@Override
	public PlayerState initialize() {
		return this;
	}

	@Override
	public PlayerState play(Region region) {
		return this;
	}

	@Override
	public PlayerState stop() {
		mThreadedMusicPlayer.stop();	
		return mStateOutsideReg; 
	}

	@Override
	public PlayerState release() {
		mThreadedMusicPlayer.release();
		return new EndedState();
	}

}
