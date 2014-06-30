package compmovil.themebylocation;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import compmovil.themebylocation.controllers.MainController;
import compmovil.themebylocation.views.MainView;

public class MainActivity extends Activity {
	
	private MainController maincontroller;
	private MainView mainview;
	
	private boolean mBackPressed = false;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainview = new MainView(this);
        maincontroller = new MainController(this, mainview);
        
        if (savedInstanceState != null){
        	//do something
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
		super.onPause();
	}

	@Override
    protected void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed(){
		if (maincontroller.isServiceRunning()){
			if (!mBackPressed) {
				mBackPressed = true;
				Toast.makeText(this, "Presione otra vez para salir. El servicio seguirá en ejecución.", Toast.LENGTH_SHORT).show();				
			}
			else {
				mBackPressed = false;
				maincontroller.stopAll();
				finish();
			}
		}
		else
			super.onBackPressed();
	}

//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		  savedInstanceState.putInt("identifier", intvariable);
//	}
	
}
