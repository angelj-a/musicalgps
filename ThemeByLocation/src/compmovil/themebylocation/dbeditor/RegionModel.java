package compmovil.themebylocation.dbeditor;

import android.location.Location;
import android.location.LocationManager;

import compmovil.themebylocation.models.RectangularRegion;

public class RegionModel {
	
	private RectangularRegion mRectReg;
	private String mName;
	private Integer mThemeId;
	private Integer mId;
	
    private Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(LocationManager.GPS_PROVIDER); //TODO: replace
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
	
	public RegionModel(Integer id, String name, double lat0, double long0, double lat1, double long1, Integer themeid){
		mId = id;
		mName = name;
		try {
			mRectReg = new RectangularRegion(mId, createLocation(lat0, long0, 0),
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
	
	public Integer getId(){
		return mId;
	}
	
	public RectangularRegion toRectangularRegion(){
		return mRectReg;		
	}
}
