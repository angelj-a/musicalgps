package compmovil.themebylocation.views;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import compmovil.themebylocation.R;



public class MainView extends View {
	
	private Activity activity;
	
	private Button startbutton;
	private Button unbindbutton;
	private Button stopservicebutton;
	

	public MainView(Activity a) {
		super(a);
	
		activity = a;        
		activity.setContentView(R.layout.main_layout);

		startbutton = (Button) activity.findViewById(R.id.startbutton);
		unbindbutton = (Button) activity.findViewById(R.id.unbindbutton);
		stopservicebutton =	(Button) activity.findViewById(R.id.stopservicebutton);
	}

	
	
	public void enableStartButton(boolean enabled){
		startbutton.setEnabled(enabled);
	}
	
	public void enableUnbindButton(boolean enabled){
		unbindbutton.setEnabled(enabled);
	}
	
	public void enableStopserviceButton(boolean enabled){
		stopservicebutton.setEnabled(enabled);
	}
	
	//TODO:
	public void displayRegion(String name){
		
	}
	

	/* ------------------------------
	 * Methods used to get a button 
	 * ------------------------------ */
	
	public Button getStartButton() {
		return startbutton;
	}	
	
	public Button getUnbindButton() {
		return unbindbutton;
	}

	public Button getStopServiceButton() {
		return stopservicebutton;
	}
	
	
}
