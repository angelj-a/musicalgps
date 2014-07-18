package compmovil.themebylocation.dbeditor;

import android.location.Location;
import android.location.LocationManager;
import compmovil.themebylocation.models.RectangularRegion;

public class RegionModel {
	
	private RectangularRegion mRectReg;
	private String mName;
	private Integer mThemeId;
	
    private Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(LocationManager.GPS_PROVIDER); //TODO: replace
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
	
	public RegionModel(String name, float lat0, float long0, float lat1, float long1, Integer themeid){
		mName = name;
		try {
			mRectReg = new RectangularRegion(createLocation(lat0, long0, 0),
											 createLocation(lat1, long1, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mThemeId = themeid;
	}
	
	public String getName(){
		return mName;
	}
	
	public Integer getThemeId(){
		return mThemeId;
	}
	
	public RectangularRegion toRectangularRegion(){
		return mRectReg;		
	}
}
