package compmovil.themebylocation.controllers;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class Controller2 {
	
	private HandlerThread handlerThread;
	private Handler messagesHandler;

	public Controller2() {

		handlerThread= new HandlerThread("GPS/Player events thread");
		handlerThread.start();
		
		messagesHandler = new Handler(handlerThread.getLooper())
		{
			  @Override
			  public void handleMessage(Message msg) {
		     }			
		};
		
	}

}
