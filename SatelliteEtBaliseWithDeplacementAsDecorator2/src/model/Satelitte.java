package model;

import event.SatelliteMoved;
import state.StateCollect;
import stateSatellite.StateCollectSatellite;
import stateSatellite.StateSatellite;
import stateSatellite.StateSynchroBase;

public class Satelitte extends ElementMobile {
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
	public void checkReceiverSynchro(ElementMobile other) {
		if(this.state instanceof StateCollectSatellite) {
			other.checkSatelliteSynchro(this);
		}
	}

	@Override
	public void checkReceiverSynchroDone(ElementMobile other) {
		other.checkSatelliteSynchroDone(this);
	}

	public void setState(StateSatellite stateParam) {
		// TODO Auto-generated method stub
		if (stateParam != null) {
			this.state = stateParam;
		}
	}
}
