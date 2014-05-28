package compmovil.themebylocation.controllers;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import compmovil.themebylocation.views.MainView;

public class UIController extends Handler implements OnClickListener {
	
	private MainView view;
	
	public UIController(MainView v) {
		view = v;
	}


	@Override
	public void onClick(View v) {
		if (view.getStart() == (Button)v){
			Log.i("THEMELOCATION", "Click start");
			if (!("".equals(view.getFrequency()) )) {
				startButtonSubroutine();
				Log.i("THEMELOCATION", "frecuencia = " + view.getFrequency());
			}
		}
		else if (view.getStop() == (Button)v){
			Log.i("THEMELOCATION", "Click stop");
			stopButtonSubroutine();
		}
		else 
			Log.i("THEMELOCATION", "UNKNOWN");
	}
	
	

	public void startButtonSubroutine() {
		view.disableStart();
		view.disableChangingFrequency();
		
	}
	
	public void stopButtonSubroutine() {
		view.enableStart();
		view.enableChangingFrequency();
	}	

}
