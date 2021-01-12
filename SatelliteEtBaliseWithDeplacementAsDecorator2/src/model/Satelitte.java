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
	public void isPartOfReceiverRange(ElementMobile other) {
		other.isPartOfSatelliteRange(this);
	}

}
