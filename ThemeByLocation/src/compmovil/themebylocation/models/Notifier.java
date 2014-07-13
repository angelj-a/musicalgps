package compmovil.themebylocation.models;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import compmovil.themebylocation.ControllerService;

public class Notifier {
	
	private Messenger mServiceMessenger;

	public Notifier(Messenger messenger){
		mServiceMessenger = messenger;
	}
	
	public void notifyExitedARegion() {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_EXITED_REGION);
		try {
			mServiceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
	}
	
	public void notifyEnteredARegion(Region region) {
		Message msg = Message.obtain(null, ControllerService.DETECTOR_ENTERED_REGION);
		msg.obj = region;
		try {
			mServiceMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
}
