package compmovil.themebylocation.dbeditor;

import compmovil.themebylocation.R;

import android.app.Activity;
import android.os.Bundle;

public class EditorActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regionsadmin_layout);
        if (savedInstanceState != null) {
        }
        
    }
	
	
	@Override
    protected void onStart(){
		super.onStart();
		initializeRegionsManager();
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
		// TODO Auto-generated method stub
		
	}


	private void initializeRegionsManager() {
		// TODO Auto-generated method stub
		
	}
	
}
