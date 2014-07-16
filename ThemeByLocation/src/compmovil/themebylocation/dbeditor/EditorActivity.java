package compmovil.themebylocation.dbeditor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import compmovil.themebylocation.R;
import compmovil.themebylocation.models.ThemesManager;

public class EditorActivity extends Activity {
	
	private RegionEditor mRegionEditor;
	private ThemeSelector mThemeSelector;
	//private RegionsDatabase mRegionsDatabase;
	
	
//	private ThemesManager mThemesManager;
//	private Spinner mDropdown;
//	private ArrayAdapter<String> mAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regionsadmin_layout);
        
        mRegionEditor = new RegionEditor(this);
        mThemeSelector = new ThemeSelector(this);
        
//		mThemesManager = new ThemesManager();
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mThemesManager.getThemesNames());
//		mDropdown = (Spinner) findViewById(R.id.spinner1);
//		mDropdown.setAdapter(mAdapter);        
        
        
        
        if (savedInstanceState != null) {
        }
        
    }
	
	
	@Override
    protected void onStart(){
		super.onStart();
		initializeRegionsAdmin();
		initializeTable();
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
		super.onStop();
	}

	@Override
    protected void onDestroy(){
		super.onDestroy();
	}
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		//savedInstanceState.putBoolean("mIsControllerBound", maincontroller.isBound());
	}
	
	
	
	private void initializeTable() {
		TableLayout table = (TableLayout) findViewById(R.id.tableRegions);


	    for (int i = 0; i < 2; i++) {

	        TableRow row= new TableRow(this);
	        TableRow.LayoutParams layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(layoutParams);
	        
	        TextView idRow = new TextView(this);
	        TextView regionName = new TextView(this);
	        TextView themeName = new TextView(this);
	        
	        idRow.setTextSize(24);
	        regionName.setTextSize(24);
	        themeName.setTextSize(24);
	        
	        idRow.setText(Integer.toString(i+1));
	        idRow.setPaddingRelative(10, 10, 20, 10);
	        idRow.setTextColor(Color.parseColor("#FFFFFF"));
	        regionName.setText("REGIÓN " + i);
	        regionName.setPaddingRelative(2, 10, 2, 10);
	        themeName.setText("ALGÚN TEMA");
	        themeName.setPaddingRelative(2, 10, 20, 10);
	        
	        if (i%2 == 0){
	        	idRow.setBackgroundColor(Color.parseColor("#777777"));
	        	regionName.setBackgroundColor(Color.parseColor("#777777"));
	        	themeName.setBackgroundColor(Color.parseColor("#777777"));
	        }
	        else {
	        	idRow.setBackgroundColor(Color.parseColor("#CCCCCC"));
	        	regionName.setBackgroundColor(Color.parseColor("#CCCCCC"));
	        	themeName.setBackgroundColor(Color.parseColor("#CCCCCC"));	        	
	        }
	        
	        
	        regionName.setOnClickListener(mRegionEditor);
	        //themeName.setOnClickListener(mThemeSelector);
	        
	        row.addView(idRow);
	        row.addView(regionName);
	        row.addView(themeName);
	       
	        table.addView(row,i);
	    }
		
	}


	private void initializeRegionsAdmin() {
		// TODO Auto-generated method stub
		
	}
	
}
