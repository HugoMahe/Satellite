package model;

import event.BaseNavaleAProximite;
import event.SatelliteMoved;
import listener.BaseNavaleAProximiteListener;
import state.StateCollect;
import stateSatellite.StateCollectSatellite;
import stateSatellite.StateSatellite;
import stateSatellite.StateSynchroBase;

public class Satelitte  extends ElementMobile implements BaseNavaleAProximiteListener {
	protected StateSatellite state;
	
	public Satelitte(int memorySize) {
		super(memorySize);
		this.setState(new StateCollectSatellite(this));
	}

	@Override
	public void bouge() {
		super.bouge();
		this.send(new SatelliteMoved(this));
	}
	
	@Override
	public void tick() {
		super.tick();
		this.state.handleState();
	}

	@Override
	public void checkReceiverSynchroDone(ElementMobile other) {
		other.checkSatelliteSynchroDone(this);
	}
	
	@Override
	public void checkReceiverSynchro(ElementMobile other) {
		if(!this.memoryFull()) {
			other.checkSatelliteSynchro(this);
		}
	}

	public void setState(StateSatellite stateParam) {
		if (stateParam != null) {
			this.state = stateParam;
		}
	}
	
	public void checkBaseNavaleSynchro(BaseNavale baseNavale) {
        this.registerListener(SatelliteMoved.class, baseNavale);
        baseNavale.registerListener(BaseNavaleAProximite.class, this);
    }
	
	public void checkBaseNavaleSynchroDone(BaseNavale baseNavale) {
        this.unregisterListener(SatelliteMoved.class, baseNavale);
        baseNavale.unregisterListener(BaseNavaleAProximite.class, this);
    }
	
	/*public void whenBaseNavaleAProximite(BaseNavaleAProximite arg) {
		this.state.whenBaseNavaleAProximite(arg);
	}*/

	@Override
	public void whenBaseNavaleAProximite(BaseNavaleAProximite arg, BaseNavaleAProximiteListener arg2) {
		this.state.whenBaseNavaleAProximite(arg, arg2);
	}
	
}
