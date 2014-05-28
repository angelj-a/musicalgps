package compmovil.themebylocation.controllers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ModelsController extends Handler {
	
	private Handler uihandler;

	public ModelsController(Looper looper) {
		super(looper);
	}

	public ModelsController(Looper looper, Callback callback) {
		super(looper, callback);
		uihandler = (Handler) callback;
	}
	
	  @Override
	  public void handleMessage(Message msg) {
		  
	  }			
	

}
