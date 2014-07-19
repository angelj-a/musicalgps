package compmovil.themebylocation.models.mocks;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

public interface LocationClientCallbackInterface {
	
	public void onConnectionFailed(ConnectionResult result);

	public void onConnected(Bundle connectionHint);

	public void onDisconnected();

}
