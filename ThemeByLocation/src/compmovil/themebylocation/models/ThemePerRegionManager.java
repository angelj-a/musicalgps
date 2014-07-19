package compmovil.themebylocation.models;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

public class ThemePerRegionManager {
	
	private Map<Integer, String> mMap_RegionId_ThemeName;
	
	//TODO: change HashMap<Integer,String> to HashMap<Region,String> 

	@SuppressLint("UseSparseArrays")
	public ThemePerRegionManager() {
		mMap_RegionId_ThemeName = new HashMap<Integer,String>();
		
		//MOCK
		//mMap_RegionId_ThemeName.put(105, "android.resource://compmovil.themebylocation/raw/region105");
		//mMap_RegionId_ThemeName.put(112, "android.resource://compmovil.themebylocation/raw/region112");
	}
	
	
	public void newAssociation(Integer regionid, String theme) throws Exception{
		if (mMap_RegionId_ThemeName.containsKey(regionid))
			throw new Exception("La región ya está asociada a otro tema");
		else
			changeAssociation(regionid, theme);
	}
	
	public void changeAssociation(Integer regionid, String theme){
		mMap_RegionId_ThemeName.put(regionid, theme);
	}
	
	public String getTheme(Integer regionid){
		return mMap_RegionId_ThemeName.get(regionid);		
	}
	
	
	
}
