package compmovil.themebylocation.dbeditor;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import compmovil.themebylocation.R;
import compmovil.themebylocation.models.ThemesManager;

public class ThemeSelector implements OnItemSelectedListener {
	
//	ArrayAdapter<String> adapter = 
//	

	private Context mContext;
 
	
	ThemeSelector(Context ctx){
		mContext = ctx;
		
	}

	
	 public void onItemSelected(AdapterView<?> parent, View view, 
	            int pos, long id) {
	        // An item was selected. You can retrieve the selected item using
	        // parent.getItemAtPosition(pos)
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }

	
	
//	@Override
//	public void onClick(View v) {
//        Toast.makeText(mContext, "Click en Tema " + ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
//      };


}
