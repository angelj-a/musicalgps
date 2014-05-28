package compmovil.themebylocation.controllers;

import android.app.Activity;
import android.os.HandlerThread;
import android.util.Log;

import compmovil.themebylocation.R;
import compmovil.themebylocation.views.MainView;


public class MainController {
	
	//Context
	private Activity activity;
	private MainView view;

	private UIController uiController;
	private ModelsController modelEventsController;
	private HandlerThread meventControllerThread;
	

	public MainController(Activity context, MainView v) {
		activity = context;
		view = v;
		
		uiController = new UIController(view);
		activity.findViewById(R.id.startbutton).setOnClickListener(uiController);
        activity.findViewById(R.id.stopbutton).setOnClickListener(uiController);
			
		meventControllerThread = new HandlerThread("GPS/Player events thread");
		meventControllerThread.start();		
		modelEventsController = new ModelsController(meventControllerThread.getLooper());
		
		modelEventsController.post(new Runnable(){
								public void run() {
									Log.i("THEMELOCATION","running");
								}
							});
	}

}
