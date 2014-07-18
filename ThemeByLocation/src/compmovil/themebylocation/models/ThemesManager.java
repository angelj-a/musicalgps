package compmovil.themebylocation.models;

import java.util.List;
import java.util.Map;

public class ThemesManager {
	
	//private Map<Integer, String> mMap_Id;
	String[] mThemesNames= new String[]{"region105", "region112", "region120"};	
	
	//TODO: choose a better name
	public ThemesManager() {
		
	}
	
	public String[] getThemesNames(){
		return mThemesNames;
	}

}
