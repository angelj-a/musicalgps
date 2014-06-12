package compmovil.themebylocation.controllers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
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
	
	final static String TAG = "THEMELOCATION";
	
	//Options implemented by MainController.IncomingHandler 
	public final static int MSG_1 = 1;
	public final static int MSG_2 = 2;
	
	//Context
	private Activity mActivity;
	private MainView mView;

    private Messenger mMyMessenger;
    
    private Messenger mService;
    private HandlerThread mServiceThread;
    
    private boolean mIsBound = false;

    
	//private final Intent mLoggingServiceIntent = new Intent("compmovil.themebylocation.ControllerService");
    

	
    class IncomingHandler extends Handler {
    	
    	public IncomingHandler(Looper looper){
    		super(looper);
    	}
    	
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_1: //TODO
                break;
            case MSG_2: //TODO
                //String str1 = msg.getData().getString("str1");
                //textStrValue.setText("Str Message: " + str1);
                break;
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
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
            mService = null;
            mIsBound = false;
        }
    };
	

	public MainController(Activity context, MainView v) {
		mActivity = context;
		mView = v;
		
		// Start a new thread to handle incoming messages from ControllerService
		mServiceThread = new HandlerThread("[MainController] IncomingHandler");
		mServiceThread.start();
		
		mMyMessenger = new Messenger(new IncomingHandler(mServiceThread.getLooper()));
		
        mActivity.findViewById(R.id.startbutton).setOnClickListener(this);
        mActivity.findViewById(R.id.stopbutton).setOnClickListener(this);
    }
	
	@Override
	public void onClick(View v) {
		if (mView.getStartButton() == (Button)v){
			Log.i(TAG, "Click start");
			if (!("".equals(mView.getFrequency()) )) {
				startButtonSubroutine();
				Log.i(TAG, "frecuencia = " + mView.getFrequency());
			}
			if (!mIsBound){
				// Bind to service (if possible)
				try {
					mActivity.bindService(new Intent(mActivity, ControllerService.class), mConnection,
							Context.BIND_AUTO_CREATE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				// Create Message 
				Message msg = Message.obtain(null, ControllerService.CLIENT_MSG_OP1);
				Bundle bundle = new Bundle();
				bundle.putString("KEY", "Mensaje desde el cliente");
				msg.setData(bundle);

				try {
					// Send message to ControllerService
					mService.send(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}				
			}
		}
		else if (mView.getStopButton() == (Button)v){
			Log.i(TAG, "Click stop");
			stopButtonSubroutine();
		}
		else 
			Log.i(TAG, "UNKNOWN");
	}
	
	
		
	public void startButtonSubroutine() {
		mView.enableStart(false);
		mView.enableChangingFrequency(false);
		
	}
	
	public void stopButtonSubroutine() {
		mView.enableStart(true);
		mView.enableChangingFrequency(true);
	}		

}
