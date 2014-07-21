package compmovil.themebylocation.dbeditor;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import compmovil.themebylocation.R;
import compmovil.themebylocation.map.GoogleMapActivity;

public class EditorActivity extends Activity {
	
	private RegionEditor mRegionEditor;
	private ThemeSelector mThemeEditor; //TODO
	
	private DBAdapter mRegionsThemesDB;
	
//	private ThemesManager mThemesManager;
//	private Spinner mDropdown;
//	private ArrayAdapter<String> mAdapter;
	
	TableLayout mTable;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regionsadmin_layout);
                
		initializeRegionsAdmin();
		showTable();
        
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
		mRegionsThemesDB.close();
		super.onDestroy();
	}
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		//savedInstanceState.putBoolean("mIsControllerBound", maincontroller.isBound());
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.region_editor_menu, menu);
	    //mMenu = menu;
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_region:
	        	//TODO: reorganize
	        	mRegionEditor.addRegion();
	            return true;
	        case R.id.refresh_table:
	        	refreshTable();
	        	return true;
	        case R.id.test_googlemap_activity:
	        	//mRegionsThemesDB.updateRegionTheme(1, 1);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	
	//
	// Gets coordinates from GoogleMapActivity and piggybacked id and region name 
	//
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data){
	    super.onActivityResult(reqCode, resCode, data);
	    if (resCode == RESULT_CANCELED)
	    	return;
	    if (reqCode == GoogleMapActivity.REGION_BOUNDS)
	    	if (resCode == RESULT_OK) {
	    		mRegionEditor.persistRegion(data);	    		
	    		refreshTable(); 	    		//TODO: REFACTOR (delegate)	  
	    	}
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.regions_editor_ctx_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) { 
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int regionIdMock = 1;
        switch(item.getItemId()){
            case R.id.edit_region_name:
            	mRegionEditor.editRegionName(regionIdMock);
                break;
            case R.id.edit_region_coordinates:
            	mRegionEditor.editRegionCoordinates(regionIdMock);
                break;
            case R.id.choose_another_theme:
            	Toast.makeText(this, "IMPLEMENTAR selección de tema: ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_region:
            	Toast.makeText(this, "IMPLEMENTAR borrado", Toast.LENGTH_SHORT).show();
                break;                
        }
        return true;
    }


	private void showTable() {		
		//TODO: MVC
		List<RegionModel> listOfRegions = mRegionsThemesDB.getAllRegions();
		List<String> listOfThemesNames = mRegionsThemesDB.getAllThemesNames();
		
		if (mTable == null)
			mTable = (TableLayout) findViewById(R.id.tableRegions);

	    for (int i = 0; i < listOfRegions.size(); i++) {

	        TableRow row= new TableRow(this);
	        TableRow.LayoutParams layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(layoutParams);
	        
	        TextView idRow = new TextView(this);
	        TextView regionName = new TextView(this);
	        TextView themeName = new TextView(this);
	        
	        //To identify its position
	        regionName.setId(i);
	        themeName.setId(i);
	        
	        
	        //Appearance
	        idRow.setTextSize(24);
	        regionName.setTextSize(24);
	        themeName.setTextSize(24);
	        
	        idRow.setText(Integer.toString(i+1));
	        idRow.setPaddingRelative(10, 10, 20, 10);
	        idRow.setTextColor(Color.parseColor("#FFFFFF"));
	        regionName.setText(listOfRegions.get(i).getName());
	        regionName.setPaddingRelative(2, 10, 2, 10);
	        themeName.setText(listOfThemesNames
	        					.get(listOfRegions.get(i).getThemeId()));
	        themeName.setPaddingRelative(2, 10, 20, 10);
	        
	        if (i%2 == 0){
	        	idRow.setBackgroundColor(Color.parseColor("#777777"));
	        	regionName.setBackgroundColor(Color.parseColor("#888888"));
	        	themeName.setBackgroundColor(Color.parseColor("#777777"));
	        }
	        else {
	        	idRow.setBackgroundColor(Color.parseColor("#CCCCCC"));
	        	regionName.setBackgroundColor(Color.parseColor("#DDDDDD"));
	        	themeName.setBackgroundColor(Color.parseColor("#CCCCCC"));	        	
	        }
	        
	        
	        //regionName.setOnClickListener(mRegionEditor);
	        //themeName.setOnClickListener(mThemeSelector);
	        
	        row.addView(idRow);
	        row.addView(regionName);
	        row.addView(themeName);
	        
	        registerForContextMenu(row);
	        
	        
	       
	        mTable.addView(row,i);
	    }
		
	}
	
	public void refreshTable(){
 		mTable.removeAllViews();
 		showTable();
	}


	private void initializeRegionsAdmin() {
		mRegionsThemesDB = new DBAdapter(this);
		mRegionsThemesDB.open(DBAdapter.WRITABLE);
        
        
        mRegionEditor = new RegionEditor(this, mRegionsThemesDB);
//        mThemeSelector = new ThemeSelector(this, mRegionsThemesDB);	
	}
	
}
