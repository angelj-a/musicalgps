package compmovil.themebylocation.dbeditor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RegionsDbHelper extends SQLiteOpenHelper {
	
	private static int version = 1;
	private final static String sDATABASE_NAME = "RegionsDb" ;
	private static CursorFactory factory = null;
	
	public final String cTABLE_THEMES = "Themes";
	public final String cTABLE_REGIONS = "Regions";
	
	public final String cKEY_THEMES_ID = "themeid";
	public final String cKEY_THEME_NAME = "theme_name";
	public final String cKEY_THEME_URI = "theme_uri";
	
	public final String cKEY_REGION_ID = "regionid";
	public final String cKEY_REGION_NAME = "reg_name";
	public final String cKEY_REGION_LAT_S = "reg_latitude_s";
	public final String cKEY_REGION_LAT_N = "reg_latitude_n";
	public final String cKEY_REGION_LONG_W = "reg_longitude_w";
	public final String cKEY_REGION_LONG_E = "reg_longitude_e";
	public final String cKEY_REGION_THEMEID = "theme_id";
	
	public final String[] cALL_KEYS_REGION = new String[]{ cKEY_REGION_ID, cKEY_REGION_NAME, cKEY_REGION_LAT_S, cKEY_REGION_LAT_N,
											cKEY_REGION_LONG_W, cKEY_REGION_LONG_E, cKEY_REGION_THEMEID };
	public final String[] cALL_KEYS_THEME = new String[]{ cKEY_THEMES_ID, cKEY_THEME_NAME, cKEY_THEME_URI};
	
	
	

	public RegionsDbHelper(Context context) {
		super(context, sDATABASE_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		   db.execSQL( "CREATE TABLE " + cTABLE_THEMES + "(" +  
				   		cKEY_THEMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				   		cKEY_THEME_NAME + " TEXT NOT NULL, " +
				   		cKEY_THEME_URI + " TEXT NOT NULL) ");

		   
		   db.execSQL( "CREATE TABLE " + cTABLE_REGIONS + "(" +
		          cKEY_REGION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		          cKEY_REGION_NAME + " TEXT NOT NULL, " +
		          cKEY_REGION_LAT_S + " REAL NOT NULL, " +
		          cKEY_REGION_LAT_N + " REAL NOT NULL, " +
		          cKEY_REGION_LONG_W + " REAL NOT NULL, " +
		          cKEY_REGION_LONG_E + " REAL NOT NULL, " +
		          cKEY_REGION_THEMEID + " INTEGER," +
		          "FOREIGN KEY(" + cKEY_REGION_THEMEID + ") REFERENCES THEMES(" + cKEY_THEMES_ID + "))" );
		 	
		   //TODO: remove
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme105','android.resource://compmovil.themebylocation/raw/region105')");
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme112','android.resource://compmovil.themebylocation/raw/region112')");
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme120','android.resource://compmovil.themebylocation/raw/region120')");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
