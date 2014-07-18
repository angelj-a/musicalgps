package compmovil.themebylocation.dbeditor;

public class ThemeModel {

	String mName;
	String mUri;
	public ThemeModel(String name, String uri){
		mName = name;
		mUri = uri;
	}
	
	public String getName(){
		return mName;
	}
	
	public String getUri(){
		return mUri;
	}
}
