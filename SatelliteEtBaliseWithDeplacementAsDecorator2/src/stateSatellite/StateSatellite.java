package stateSatellite;

import event.BaseNavaleAProximite;
import listener.BaseNavaleAProximiteListener;

public abstract class StateSatellite {
	
	public abstract void handleState();

	public abstract void next();
	
	public  void  whenBaseNavaleAProximite(BaseNavaleAProximite arg, BaseNavaleAProximiteListener arg2) {
	}

}
