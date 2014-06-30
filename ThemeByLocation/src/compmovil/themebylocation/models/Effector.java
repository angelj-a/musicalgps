package compmovil.themebylocation.models;

public interface Effector {
	
	public void initialize();
	
	public void onEnterRegion(Region region);
	
	public void onExitRegion();
	
	public void stop();

}
