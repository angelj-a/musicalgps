package compmovil.themebylocation.dbeditor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RegionsDbHelper extends SQLiteOpenHelper {
	
	private static int version = 1;
	private final static String sDATABASE_NAME = "RegionsDb.db" ;
	private static CursorFactory factory = null;
	
	public static final String TABLE_THEMES = "Themes";
	public static final String TABLE_REGIONS = "Regions";
	
	public static final String KEY_THEMES_ID = "themeid";
	public static final String KEY_THEME_NAME = "theme_name";
	public static final String KEY_THEME_URI = "theme_uri";
	
	public static final String KEY_REGION_ID = "regionid";
	public static final String KEY_REGION_NAME = "reg_name";
	public static final String KEY_REGION_LAT_S = "reg_latitude_s";
	public static final String KEY_REGION_LAT_N = "reg_latitude_n";
	public static final String KEY_REGION_LONG_W = "reg_longitude_w";
	public static final String KEY_REGION_LONG_E = "reg_longitude_e";
	public static final String KEY_REGION_THEMEID = "theme_id";
	
	public static final String[] ALL_KEYS_REGION = new String[]{ KEY_REGION_ID, KEY_REGION_NAME, KEY_REGION_LAT_S, KEY_REGION_LAT_N,
											KEY_REGION_LONG_W, KEY_REGION_LONG_E, KEY_REGION_THEMEID };
	public static final String[] ALL_KEYS_THEME = new String[]{ KEY_THEMES_ID, KEY_THEME_NAME, KEY_THEME_URI};
	
	
	

	public RegionsDbHelper(Context context) {
		super(context, sDATABASE_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		   db.execSQL( "CREATE TABLE " + TABLE_THEMES + "(" +  
				   		KEY_THEMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				   		KEY_THEME_NAME + " TEXT NOT NULL, " +
				   		KEY_THEME_URI + " TEXT NOT NULL) ");

		   
		   db.execSQL( "CREATE TABLE " + TABLE_REGIONS + "(" +
		          KEY_REGION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		          KEY_REGION_NAME + " TEXT NOT NULL, " +
		          KEY_REGION_LAT_S + " REAL NOT NULL, " +
		          KEY_REGION_LAT_N + " REAL NOT NULL, " +
		          KEY_REGION_LONG_W + " REAL NOT NULL, " +
		          KEY_REGION_LONG_E + " REAL NOT NULL, " +
		          KEY_REGION_THEMEID + " INTEGER," +
		          "FOREIGN KEY(" + KEY_REGION_THEMEID + ") REFERENCES THEMES(" + KEY_THEMES_ID + "))" );
		 	
		   //TODO: remove
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme105','android.resource://compmovil.themebylocation/raw/region105')");
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme112','android.resource://compmovil.themebylocation/raw/region112')");
		   db.execSQL("INSERT INTO THEMES(theme_name, theme_uri) VALUES('theme120','android.resource://compmovil.themebylocation/raw/region120')");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
