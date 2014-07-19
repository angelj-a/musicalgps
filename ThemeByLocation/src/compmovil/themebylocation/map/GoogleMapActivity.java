package compmovil.themebylocation.map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
	
	private GoogleMap mMap;
	private Marker mCorner0;
	private Marker mCorner1;
	private Polygon mRegion;
	
	
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
        	else {
        		//TODO: draw a rectangle
        		mCorner1 = mMap.addMarker(marker);
        		//Don't accept any more markers
        		mMap.setOnMapClickListener(null);
        		
        		drawRegion();        		
        	}
            Toast.makeText(GoogleMapActivity.this, "Coordenadas: lat " + point.latitude + ", long " + point.longitude, Toast.LENGTH_LONG).show();
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
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		if (mMap == null)
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
        LatLng sydney = new LatLng(-33.867, 151.206);

        mMap.setMyLocationEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));


        mMap.setOnMapClickListener(mMapClickListener);        
	
			
//			mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
//					CAMERA_LAT, CAMERA_LNG)));

	}	


}
