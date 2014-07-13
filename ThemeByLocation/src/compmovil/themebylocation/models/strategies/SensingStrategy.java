package compmovil.themebylocation.models.strategies;

public interface SensingStrategy {
	
	public void initialize() throws Exception;
	
	public void startSensing();
	
	public void stopSensing();

}
