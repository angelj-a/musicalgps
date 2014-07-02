package compmovil.themebylocation.models;

public interface Effector {
	
	public void initialize() throws Exception;
	
	public void onEnterRegion(Region region);
	
	public void onExitRegion();
	
	public void stopEffector();

}
