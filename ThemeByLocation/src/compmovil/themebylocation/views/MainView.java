package compmovil.themebylocation.views;

import compmovil.themebylocation.R;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainView extends View {
	
	private Activity activity;
	
	private Button startbutton;
	private Button unbindbutton;
	private Button stopservicebutton;
	private TextView infotextview;
	

	public MainView(Activity a) {
		super(a);
	
		activity = a;        
		activity.setContentView(R.layout.main_layout);

		startbutton = (Button) activity.findViewById(R.id.startbutton);
		unbindbutton = (Button) activity.findViewById(R.id.unbindbutton);
		stopservicebutton =	(Button) activity.findViewById(R.id.stopservicebutton);
		infotextview = (TextView) activity.findViewById(R.id.info_text);
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
	
	public void displayInfo(String name){
		infotextview.setText(name);
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
