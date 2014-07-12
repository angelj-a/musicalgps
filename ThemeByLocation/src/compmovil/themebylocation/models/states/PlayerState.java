package compmovil.themebylocation.models.states;

import compmovil.themebylocation.models.Region;

public interface PlayerState {

	//They all return the next state
	public PlayerState initialize() throws Exception;
	
	public PlayerState play(Region region);
	
	public PlayerState stop();
	
	public PlayerState release();
	
	
}
