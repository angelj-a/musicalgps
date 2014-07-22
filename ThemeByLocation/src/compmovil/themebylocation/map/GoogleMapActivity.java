package compmovil.themebylocation.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import compmovil.themebylocation.R;

public class GoogleMapActivity extends Activity {
	
	
	/***********************************************************
	 * 
	 * GoogleMapActivity: attributes and constants
	 * 
	 ***********************************************************/
	
	public static final String REGION_ID = "id";
	public static final String REGION_NAME= "name";	
	public static final String LONGITUDE1 = "longitude1";
	public static final String LATITUDE1 = "latitude1";
	public static final String LONGITUDE0 = "longitude0";
	public static final String LATITUDE0 = "latitude0";

	
	public static final int REGION_BOUNDS = 1;

	
	private GoogleMap mMap;
	private Marker mCorner0, mCorner1;
	private Polygon mRegion;
	
	private Intent mResult;
	
	private LatLng mMapCameraOnStart;
	
	
	private OnMapClickListener mMapClickListener = new GoogleMap.OnMapClickListener() {		
		@Override
		public void onMapClick(LatLng point) {            	            	
            MarkerOptions marker = new MarkerOptions()
            					.position(new LatLng(point.latitude, point.longitude))
            					.draggable(true);
        	
            if (mCorner0 == null) {
        		mCorner0 = mMap.addMarker(marker);
        		//prepare the map to accept markers to be dragged
        		mMap.setOnMarkerDragListener(mMarkerDragListener);
        		
        	}
        	else if (mCorner1 == null){
        		mCorner1 = mMap.addMarker(marker);
        		//Don't accept any more markers
        		mMap.setOnMapClickListener(null);

        		drawRegion();        		
        	}
        	else
        		mMap.setOnMapClickListener(null);
		}
	};
	
	private OnMarkerDragListener mMarkerDragListener = new GoogleMap.OnMarkerDragListener() {		
		@Override
		public void onMarkerDragStart(Marker marker) {
			mRegion.remove();
		}
		
		@Override
		public void onMarkerDragEnd(Marker marker) {
			drawRegion();
		}

		@Override
		public void onMarkerDrag(Marker marker) {
		}
	};	
	
	
	
	
	/***********************************************************
	 * 
	 * GoogleMapActivity: methods
	 * 
	 ***********************************************************/
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		if (mMap == null)
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
				
		setMapAccordingToParametersIfPossible();
		
        mMap.setOnMapClickListener(mMapClickListener);
		//TODO: redraw rectangle and markers if needed
        
	}
	
	@Override
    protected void onStart(){
		super.onStart();
	}  

	@Override
    protected void onRestart(){
		super.onRestart();	
	}

	@Override
    protected void onResume(){
		super.onResume();
	}

	@Override
    protected void onPause(){
		super.onPause();
	}

	@Override
    protected void onStop(){
		//TODO: save current markers just in case the user rotates the device's screen
		super.onStop();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.maps_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    	case R.id.help_tip:
	    		Toast.makeText(this, R.string.help_tip, Toast.LENGTH_LONG).show();
	    		return true;
	        case R.id.discard_region:
	        	setResult(RESULT_CANCELED);
	        	finish();
	            return true;
	        
	        // On saving changes
	        case R.id.save_region:
	        	if (mCorner0 != null && mCorner1 != null) {
	        		mResult = new Intent();
	        		mResult.putExtra(REGION_ID, getIntent().getIntExtra(REGION_ID, -1));
	        		mResult.putExtra(REGION_NAME, getIntent().getStringExtra(REGION_NAME));
	        		mResult.putExtra(LATITUDE0, mCorner0.getPosition().latitude);
	        		mResult.putExtra(LONGITUDE0, mCorner0.getPosition().longitude);
	        		mResult.putExtra(LATITUDE1, mCorner1.getPosition().latitude);
	        		mResult.putExtra(LONGITUDE1, mCorner1.getPosition().longitude);
	        		
	        		setResult(RESULT_OK,mResult);
	        		finish();
	        		return true;
	        	}
	        	else {
	        		Toast.makeText(this, "Región incompleta. Presione Cancelar o marque las dos esquinas", Toast.LENGTH_LONG).show();
	        		return true;
	        	}
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    setResult(RESULT_CANCELED);
	    finish();
	}
	
	
	/***********************************************************
	 * 
	 * GoogleMapActivity: auxiliary functions
	 * 
	 ***********************************************************/	
	
	private void drawRegion(){
		mRegion = mMap.addPolygon(new PolygonOptions()
        .add(mCorner0.getPosition(),
        	 new LatLng(mCorner0.getPosition().latitude, mCorner1.getPosition().longitude),
        	 mCorner1.getPosition(),
        	 new LatLng(mCorner1.getPosition().latitude, mCorner0.getPosition().longitude))
        .strokeColor(Color.BLUE)
        .strokeWidth(2)
        .fillColor(Color.BLUE & 0x3FFFFFFF));
	}

	//
	private void setMapAccordingToParametersIfPossible(){
		Bundle extras = getIntent().getExtras();
		if (extras.containsKey(LATITUDE0) &&
			extras.containsKey(LATITUDE1) &&
			extras.containsKey(LONGITUDE0)&&
			extras.containsKey(LONGITUDE1))
		{
			mCorner0 = mMap.addMarker(new MarkerOptions()
										.position(new LatLng(extras.getDouble(LATITUDE0), extras.getDouble(LONGITUDE0)))
										.draggable(true));
			mCorner1 = mMap.addMarker(new MarkerOptions()
										.position(new LatLng(extras.getDouble(LATITUDE1), extras.getDouble(LONGITUDE1)))
										.draggable(true));
			drawRegion();
				
		    mMapCameraOnStart = new LatLng(extras.getDouble(LATITUDE0), extras.getDouble(LONGITUDE0));
			mMap.setOnMarkerDragListener(mMarkerDragListener);
		}
		else
				mMapCameraOnStart = new LatLng(-34.544327,-58.439425);

		mMap.setMyLocationEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMapCameraOnStart, 13));

	}

}
