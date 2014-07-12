package compmovil.themebylocation.models;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import compmovil.themebylocation.ControllerService;

public class RegionSensor {
	
	/***********************************************************
	 * 
	 * RegionSensor: attributes
	 * 
	 ***********************************************************/
	
	private LocationManager mLocationManager;	

	private RegionsManager mRegionsManager;
	private Region mCurrentRegion;
	
	private static Region NO_REGION = null; 
	
	
    private HandlerThread mHandlerThread;
    private Handler mHandler;
	private Messenger mObserverMessenger;

	
	
	
	
	/***********************************************************
	 * 
	 * RegionSensor: classes
	 * 
	 ***********************************************************/
	private LocationListener mLocationListener = new LocationListener(){
    	public void onLocationChanged(Location location) {
    		if (location != null) {
    			//Check if you just entered in a region
    			if (mCurrentRegion == NO_REGION) {
    				mCurrentRegion = mRegionsManager.insideWhichRegion(location);
    				if (mCurrentRegion != NO_REGION) {
    					notifyEnteredARegion(mCurrentRegion);
    				}
    			}
    			 //Check if you're still inside the region
    			else if (mCurrentRegion.isInsideRegion(location)){
        			Log.i("THEMELOCATION", location.getLatitude() + " " + location.getLongitude() + " - Estás dentro de la region");
    			}
    			else {
    				Log.i("THEMELOCATION", location.getLatitude() + " " + location.getLongitude() + " - Saliste de la region");
    				notifyExitedARegion();
        			mCurrentRegion = null;
    			}
    		}
    	}
    	
    	public void onProviderDisabled(String provider) {}
    	public void onProviderEnabled(String provider) 	{} 
    	public void onStatusChanged(String provider, int status, Bundle extras){}
    };	
    
    
	
	/***********************************************************
	 * 
	 * RegionSensor: methods
	 * 
	 ***********************************************************/
	
	public RegionSensor(Messenger messenger) {
		mObserverMessenger = messenger;
		mRegionsManager = new RegionsManager();
	}
	
	public void initialize(Context ctx) throws Exception{
		mLocationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		
		if (!(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)))
			throw new Exception("No hay disponible GPS");
		
		mHandlerThread = new HandlerThread("GPS Thread");
	}
	
	public void startSensing() {
		mHandlerThread.start();
	    mHandler = new Handler(mHandlerThread.getLooper());
	    mHandler.post(
	            new Runnable() {
	                    @Override
	                    public void run() {
	                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, mLocationListener);
	                    }
	            });	
	}
	
	
	public void stopSensing() {
		mLocationManager.removeUpdates(mLocationListener);
		if (mHandlerThread != null)
			mHandlerThread.getLooper().quit();
	}

	
	/***********************************************************
	 * 
	 * RegionSensor: auxiliary functions
	 * 
	 ***********************************************************/

	private void notifyExitedARegion() {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_EXITED_REGION);
		try {
			mObserverMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
	}
	
	private void notifyEnteredARegion(Region region) {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_ENTERED_REGION);
		msg.obj = region;
		try {
			mObserverMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}

}
