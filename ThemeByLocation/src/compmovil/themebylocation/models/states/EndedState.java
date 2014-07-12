package compmovil.themebylocation.models.states;

import compmovil.themebylocation.models.Region;

public class EndedState implements PlayerState {
	
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
		return this;
	}

	@Override
	public PlayerState release() {
		return this;
	}

}
