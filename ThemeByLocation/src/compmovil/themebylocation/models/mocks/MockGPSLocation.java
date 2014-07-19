package compmovil.themebylocation.models.mocks;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class MockGPSLocation implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
    private static final String PROVIDER = "gps";
    private static final float ACCURACY = 3.0f;

	private Context mContext;
	private LocationClientCallbackInterface mLocClientCallback;
	private LocationClient mLocationClient; //TODO: LocationClient it's deprecated
	
	public MockGPSLocation(Context ctx){
		mContext = ctx;
		mLocationClient = new LocationClient(mContext, this, this);
	}
	
	public void enableMockMode(boolean enabled){
		mLocationClient.setMockMode(enabled);
	}
	
	public void setMockLocation(float lat, float lng){
		mLocationClient.setMockLocation(createLocation(lat, lng,ACCURACY));
	}
	
	public void connect(LocationClientCallbackInterface callback){
		mLocationClient.connect();
		mLocClientCallback = callback;
	}
	
	public void disconnect(){
		mLocationClient.disconnect();
	}
	

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		mLocClientCallback.onConnectionFailed(result);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocClientCallback.onConnected(connectionHint);
	}

	@Override
	public void onDisconnected() {
		mLocClientCallback.onDisconnected();
	}
	
    
	
	private Location createLocation(double lat, double lng, float accuracy) {
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        newLocation.setTime(System.currentTimeMillis());
        return newLocation;
    }

}
