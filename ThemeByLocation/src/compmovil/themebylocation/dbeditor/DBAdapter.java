package compmovil.themebylocation.dbeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	
	public static final boolean WRITABLE = true;
	public static final boolean READONLY= false;
	
	private Context mContext;
	private RegionsDbHelper mDbHelper;
	private SQLiteDatabase mDB;
	
	public DBAdapter(Context ctx) {
		mContext = ctx;
		mDbHelper = new RegionsDbHelper(mContext);
	}
	
	public void open(boolean writable){
		if (writable)
			mDB = mDbHelper.getWritableDatabase();
		else
			mDB = mDbHelper.getReadableDatabase();
	}
	
	public void close(){
		mDbHelper.close();
	}
	
    
	//Default theme: 0
	
	public void addRegion(String name, double lat0, double long0, double lat1, double long1){
   	 ContentValues values = new ContentValues();
       
     values.put(RegionsDbHelper.KEY_REGION_NAME, name);
	 values.put(RegionsDbHelper.KEY_REGION_LAT_S, lat0);
	 values.put(RegionsDbHelper.KEY_REGION_LAT_N, lat1);
	 values.put(RegionsDbHelper.KEY_REGION_LONG_W, long0);
	 values.put(RegionsDbHelper.KEY_REGION_LONG_E, long1);
	 values.put(RegionsDbHelper.KEY_REGION_THEMEID, 0);
		 
	 mDB.insert(RegionsDbHelper.TABLE_REGIONS, null, values);	 
   };
   

   public void updateRegionState(int id, String name, double lat0, double long0, double lat1, double long1){
	   	 ContentValues values = new ContentValues();
	       
	     values.put(RegionsDbHelper.KEY_REGION_NAME, name);
		 values.put(RegionsDbHelper.KEY_REGION_LAT_S, lat0);
		 values.put(RegionsDbHelper.KEY_REGION_LAT_N, lat1);
		 values.put(RegionsDbHelper.KEY_REGION_LONG_W, long0);
		 values.put(RegionsDbHelper.KEY_REGION_LONG_E, long1);
		 values.put(RegionsDbHelper.KEY_REGION_THEMEID, 0);
			 
		 mDB.update(RegionsDbHelper.TABLE_REGIONS, values, RegionsDbHelper.KEY_REGION_ID + "=" + id,null);	 
   };
   
   
   public void updateRegionName(int regionid, String name){
	   ContentValues values = new ContentValues();
	       
	   values.put(RegionsDbHelper.KEY_REGION_NAME, name);
	   mDB.update(RegionsDbHelper.TABLE_REGIONS, values, RegionsDbHelper.KEY_REGION_ID + "=" + regionid,null);
   }
   
   public void updateRegionTheme(int regionid, int themeid){
	   ContentValues values = new ContentValues();
	       
	   values.put(RegionsDbHelper.KEY_REGION_THEMEID, themeid);
	   mDB.update(RegionsDbHelper.TABLE_REGIONS, values, RegionsDbHelper.KEY_REGION_ID + "=" + regionid,null);
   }
	
   
   public List<RegionModel> getAllRegions(){
	   List<RegionModel> listRegMod = new ArrayList<RegionModel>();
   		Cursor  cursor = mDB.query(true,RegionsDbHelper.TABLE_REGIONS, RegionsDbHelper.ALL_KEYS_REGION,null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
      		 do {          
      			 listRegMod.add(new RegionModel(cursor.getString(1), cursor.getDouble(2),cursor.getDouble(3),cursor.getDouble(4), cursor.getDouble(5), cursor.getInt(6)));
      	        } while (cursor.moveToNext());
          }
   	return listRegMod;
   }
   
   public List<String> getAllRegionsNames(){
	   List<String> listRegName = new ArrayList<String>();
   		Cursor  cursor = mDB.query(true,RegionsDbHelper.TABLE_REGIONS, new String[]{RegionsDbHelper.KEY_REGION_NAME},null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
      		 do {          
      			listRegName.add(cursor.getString(0));
      	     } while (cursor.moveToNext());
          }
   	return listRegName;
   }
	
   public List<ThemeModel> getAllThemes(){
	   List<ThemeModel> themes = new ArrayList<ThemeModel>();
	   	Cursor  cursor = mDB.query(true,RegionsDbHelper.TABLE_THEMES, RegionsDbHelper.ALL_KEYS_THEME,null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
   		 do {          
   			 themes.add(new ThemeModel(cursor.getString(1),cursor.getString(2)));
   	        } while (cursor.moveToNext());
       }
	   	return themes;
   }
   
   public List<String> getAllThemesNames(){
	   List<String> themes = new ArrayList<String>();
	   	Cursor  cursor = mDB.query(true,RegionsDbHelper.TABLE_THEMES, new String[]{RegionsDbHelper.KEY_THEME_NAME},null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
   		 do {          
   			 themes.add(cursor.getString(0));
   	        } while (cursor.moveToNext());
       }
	   	return themes;
   }

   
   
   @SuppressLint("UseSparseArrays")
   public Map<Integer,String> getThemesPerRegion(){
	    Map<Integer,String> themesperreg = new HashMap<Integer,String>();
	    final String QUERY_URI_PER_REGION = "SELECT " + RegionsDbHelper.KEY_REGION_ID + "," + RegionsDbHelper.KEY_THEME_URI + " FROM " + RegionsDbHelper.TABLE_REGIONS + " r INNER JOIN " + 
	    											RegionsDbHelper.TABLE_THEMES + "t ON t." + RegionsDbHelper.KEY_REGION_THEMEID +"= b." + RegionsDbHelper.KEY_THEMES_ID + ";";
	   	//Cursor  cursor = mDB.query(true,RegionsDbHelper.TABLE_REGIONS, new String[]{RegionsDbHelper.KEY_REGION_ID, RegionsDbHelper.KEY_REGION_THEMEID},null,null,null,null,null,null);
	    Cursor cursor = mDB.rawQuery(QUERY_URI_PER_REGION, null);
    	if (cursor.moveToFirst()) {
   		 do {          
   			themesperreg.put(cursor.getInt(0),cursor.getString(1));
   	     } while (cursor.moveToNext());
       }	    
	    return themesperreg;
   }
   
   //Warning: somebody might be able to modify the table
   public Cursor getRegion(int id){
   	Cursor cursor = mDB.query(true,RegionsDbHelper.TABLE_REGIONS, RegionsDbHelper.ALL_KEYS_REGION, RegionsDbHelper.KEY_REGION_ID+"="+id,null,null,null,null,null);
   	if (cursor != null)
		cursor.moveToFirst();       

   	return cursor;
   }

}
