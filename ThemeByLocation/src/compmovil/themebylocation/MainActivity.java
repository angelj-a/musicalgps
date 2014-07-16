package compmovil.themebylocation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import compmovil.themebylocation.controllers.MainController;
import compmovil.themebylocation.dbeditor.EditorActivity;
import compmovil.themebylocation.views.MainView;

public class MainActivity extends Activity {
	
	private MainController maincontroller;
	private MainView mainview;
	
	private boolean mIsControllerBound;
	//private boolean mBackPressed = false;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainview = new MainView(this);
        maincontroller = new MainController(this, mainview);
        
        if (savedInstanceState != null)
        	mIsControllerBound = savedInstanceState.getBoolean("mIsControllerBound");
        else
        	mIsControllerBound = false;
        
    }
	
	
	@Override
    protected void onStart(){
		if (mIsControllerBound) {
			maincontroller.bind();
		}
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
		maincontroller.stop(MainController.StopOptions.ONLY_CONTROLLER);
		Log.i("THEMELOCATION", "onDestroy");
		super.onDestroy();
	}
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean("mIsControllerBound", maincontroller.isBound());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.manage_regions:
	        	startActivity(new Intent(this, EditorActivity.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    menu.findItem(R.id.manage_regions).setEnabled(!maincontroller.isServiceRunning());
	    return super.onPrepareOptionsMenu(menu);

	}
	
}
