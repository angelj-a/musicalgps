package compmovil.themebylocation.models;

import android.location.Location;
import android.util.Log;

public class RectangularRegion implements Region {
	
	// Max distance between two latitudes(or longitudes): 1km.  In degrees: 0º 0' 33" 
	//assumption: 1sec of latitude (dec(1/3600) in decimal notation)  =aprox= 30mts    
	static double MAX_DISTANCE = 1/3600 * 33;
	
	int mId;
	double mEastMostLongitude, mWestMostLongitude;
	double mNorthMostLatitude, mSouthMostLatitude; 
	
	public RectangularRegion(int id){
		mId = id;
	};
	
	public RectangularRegion(Location corner0, Location corner1) throws Exception{
		double lat0, long0, lat1, long1;
		lat0 = corner0.getLatitude();
		long0 = corner0.getLongitude();
		lat1 = corner1.getLatitude();
		long1 = corner1.getLongitude();
		if (Math.abs(lat0 - lat1) < MAX_DISTANCE || Math.abs(long0 - long1) < MAX_DISTANCE )
			throw new Exception("La región debe ser al menos 1km por al menos 1km");
		
		if (long0 < long1) {
			mWestMostLongitude = long0;
			mEastMostLongitude = long1;
		}
		else {
			mWestMostLongitude = long1;
			mEastMostLongitude = long0;
		}
		
		if (lat0 < lat1) {
			mSouthMostLatitude = lat0;
			mNorthMostLatitude = lat1;
		}
		else {
			mSouthMostLatitude = lat1;
			mNorthMostLatitude = lat0;			
		}
		
	}
	
	public int getId(){
		return mId;
	}
	
	/* (non-Javadoc)
	 * @see compmovil.themebylocation.models.Region#isInsideRegion(android.location.Location)
	 */
	@Override
	public boolean isInsideRegion(Location l) {
		double	latitude = l.getLatitude(),
				longitude = l.getLongitude();
				
		return mWestMostLongitude <= longitude && longitude <= mEastMostLongitude
				&& mNorthMostLatitude >= latitude && latitude >= mSouthMostLatitude;
	}
	
}