package compmovil.themebylocation.models;

import android.location.Location;

public interface Region {

	public abstract boolean isInsideRegion(Location l);

}