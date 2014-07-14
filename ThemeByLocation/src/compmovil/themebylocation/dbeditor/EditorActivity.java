package compmovil.themebylocation.dbeditor;

import android.app.Activity;
import android.os.Bundle;

public class EditorActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (savedInstanceState != null) {}
        
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
//		maincontroller.stop(MainController.StopOptions.ONLY_CONTROLLER);
//		Log.i("THEMELOCATION", "onDestroy");
		super.onDestroy();
	}
	

	public void onSaveInstanceState(Bundle savedInstanceState) {
		//savedInstanceState.putBoolean("mIsControllerBound", maincontroller.isBound());
	}
	
	
}
