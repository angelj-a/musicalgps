package compmovil.themebylocation.models;

import compmovil.themebylocation.models.strategies.SensingStrategy;

public class RegionSensor {
	
	/***********************************************************
	 * 
	 * RegionSensor: attributes
	 * 
	 ************************************************************/	

	private SensingStrategy mSensingStrategy;
	private boolean mIsStopped;
    
    
	
	/***********************************************************
	 * 
	 * RegionSensor: methods
	 * 
	 ***********************************************************/
    
	public RegionSensor(SensingStrategy strategy) {
		if (strategy == null)
			throw new NullPointerException("El parámetro no puede ser null");
		
		mSensingStrategy = strategy;
		mIsStopped = true;
	}
	
	
	public void initialize() throws Exception {
		mSensingStrategy.initialize();
	}
	
	public void startSensing() {
		mSensingStrategy.startSensing();
		mIsStopped = false;
	}
	
	
	public void stopSensing() {
		mSensingStrategy.stopSensing();
		mIsStopped = true;
	}
	
	// returns true if the strategy was set successfully
	public boolean setSensingStrategy(SensingStrategy strategy) {
		if (mIsStopped) {
			if (strategy == null)
				throw new NullPointerException("El parámetro no puede ser null");
			mSensingStrategy = strategy;
			return true;
		}
		else
			return false;
	}
}
