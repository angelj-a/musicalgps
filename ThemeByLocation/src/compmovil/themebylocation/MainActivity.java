package compmovil.themebylocation;


import android.app.Activity;
import android.os.Bundle;

import compmovil.themebylocation.controllers.MainController;
import compmovil.themebylocation.views.MainView;

public class MainActivity extends Activity {
	
	MainController maincontroller;
	MainView mainview;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainview = new MainView(this);
        maincontroller = new MainController(this, mainview);
               
        // Bind controller to click
        //findViewById(R.id.clickId).setOnClickListener(controller);
        
    }

}
