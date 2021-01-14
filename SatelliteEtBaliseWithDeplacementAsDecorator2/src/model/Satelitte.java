package model;

import event.SatelliteMoved;

public class Satelitte extends ElementMobile {
	
	public Satelitte(int memorySize) {
		super(memorySize);
	}

	@Override
	public void bouge() {
		super.bouge();
		this.send(new SatelliteMoved(this));
	}

	@Override
	public void checkReceiverSynchro(ElementMobile other) {
		other.checkSatelliteSynchro(this);
	}

	@Override
	public void checkReceiverSynchroDone(ElementMobile other) {
		other.checkSatelliteSynchroDone(this);
	}
}
