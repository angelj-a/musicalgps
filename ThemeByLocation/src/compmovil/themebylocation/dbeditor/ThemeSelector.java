package compmovil.themebylocation.dbeditor;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ThemeSelector implements OnItemSelectedListener {
	
//	ArrayAdapter<String> adapter = 
//	

	private Context mContext;
	private DBAdapter mDB;
 
	
	ThemeSelector(Context ctx, DBAdapter regionsthemesdb){
		mContext = ctx;
		mDB = regionsthemesdb;
		
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
