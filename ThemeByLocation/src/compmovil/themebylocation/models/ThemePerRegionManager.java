package compmovil.themebylocation.models;

import java.util.Map;

import android.annotation.SuppressLint;

import compmovil.themebylocation.dbeditor.DBAdapter;

public class ThemePerRegionManager {
	
	private Map<Integer, String> mMap_RegionId_ThemeName;
	private DBAdapter mRegionsThemesDB;
	
	//TODO: change HashMap<Integer,String> to HashMap<Integer,Theme> 

	@SuppressLint("UseSparseArrays")
	public ThemePerRegionManager(DBAdapter dbAdapter) {
		mRegionsThemesDB = dbAdapter;
		
		//MOCK
		//mMap_RegionId_ThemeName.put(105, "android.resource://compmovil.themebylocation/raw/region105");
		//mMap_RegionId_ThemeName.put(112, "android.resource://compmovil.themebylocation/raw/region112");
	}
	
	public void initialize(){
		mMap_RegionId_ThemeName = mRegionsThemesDB.getThemesPerRegion();
	}
	
	
//	public void newAssociation(Integer regionid, String theme) throws Exception{
//		if (mMap_RegionId_ThemeName.containsKey(regionid))
//			throw new Exception("La región ya está asociada a otro tema");
//		else
//			changeAssociation(regionid, theme);
//	}
//	
//		public void changeAssociation(Integer regionid, String theme){
//		mMap_RegionId_ThemeName.put(regionid, theme);
//	}
	
	public String getTheme(Integer regionid){
		return mMap_RegionId_ThemeName.get(regionid);		
	}
	
	
	
}
