package compmovil.themebylocation.models;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

import compmovil.themebylocation.dbeditor.DBAdapter;
import compmovil.themebylocation.dbeditor.RegionModel;
import compmovil.themebylocation.utils.exceptions.NoRegionsException;

public class RegionsManager {
	    
	private List<Region> mRegionsContainer = new ArrayList<Region>();	
	private DBAdapter mRegionsThemesDB;
	private static Region NO_REGION = null;		

	public RegionsManager(DBAdapter dbAdapter){
		mRegionsThemesDB = dbAdapter;
	}
	
	public void initialize() throws Exception {
		List<RegionModel> regmodels = mRegionsThemesDB.getAllRegions();
		for (RegionModel rm : regmodels) {
			mRegionsContainer.add(rm.toRectangularRegion()); 
		}
		
		if (mRegionsContainer.isEmpty())
			throw new NoRegionsException();
	}
	
	public Region insideWhichRegion(Location location){
		for (Region r : mRegionsContainer) {
			if (r.isInsideRegion(location))	
				return r; 
		}
		return NO_REGION;
	}
	
}
