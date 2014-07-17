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

import compmovil.themebylocation.ControllerService;
import compmovil.themebylocation.R;
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
	public final static int UPDATE_CURRENT_REGION = 1;
	public final static int ERROR = 2;
	public final static int ERROR_LOCATION_PROVIDER_NOT_DETECTED = 0;
	public final static int ERROR_FAILED_INITIALIZING_MUSIC_PLAYER = 1;
	

	//Context
	private Activity mActivity;
	private MainView mView;

    private Messenger mMyMessenger;
    
    private Messenger mService;
    private HandlerThread mIncomingHandlerThread;
    
    private boolean mIsBound = false;
    private boolean mIsRunning = false;

    
    private Intent mServiceIntent;
    
    
    private String mInfo;
    
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
            case UPDATE_CURRENT_REGION: //TODO
            	mInfo = msg.getData().getString("region_name");
                mActivity.runOnUiThread(mRunnableUpdateInfo); 
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
        @Override
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

        @Override
		public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
            mService = null;
            mIsBound = false;
            Log.i(TAG,"Desconectado del service");
        }
    };   
    
    private Runnable mRunnableUpdateInfo = new Runnable() { 
        @Override
		public void run() 
        {
        	Log.i(TAG,"info = " + mInfo);
        	mView.displayInfo(mInfo); 
        } 
    };
    
    private Runnable mRunnableBackToInitialUIState  = new Runnable(){
    	@Override
		public void run()
    	{
    		enableBindingOnView(true);
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
        mActivity.findViewById(R.id.unbindbutton).setOnClickListener(this);
        mActivity.findViewById(R.id.stopservicebutton).setOnClickListener(this);
        
        enableBindingOnView(true);
        
    }
	
	@Override
	public void onClick(View v) {
		if (mView.getStartButton() == (Button)v){
			Log.i(TAG, "Click iniciar");
			if (!mIsBound){
				// Bind to service (if possible)
				try {
					bind();
					enableBindingOnView(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if (mView.getUnbindButton() == (Button)v){
			Log.i(TAG,"Click bind");
			unbind();	
			enableBindingOnView(true);
		}
		else if (mView.getStopServiceButton() == (Button)v) {
			Log.i(TAG,"Click stop service");
			if (mIsBound) {
				stop(StopOptions.ONLY_SERVICE);
				enableBindingOnView(true);
				mInfo = "";
				mView.displayInfo(mInfo);
			}
		}
		else
			Log.i(TAG, "UNKNOWN");
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
	
	//TODO:completar
	private void processError(int errorcode) {
		switch(errorcode){
			case ERROR_LOCATION_PROVIDER_NOT_DETECTED:
            	mInfo = "Servicio de geolocalización no disponible";
                mActivity.runOnUiThread(mRunnableUpdateInfo); 
				break;
			
			default:
				break;
		}
		stop(StopOptions.ONLY_SERVICE);
		mActivity.runOnUiThread(mRunnableBackToInitialUIState);
	}

	private void enableBindingOnView(boolean enable){
		mView.enableStartButton(enable);
		mView.enableUnbindButton(!enable);
		mView.enableStopserviceButton(!enable);
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
