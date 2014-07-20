package compmovil.themebylocation.dbeditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import compmovil.themebylocation.R;
import compmovil.themebylocation.map.GoogleMapActivity;

public class RegionEditor implements OnClickListener {
	
	private Context mActivity;
	private DBAdapter mRegionsDB;
	
	RegionEditor(Context ctx, DBAdapter db){
		mActivity = ctx;
		mRegionsDB = db;
	}

	@Override
	public void onClick(View v) {
    };
    
    public void editRegionName(int id){
		regionEditDialog(id);
    }

    public void addRegion(){
    	regionEditDialog(-1);
    	//note: refreshTable is executed by Activity at onActivityResult
    }
      
    private void regionEditDialog(int regionId) {
          LayoutInflater inflater = ((Activity)mActivity).getLayoutInflater();          
          final View view = inflater.inflate(R.layout.region_edit_dialog, null);          
          final Intent params = new Intent(mActivity, GoogleMapActivity.class);                    
          final EditText regionNameView = (EditText)view.findViewById(R.id.region_name_edit);
          final int id = regionId;
                   
          //Load parameters from database
          if (id  >= 0){
        	  Cursor c = mRegionsDB.getRegion(id);
        	  regionNameView.setText(c.getString(c.getColumnIndex("reg_name")));
          }
          
          
          AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
          final AlertDialog alert = builder.setView(view)
                 .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int buttonid) {
                         String regionName = regionNameView.getText().toString();
	                         if (id < 0) {
	                        	 params.putExtra(GoogleMapActivity.REGION_ID, id);
	                        	 params.putExtra(GoogleMapActivity.REGION_NAME, regionName);
	                         	 launchMapActivity(params);
	                         }
	                         else {
	                        	 mRegionsDB.updateRegionName(id,regionName);
	                		      //TODO: REFACTOR (callback?)
	                		   	 ((EditorActivity)mActivity).refreshTable();
	                         }
                     }
                 })
                 .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int buttonid) {
                    	 dialog.dismiss();
                     }
          }).create();
          
          alert.show();
          if (id < 0)
        	  alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

          //Don't save if region name is an empty string
          regionNameView.addTextChangedListener( new TextWatcher() {
                	  public void afterTextChanged(Editable s) {
                		  if (regionNameView.getText().length() > 0)
                			  alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                		  else 
                			  alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                	  	 }

                	  public void beforeTextChanged(CharSequence s, int start, int count,
                			  int after) {
                	  }

                	  public void onTextChanged(CharSequence s, int start, int before,
                			  int count) {
                	  }
                  }
        ); 
          
      }
    
    
    //This method is only called by Activity after receiving results from GoogleMapActivity
    public void persistRegion(Intent regionData){
		int region_id = regionData.getIntExtra(GoogleMapActivity.REGION_ID, -1);
		if (region_id == -1)
			mRegionsDB.addRegion(regionData.getStringExtra(GoogleMapActivity.REGION_NAME),
					regionData.getDoubleExtra(GoogleMapActivity.LATITUDE0, 0),
					regionData.getDoubleExtra(GoogleMapActivity.LONGITUDE0, 0),
					regionData.getDoubleExtra(GoogleMapActivity.LATITUDE1, 0),
					regionData.getDoubleExtra(GoogleMapActivity.LONGITUDE1, 0));
		else
			mRegionsDB.updateRegionState(region_id,
											regionData.getStringExtra(GoogleMapActivity.REGION_NAME),
											regionData.getDoubleExtra(GoogleMapActivity.LATITUDE0, 0),
											regionData.getDoubleExtra(GoogleMapActivity.LONGITUDE0, 0),
											regionData.getDoubleExtra(GoogleMapActivity.LATITUDE1, 0),
											regionData.getDoubleExtra(GoogleMapActivity.LONGITUDE1, 0));
    }
    
	public void editRegionCoordinates(int regionId) {
		Cursor c = mRegionsDB.getRegion(regionId);
		Intent params = new Intent(mActivity, GoogleMapActivity.class);
		params.putExtra(GoogleMapActivity.REGION_ID, regionId);
		params.putExtra(GoogleMapActivity.REGION_NAME, c.getString(c.getColumnIndex(RegionsDbHelper.KEY_REGION_NAME)));
		params.putExtra(GoogleMapActivity.LATITUDE0, c.getDouble(c.getColumnIndex(RegionsDbHelper.KEY_REGION_LAT_S)));
		params.putExtra(GoogleMapActivity.LONGITUDE0, c.getDouble(c.getColumnIndex(RegionsDbHelper.KEY_REGION_LONG_W)));
		params.putExtra(GoogleMapActivity.LATITUDE1, c.getDouble(c.getColumnIndex(RegionsDbHelper.KEY_REGION_LAT_N)));
		params.putExtra(GoogleMapActivity.LONGITUDE1, c.getDouble(c.getColumnIndex(RegionsDbHelper.KEY_REGION_LONG_E)));
		launchMapActivity(params);
	}
	
	
	public void changeTheme(int regionid, int newthemeid){
   	 	mRegionsDB.updateRegionTheme(regionid,newthemeid);
   	 	//TODO: alertdialog
	}
	
	private void launchMapActivity(Intent params) {
		((Activity)mActivity).startActivityForResult(params, GoogleMapActivity.REGION_BOUNDS);		
	}
    
}