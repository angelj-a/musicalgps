package compmovil.themebylocation.models;

import compmovil.themebylocation.ControllerService;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class RegionSensor {
	
	/***********************************************************
	 * 
	 * RegionSensor: attributes
	 * 
	 ***********************************************************/
	
	private LocationManager mLocationManager;	

	
	//MOCK LOCATIONS:
    private static final String PROVIDER = "flp";
    private static final double LAT0 = 37;
    private static final double LNG0 = -122;
    private static final double LAT1 = 38;
    private static final double LNG1 = -122.5;
    private static final float ACCURACY = 0.0f;
    /*
     * From input arguments, create a single Location with provider set to
     * "flp"
     */
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(LocationManager.GPS_PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }

    // Example of creating a new Location from test data
    Location testLocation0 = createLocation(LAT0, LNG0, ACCURACY);
    Location testLocation1 = createLocation(LAT1, LNG1, ACCURACY);
    
    Location testLocation0B = createLocation(LAT0+10f, LNG0, ACCURACY);
    Location testLocation1B = createLocation(LAT1+10f, LNG1, ACCURACY);

	
	//HARDCODED REGION:
	private Region mCurrentRegion;
	private Region[] mRegionsContainer = new Region[2];
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
    				mCurrentRegion = insideRegion(location);
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
		
		//TODO:REMOVE LATER. Replace with Regions Manager or something like that
		try {
			mRegionsContainer[0] = new RectangularRegion(testLocation0, testLocation1);
			mRegionsContainer[1] = new RectangularRegion(testLocation0B, testLocation1B);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initialize(Context ctx){
		mLocationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);	
		mHandlerThread = new HandlerThread("GPS Thread");
		
//		//MOCK
//		mLocationManager.addTestProvider (LocationManager.GPS_PROVIDER,
//                "requiresNetwork" == "",
//                "requiresSatellite" == "",
//                "requiresCell" == "",
//                "hasMonetaryCost" == "",
//                "supportsAltitude" == "",
//                "supportsSpeed" == "",
//                "supportsBearing" == "",
//                 android.location.Criteria.POWER_LOW,
//                 android.location.Criteria.ACCURACY_FINE);      
//		
//		mLocationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
//
//		mLocationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER,
//		                             LocationProvider.AVAILABLE,
//		                             null,System.currentTimeMillis());    
//		  
//		mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, testLocationCurrent);
//		
//
//		for (x=1; x<11; x++)
//	    mHandler.postDelayed(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                            mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,
//	                            		createLocation(LAT0+i*x, LNG0, ACCURACY));
//	                    }
//	            }, 2000*x);

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
	
	
	private Region insideRegion(Location location) {
		for (Region r : mRegionsContainer) {
			if (r.isInsideRegion(location))	
				return r; 
		}
		return NO_REGION;
	}

}
