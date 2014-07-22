package compmovil.themebylocation.models.strategies;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import compmovil.themebylocation.models.Notifier;
import compmovil.themebylocation.models.Region;
import compmovil.themebylocation.models.RegionsManager;

public class GPSLocationListenerSensingStrategy implements SensingStrategy {
	
	private LocationManager mLocationManager;
	
	private Region mCurrentRegion;
	private static Region NO_REGION = null;
	private RegionsManager mRegionsManager;
	
    private HandlerThread mHandlerThread;
    private Handler mHandler;
	private Context mContext;
	private Notifier mNotifier;
	private boolean mIsStarted;
	
	private LocationListener mLocationListener = new LocationListener(){
    	@Override
		public void onLocationChanged(Location location) {
    		if (location != null) {
    			//Check if you just entered in a region
    			if (mCurrentRegion == NO_REGION) {
    				mCurrentRegion = mRegionsManager.insideWhichRegion(location);
    				if (mCurrentRegion != NO_REGION) {
    					mNotifier.notifyEnteredARegion(mCurrentRegion);
    				}
    			}
    			 //Check if you're still inside the region
    			else if (mCurrentRegion.isInsideRegion(location)){
        			Log.i("THEMELOCATION", location.getLatitude() + " " + location.getLongitude() + " - Estás dentro de la region");
    			}
    			else {
    				Log.i("THEMELOCATION", location.getLatitude() + " " + location.getLongitude() + " - Saliste de la region");
    				mNotifier.notifyExitedARegion();
        			mCurrentRegion = null;
    			}
    		}
    	}
    	
    	@Override
		public void onProviderDisabled(String provider) {}
    	@Override
		public void onProviderEnabled(String provider) 	{} 
    	@Override
		public void onStatusChanged(String provider, int status, Bundle extras){}
    };

	private Runnable mRunnableRequestLocUpdates = new Runnable() {
        @Override
        public void run() {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, mLocationListener);
        }
	};

	

	public GPSLocationListenerSensingStrategy(Context ctx, Notifier notifier, RegionsManager regmanager){
		mRegionsManager = regmanager;
		mContext = ctx;
		mNotifier = notifier;
	}
	

	@Override
	public void initialize() throws Exception {
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		
		if (!(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)))
			throw new Exception("No hay disponible GPS");
		
		mHandlerThread = new HandlerThread("GPS Thread");
		mRegionsManager.initialize();
		mIsStarted = false;
		
	}

	@Override
	public void startSensing() {
		mHandlerThread.start();
	    mHandler = new Handler(mHandlerThread.getLooper());
	    mHandler.post(mRunnableRequestLocUpdates );
	    mIsStarted = true;
	}

	@Override
	public void stopSensing() {
		mLocationManager.removeUpdates(mLocationListener);
		if (mHandlerThread != null && mIsStarted)
			mHandlerThread.getLooper().quit();
		mIsStarted = false;
	}

	
	
	
}
