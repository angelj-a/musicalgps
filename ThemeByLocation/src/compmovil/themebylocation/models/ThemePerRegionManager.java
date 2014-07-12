package compmovil.themebylocation.models;

import java.util.HashMap;
import java.util.Map;

import compmovil.themebylocation.R;

public class ThemePerRegionManager {
	
	private Map mMap_RegionId_ThemeName;
	
	//TODO: change HashMap<Integer,Integer> to HashMap<Integer,String>
	//TODO: (later) change HashMap<Integer,String> to HashMap<Region,String> 

	public ThemePerRegionManager() {
		mMap_RegionId_ThemeName = new HashMap<Integer,Integer>();
		
		//mMap_RegionId_ThemeName.put(105, R.raw.region105);
		//mMap_RegionId_ThemeName.put(112, R.raw.region112);
	}
	
	
	public void newAssociation(Integer regionid, Integer theme) throws Exception{
		Integer dummy_ = 0;
		if (mMap_RegionId_ThemeName.containsKey(regionid))
			throw new Exception("La región ya está asociada a otro tema");
		else
			changeAssociation(regionid, theme);
	}
	
	public void changeAssociation(Integer regionid, Integer theme){
		Integer dummy_ = 0;
		mMap_RegionId_ThemeName.put(regionid, theme);
	}
	
	public Integer getTheme(Integer regionid){
		return (Integer) mMap_RegionId_ThemeName.get(regionid);		
	}
	
	
	
}
