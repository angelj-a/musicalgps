package compmovil.themebylocation.models;

import java.util.Map;

import android.annotation.SuppressLint;

import compmovil.themebylocation.dbeditor.DBAdapter;

public class ThemePerRegionManager {
	
	private Map<Integer, String> mMap_RegionId_ThemeName;
	private DBAdapter mRegionsThemesDB; 

	@SuppressLint("UseSparseArrays")
	public ThemePerRegionManager(DBAdapter dbAdapter) {
		mRegionsThemesDB = dbAdapter;
	}
	
	public void initialize(){
		mMap_RegionId_ThemeName = mRegionsThemesDB.getThemesPerRegion();
	}
	
	public String getTheme(Integer regionid){
		return mMap_RegionId_ThemeName.get(regionid);		
	}
	
	
	
}
