package compmovil.themebylocation.dbeditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

import compmovil.themebylocation.R;

public class RegionEditor implements OnClickListener {
	
	private Context mActivity;
	private DBAdapter mRegionsDB;
	
	RegionEditor(Context ctx, DBAdapter db){
		mActivity = ctx;
		mRegionsDB = db;
	}

	@Override
	public void onClick(View v) {
		createRegionEditDialog(((TextView)v).getId()).show();
      };

    public void addRegion(){
    	createRegionEditDialog(-1).show();
    }
      
    private Dialog createRegionEditDialog(int regionId) {
          AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
          LayoutInflater inflater = ((Activity)mActivity).getLayoutInflater();          
          final View view = inflater.inflate(R.layout.region_edit_dialog, null);
          
          TextView regionNameView = (TextView)view.findViewById(R.id.region_name_edit);
          TextView latitude0View = (TextView)view.findViewById(R.id.region_latitude_edit);
          TextView longitude0View  = (TextView)view.findViewById(R.id.region_longitude_edit);
                   
          //Load parameters from database
          if (regionId != -1){
        	  Cursor c = mRegionsDB.getRegion(regionId);
        	  regionNameView.setText(c.getString(c.getColumnIndex("reg_name")));
        	  latitude0View.setText(c.getString(c.getColumnIndex("reg_latitude_s")));
        	  longitude0View.setText(c.getString(c.getColumnIndex("reg_longitude_w")));
          }
          

          final TextView[] params = new TextView[]{regionNameView,latitude0View,longitude0View};
          builder.setView(view)
                 .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int id) {
                         String regionName = params[0].getText().toString();
                         float latitude0 = Float.parseFloat(params[1].getText().toString());
                         float longitude0 = Float.parseFloat(params[2].getText().toString());               
                         
                         mRegionsDB.addRegion(regionName,latitude0,longitude0,latitude0 + 2.0f, longitude0+2.0f);
                         
                         //TODO: replace with controller
                         ((EditorActivity)mActivity).refreshTable();
                     }
                 })
                 .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                     }
                 });      
          return builder.create();
      }

    
}