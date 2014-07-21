package compmovil.themebylocation.models;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

import compmovil.themebylocation.dbeditor.DBAdapter;
import compmovil.themebylocation.dbeditor.RegionModel;

public class RegionsManager {
	    
	private List<Region> mRegionsContainer = new ArrayList<Region>();	
	private DBAdapter mRegionsThemesDB;
	private static Region NO_REGION = null;		

	public RegionsManager(DBAdapter dbAdapter){
		mRegionsThemesDB = dbAdapter;
	}
	
	public void initialize(){
		List<RegionModel> regmodels = mRegionsThemesDB.getAllRegions();
		for (RegionModel rm : regmodels) {
			mRegionsContainer.add(rm.toRectangularRegion()); 
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
