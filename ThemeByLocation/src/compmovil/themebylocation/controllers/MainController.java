package compmovil.themebylocation.controllers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import compmovil.themebylocation.ControllerService;
import compmovil.themebylocation.R;
import compmovil.themebylocation.models.RectangularRegion;
import compmovil.themebylocation.views.MainView;


public class MainController implements OnClickListener {
	
	/***********************************************************
	 * 
	 * MainController: attributes and constants
	 * 
	 ***********************************************************/
	
	final static String TAG = "THEMELOCATION";
	public enum StopOptions { ALL, ONLY_SERVICE, ONLY_UNBIND, ONLY_CONTROLLER };

	
	//Options implemented by MainController.IncomingHandler 
	public final static int MSG_1 = 1;
	public final static int MSG_2 = 2;
	public final static int ERROR = 3;
	
	public final static int ERROR_LOCATION_PROVIDER_NOT_DETECTED = 1;
	

	//Context
	private Activity mActivity;
	private MainView mView;

    private Messenger mMyMessenger;
    
    private Messenger mService;
    private HandlerThread mIncomingHandlerThread;
    
    private boolean mIsBound = false;
    private boolean mIsRunning = false;

    
    private Intent mServiceIntent;
    
    
    
    /***********************************************************
	 * 
	 * MainController: classes
	 * 
	 ***********************************************************/
        
    class IncomingHandler extends Handler {
    	
    	public IncomingHandler(Looper looper){
    		super(looper);
    	}
    	
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_1: //TODO
            	Toast.makeText(mActivity, "Recibido mensaje de Service", Toast.LENGTH_SHORT).show();            	
                break;
            case MSG_2: //TODO
                //String str1 = msg.getData().getString("str1");
                //textStrValue.setText("Str Message: " + str1);
                break;
            case ERROR:
            	int errorcode = msg.arg1;
            	processError(errorcode);
            default:
                super.handleMessage(msg);
            }
        }

    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);

            try {
                Message msg = Message.obtain(null, ControllerService.REGISTER_CLIENT_HANDLER);
                msg.replyTo = mMyMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even do anything with it
            }
            mIsBound = true;
            //FAKE:
            mIsRunning = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
            mService = null;
            mIsBound = false;
            Log.i(TAG,"Desconectado del service");
        }
    };   
    
	
    /***********************************************************
	 * 
	 * MainController: methods
	 * 
	 ***********************************************************/
    

	public MainController(Activity context, MainView v) {
		mActivity = context;
		mView = v;
		
		// Start a new thread to handle incoming messages from ControllerService
		mIncomingHandlerThread = new HandlerThread("[MainController] IncomingHandler");
		mIncomingHandlerThread.start();
		
		mMyMessenger = new Messenger(new IncomingHandler(mIncomingHandlerThread.getLooper()));
		
        mActivity.findViewById(R.id.startbutton).setOnClickListener(this);
        mActivity.findViewById(R.id.stopbutton).setOnClickListener(this);
        mActivity.findViewById(R.id.bindbutton).setOnClickListener(this);
        mActivity.findViewById(R.id.stopservicebutton).setOnClickListener(this);
        
    }
	
	@Override
	public void onClick(View v) {
		if (mView.getStartButton() == (Button)v){
			Log.i(TAG, "Click start");
//			if (!("".equals(mView.getFrequency()) )) {
//				startButtonSubroutine();
//				Log.i(TAG, "frecuencia = " + mView.getFrequency());
//			}
			if (!mIsBound){
				// Bind to service (if possible)
				try {
					bind();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			else {
//				// Create Message 
//				//Bundle bundle = new Bundle();
//				//bundle.putString("KEY", "Mensaje desde el cliente");
//				//msg.setData(bundle);
//				
//				//fakeEnteredRegion();
//				
//			}
		}
//		else if (mView.getStopButton() == (Button)v){
//			Log.i(TAG, "Click stop");
//			stopButtonSubroutine();
//		}
		else if (mView.getBindButton() == (Button)v){
			Log.i(TAG,"Click bind");
			//fakeExitedRegion();
			bind();
		}
		else if (mView.getStopServiceButton() == (Button)v) {
			Log.i(TAG,"Click stop service");
			if (mIsBound)
				stop(StopOptions.ONLY_SERVICE);
		}
		else
			Log.i(TAG, "UNKNOWN");
	}
	
	
	//TODO: remove
	private void fakeExitedRegion() {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_EXITED_REGION);
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
		
	}
	
	//TODO:remove
	private void fakeEnteredRegion() {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_ENTERED_REGION);
		RectangularRegion aregion = new RectangularRegion(2048);
		msg.obj = aregion;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
		
	}
	
	
	public boolean isServiceRunning(){
		return mIsRunning;
	}
	
	public boolean isBound(){
		return mIsBound;
	}
	
		
	public void stop(StopOptions op){
		unbind();
		switch(op){
		case ALL:
			stopTheService();
			cleanUp();
			break;
		case ONLY_CONTROLLER:
			cleanUp();
			break;
			
		case ONLY_SERVICE:
			stopTheService();
			break;
			
		case ONLY_UNBIND:
		default:
			break;
		}
		
	}
	
	
	public void bind(){
		mServiceIntent = new Intent(mActivity, ControllerService.class);
		mActivity.startService(mServiceIntent); //to allow the service to keep running after the client unbinds 
		mActivity.bindService(mServiceIntent, mConnection,
				Context.BIND_AUTO_CREATE);		
	}
	
	

    /***********************************************************
	 * 
	 * MainController: auxiliary functions
	 * 
	 ***********************************************************/
	
		
	public void startButtonSubroutine() {
		mView.enableStart(false);
		mView.enableChangingFrequency(false);
		
	}
	
	public void stopButtonSubroutine() {
		mView.enableStart(true);
		mView.enableChangingFrequency(true);
	}
	
	private void processError(int errorcode) {
		switch(errorcode){
			case ERROR_LOCATION_PROVIDER_NOT_DETECTED:
				//TODO: unbind from service
				break;
			
			default:
				break;
		}
		
	}
	
	
	private void unbind(){
		if (mIsBound) {
			mActivity.unbindService(mConnection);
			mService = null;
			mIsBound = false;
		}
	}

	private void stopTheService() {
	    mActivity.stopService(mServiceIntent);
	    mIsRunning = false;
	}
	
	private void cleanUp(){
		mIncomingHandlerThread.getLooper().quit();
	}

}
