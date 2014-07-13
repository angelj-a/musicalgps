package compmovil.themebylocation.models;

import android.location.Location;
import android.location.LocationManager;

public class RegionsManager {

	
	/*----------*
	 * 
	 * MOCK REGIONS
	 * 
	 *----------*/
    private static final double LAT0 = 37;
    private static final double LNG0 = -122;
    private static final double LAT1 = 38;
    private static final double LNG1 = -122.5;
    private static final float ACCURACY = 0.0f;
    
	private static Region NO_REGION = null; 


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
    
    Location testLocation0C = createLocation(LAT0+20f, LNG0, ACCURACY);
    Location testLocation1C = createLocation(LAT1+20f, LNG1, ACCURACY);

    
	private Region[] mRegionsContainer = new Region[3];
		

	public RegionsManager(){
		//MOCK
		//TODO: get them from a database 
		try {
			mRegionsContainer[0] = new RectangularRegion(testLocation0, testLocation1);
			mRegionsContainer[1] = new RectangularRegion(testLocation0B, testLocation1B);
			mRegionsContainer[2] = new RectangularRegion(testLocation0C, testLocation1C);
			((RectangularRegion)mRegionsContainer[0]).setId(105);
			((RectangularRegion)mRegionsContainer[1]).setId(112);
			((RectangularRegion)mRegionsContainer[2]).setId(120);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Region insideWhichRegion(Location location){
		for (Region r : mRegionsContainer) {
			if (r.isInsideRegion(location))	
				return r; 
		}
		return NO_REGION;
	}
	
}
