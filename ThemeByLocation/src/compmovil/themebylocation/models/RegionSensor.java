package compmovil.themebylocation.models;

import compmovil.themebylocation.models.strategies.SensingStrategy;

public class RegionSensor {
	
	/***********************************************************
	 * 
	 * RegionSensor: attributes
	 * 
	 ************************************************************/	

	private SensingStrategy mSensingStrategy;
	private boolean mIsStarted;
    
    
	
	/***********************************************************
	 * 
	 * RegionSensor: methods
	 * 
	 ***********************************************************/
    
	public RegionSensor(SensingStrategy strategy) {
		if (strategy == null)
			throw new NullPointerException("El parámetro no puede ser null");
		
		mSensingStrategy = strategy;
		mIsStarted = false;
	}
	
	
	public void initialize() throws Exception {
		mSensingStrategy.initialize();
	}
	
	public void startSensing() {
		mSensingStrategy.startSensing();
		mIsStarted = true;
	}
	
	
	public void stopSensing() {
		if (mIsStarted)
			mSensingStrategy.stopSensing();
		mIsStarted = false;
	}
	
	// returns true if the strategy was successfully set 
	public boolean setSensingStrategy(SensingStrategy strategy) {
		if (mIsStarted) {
			if (strategy == null)
				throw new NullPointerException("El parámetro no puede ser null");
			mSensingStrategy = strategy;
			return true;
		}
		else
			return false;
	}
}
