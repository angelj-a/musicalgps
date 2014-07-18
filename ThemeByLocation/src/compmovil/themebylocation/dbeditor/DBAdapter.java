package compmovil.themebylocation.dbeditor;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	
	private Context mContext;
	private RegionsDbHelper mDbHelper;
	private SQLiteDatabase mDB;
	
	public DBAdapter(Context ctx) {
		mContext = ctx;
		mDbHelper = new RegionsDbHelper(mContext);
	}
	
	public void open(boolean readonly){
		if (readonly)
			mDB = mDbHelper.getReadableDatabase();
		else
			mDB = mDbHelper.getWritableDatabase();
	}
	
	public void close(){
		mDbHelper.close();
	}
	
    
	
	public void addRegion(String name, float lat0, float long0, float lat1, float long1){
   	 ContentValues values = new ContentValues();
       
     values.put(mDbHelper.cKEY_REGION_NAME, name);
	 values.put(mDbHelper.cKEY_REGION_LAT_S, lat0);
	 values.put(mDbHelper.cKEY_REGION_LAT_N, lat1);
	 values.put(mDbHelper.cKEY_REGION_LONG_W, long0);
	 values.put(mDbHelper.cKEY_REGION_LONG_E, long1);
	 values.put(mDbHelper.cKEY_REGION_THEMEID, (Integer)null);
		 
	 mDB.insert(mDbHelper.cTABLE_REGIONS, null, values);	 
   };
   

   public void updateRegionState(int id, String name, float lat0, float long0, float lat1, float long1){
	   	 ContentValues values = new ContentValues();
	       
	     values.put(mDbHelper.cKEY_REGION_NAME, name);
		 values.put(mDbHelper.cKEY_REGION_LAT_S, lat0);
		 values.put(mDbHelper.cKEY_REGION_LAT_N, lat1);
		 values.put(mDbHelper.cKEY_REGION_LONG_W, long0);
		 values.put(mDbHelper.cKEY_REGION_LONG_E, long1);
		 values.put(mDbHelper.cKEY_REGION_THEMEID, (Integer)null);
			 
		 mDB.update(mDbHelper.cTABLE_REGIONS, values, mDbHelper.cKEY_REGION_ID + "=" + id,null);	 
   };
   
   
   public void updateRegionTheme(int regionid, int themeid){
	   ContentValues values = new ContentValues();
	       
	   values.put(mDbHelper.cKEY_REGION_THEMEID, themeid);
	   mDB.update(mDbHelper.cTABLE_REGIONS, values, mDbHelper.cKEY_REGION_ID + "=" + regionid,null);
   }
	
   
   public List<RegionModel> getAllRegions(){
	   List<RegionModel> listRegMod = new ArrayList<RegionModel>();
   		Cursor  cursor = mDB.query(true,mDbHelper.cTABLE_REGIONS, mDbHelper.cALL_KEYS_REGION,null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
      		 do {          
      			 listRegMod.add(new RegionModel(cursor.getString(1), cursor.getFloat(2),cursor.getFloat(3),cursor.getFloat(4), cursor.getFloat(5), cursor.getInt(6)));
      	        } while (cursor.moveToNext());
          }
   	return listRegMod;
   }
   
   public List<String> getAllRegionsNames(){
	   List<String> listRegName = new ArrayList<String>();
   		Cursor  cursor = mDB.query(true,mDbHelper.cTABLE_REGIONS, new String[]{mDbHelper.cKEY_REGION_NAME},null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
      		 do {          
      			listRegName.add(cursor.getString(0));
      	     } while (cursor.moveToNext());
          }
   	return listRegName;
   }
	
   public List<ThemeModel> getAllThemes(){
	   List<ThemeModel> themes = new ArrayList<ThemeModel>();
	   	Cursor  cursor = mDB.query(true,mDbHelper.cTABLE_THEMES, mDbHelper.cALL_KEYS_THEME,null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
   		 do {          
   			 themes.add(new ThemeModel(cursor.getString(1),cursor.getString(2)));
   	        } while (cursor.moveToNext());
       }
	   	return themes;
   }
   
   public List<String> getAllThemesNames(){
	   List<String> themes = new ArrayList<String>();
	   	Cursor  cursor = mDB.query(true,mDbHelper.cTABLE_THEMES, new String[]{mDbHelper.cKEY_THEME_NAME},null,null,null,null,null,null);
    	if (cursor.moveToFirst()) {
   		 do {          
   			 themes.add(cursor.getString(0));
   	        } while (cursor.moveToNext());
       }
	   	return themes;
   }
   
   //Warning: somebody might be able to modify the table
   public Cursor getRegion(int id){
   	Cursor cursor = mDB.query(true,mDbHelper.cTABLE_REGIONS, mDbHelper.cALL_KEYS_REGION, mDbHelper.cKEY_REGION_ID+"="+id,null,null,null,null,null);
   	if (cursor != null)
		cursor.moveToFirst();       

   	return cursor;
   }

}
