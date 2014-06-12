package compmovil.themebylocation.views;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import compmovil.themebylocation.R;



public class MainView extends View {
	
	private Activity activity;
	
	private Button startbutton;
	private Button stopbutton;
	private EditText frequency_edit;
	

	public MainView(Activity a) {
		super(a);
	
		activity = a;        
		activity.setContentView(R.layout.main_layout);

		startbutton = (Button) activity.findViewById(R.id.startbutton);
		stopbutton = (Button) activity.findViewById(R.id.stopbutton);
		frequency_edit = (EditText) activity.findViewById(R.id.track_freq);
	}

	
	
	public void enableChangingFrequency(boolean enabled) {
		frequency_edit.setEnabled(enabled);
	}
	
	public void enableStart(boolean enabled){
		startbutton.setEnabled(enabled);
	}
	
	public String getFrequency(){
		return frequency_edit.getText().toString();
	}
	
	
	public Button getStartButton() {
		return startbutton;
	}	
	
	public Button getStopButton() {
		return stopbutton;
	}
	
	
}
