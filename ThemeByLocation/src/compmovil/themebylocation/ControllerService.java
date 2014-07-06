package compmovil.themebylocation;


import compmovil.themebylocation.controllers.MainController;
import compmovil.themebylocation.models.Effector;
import compmovil.themebylocation.models.MusicPlayerEffector;
import compmovil.themebylocation.models.RectangularRegion;
import compmovil.themebylocation.models.RegionSensor;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;


public class ControllerService extends Service {
	
	
	/***********************************************************
	 * 
	 * ControllerService: attributes
	 * 
	 ***********************************************************/
	
	//DEBUG
	private int mClientsCounter = 0;
	private final static String TAG = "THEMELOCATION";
	
	//Options implemented by ControllerService.IncomingHandler
	public final static int REGISTER_CLIENT_HANDLER  = 0;
	public final static int CLIENT_INITIALIZE = 1;	
	public final static int CLIENT_MSG_OP2 = 2;	//DUMMY
	public final static int STOP_SERVICE = 3;

	public final static int DETECTOR_ENTERED_REGION = 4;
	public final static int DETECTOR_EXITED_REGION = 5;


	private static final int NOTIFICATION_ID = 10;
	private static final boolean ALLOW_REBIND = true;

	
	private HandlerThread mServiceThread;
	private ControllerServiceHandler mServiceHandler;
	
	private Messenger mMessengerMe;
	private Messenger mMessengerClient;
	
	
	private Effector mMusicTracksPlayer;
	private RegionSensor mRegionSensor;
	
	
	
	/***********************************************************
	 * 
	 * ControllerService: classes
	 * 
	 ***********************************************************/
	
	
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
					if (mRegionSensor == null) {
						mRegionSensor = new RegionSensor(mMessengerMe);
						try {
							mRegionSensor.initialize(getApplicationContext());
						} catch (Exception e) {
							notifyError(MainController.ERROR_LOCATION_PROVIDER_NOT_DETECTED);
							e.printStackTrace();
							stopAll();
							return;
						}
						mRegionSensor.startSensing();
					}					
					if (mMusicTracksPlayer == null) {
						mMusicTracksPlayer = new MusicPlayerEffector(getApplicationContext()); 
						try {
							mMusicTracksPlayer.initialize();
						} catch (Exception e) {
							notifyError(MainController.ERROR_FAILED_INITIALIZING_MUSIC_PLAYER);
							e.printStackTrace();
							stopAll();
						}
					}
					runOnForeground();
					break;
					
				//Sent by RegionSensor										
				case DETECTOR_ENTERED_REGION:
	                RectangularRegion param = (RectangularRegion) msg.obj;
					Toast.makeText(getApplicationContext(), "Entramos a Region " +  param.getId(), Toast.LENGTH_SHORT).show();
					mMusicTracksPlayer.onEnterRegion(param);
					
		            try {
		            	notifyRegionUpdate("Región " + param.getId());
		            } catch (RemoteException e) { }
					
					
					break;
					
				case DETECTOR_EXITED_REGION:
					mMusicTracksPlayer.onExitRegion();
					
		            try {
		            	notifyRegionUpdate("-");
		            }
		            catch (RemoteException e) { }					
					
					break;
				
				//TODO: not needed?
				case STOP_SERVICE: 
					stopAll();
					break;
			
				default:
					super.handleMessage(msg);		
			}
		}
	}

	
	
	/***********************************************************
	 * 
	 * ControllerService: methods
	 * 
	 ***********************************************************/
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		Log.i(TAG,"onStartCommand");
		return START_NOT_STICKY;
	}
	
	
	
	@Override
	public void onCreate (){
		super.onCreate();
		Log.i(TAG,"Service. onCreate");
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
		if (mClientsCounter == 0)
		{
			mClientsCounter++;
			Toast.makeText(getApplicationContext(), "Bindeado", Toast.LENGTH_SHORT).show();
			return mMessengerMe.getBinder();
		}
		else
			return null;
	}
		
	@Override
	public boolean onUnbind (Intent intent){
		mClientsCounter--;
		mMessengerClient = null;
		return ALLOW_REBIND;
	}	

	@Override
	public void onRebind(Intent intent) {        
		Log.i(TAG,"onRebind");
	}
	
	@Override
	public void onDestroy(){
		Log.i(TAG,"Service onDestroy");
		cleanUp();
		super.onDestroy();
	}

    
	
	/***********************************************************
	 * 
	 * ControllerService: auxiliary functions
	 * 
	 ***********************************************************/	
	
	
	private void cleanUp(){
		if (mMusicTracksPlayer != null)
			mMusicTracksPlayer.stopEffector();
		if (mRegionSensor != null) {
			mRegionSensor.stopSensing();
		}
		mServiceHandler.getLooper().quit();
			
	}
	
	private void stopAll(){
		cleanUp();
		stopSelf();
	}
	
	private void runOnForeground() {
		final Intent notificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		final Notification notification = new Notification.Builder(
				getApplicationContext())
				.setSmallIcon(android.R.drawable.ic_media_play)
				.setOngoing(true).setContentTitle("MusicalGPS en ejecución")
				.setContentText("Clic para acceder al menú del programa")
				.setContentIntent(pendingIntent).build();
  
		startForeground(NOTIFICATION_ID, notification);
	}
	
	private void notifyRegionUpdate(String info) throws RemoteException{
            Message msg_resp = Message.obtain(null, MainController.UPDATE_CURRENT_REGION);
            Bundle bundle = new Bundle();
            bundle.putString("region_name", info);
            msg_resp.setData(bundle);
            mMessengerClient.send(msg_resp);
	}
	
	private void notifyError(int errorcode){
        Message msg_resp = Message.obtain(null, MainController.ERROR);
        msg_resp.arg1 = errorcode;
        try {
			mMessengerClient.send(msg_resp);
		} catch (RemoteException e) {
			//cannot recover from error: force stop
			stopAll();
			e.printStackTrace();
		}		
	}
	
}
