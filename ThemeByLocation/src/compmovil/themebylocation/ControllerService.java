package compmovil.themebylocation;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.widget.Toast;


public class ControllerService extends Service {
	
	//DEBUG
	
	
	//Options implemented by ControllerService.IncomingHandler
	public final static int REGISTER_CLIENT_HANDLER  = 0;
	public final static int CLIENT_MSG_OP1 = 1;	//DUMMY
	public final static int CLIENT_MSG_OP2 = 2;	//DUMMY
	
	private HandlerThread mServiceThread;
	private ControllerServiceHandler mServiceHandler;
	
	private Messenger mMessengerMe;
	private Messenger mMessengerClient;

	
	
	class ControllerServiceHandler extends Handler {
		
		public ControllerServiceHandler(Looper looper){
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			
				case REGISTER_CLIENT_HANDLER:
					Toast.makeText(getApplicationContext(), "Recibido el handler del cliente", Toast.LENGTH_SHORT).show();
					mMessengerClient = msg.replyTo;
					break;
				case CLIENT_MSG_OP1:
					Toast.makeText(getApplicationContext(), "Recibido mensaje" + CLIENT_MSG_OP1, Toast.LENGTH_SHORT).show();
					break;
					
				case CLIENT_MSG_OP2:
					Toast.makeText(getApplicationContext(), "Recibido mensaje" + CLIENT_MSG_OP2, Toast.LENGTH_SHORT).show();
					break;					
			
				default:
					super.handleMessage(msg);		
			}
		}
	}
	
	
	@Override
	public void onCreate (){
		super.onCreate();
		// Starts a separate thread for the message handler
		mServiceThread = new HandlerThread("[ControllerService] ControllerServiceHandler", 
				Process.THREAD_PRIORITY_BACKGROUND);
	    mServiceThread.start();
	    mServiceHandler = new ControllerServiceHandler(mServiceThread.getLooper());
		
	    // This service's messenger
		mMessengerMe = new Messenger(mServiceHandler);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(getApplicationContext(), "Bindeado", Toast.LENGTH_SHORT).show();
		return mMessengerMe.getBinder();
	}
		
	@Override
	 public boolean onUnbind (Intent intent){
		 //TODO: stop all secondary services
		 stopSelf();
		 return false;
	  }	

}
