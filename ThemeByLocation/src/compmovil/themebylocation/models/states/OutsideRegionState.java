package compmovil.themebylocation.models.states;

import compmovil.themebylocation.models.Region;
import compmovil.themebylocation.models.ThreadMusicPlayer;

public class OutsideRegionState implements PlayerState {

	private ThreadMusicPlayer mThreadedMusicPlayer;
	private InsideRegionState mStateInsideReg;

	
	public OutsideRegionState(ThreadMusicPlayer threadMP){
		mThreadedMusicPlayer = threadMP;
		mStateInsideReg = new InsideRegionState(mThreadedMusicPlayer, this);
	}	

	@Override
	public PlayerState initialize() {
		return this;
	}
	
	@Override
	public PlayerState play(Region region) {
		mThreadedMusicPlayer.play(region);
		return mStateInsideReg;
	}

	@Override
	public PlayerState stop() {
		return this;
	}

	@Override
	public PlayerState release() {
		mThreadedMusicPlayer.release();
		return new EndedState();
	}

}
