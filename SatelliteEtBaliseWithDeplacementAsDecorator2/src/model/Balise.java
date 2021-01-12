package model;

import event.SatelliteMoved;
import listener.SatelliteMoveListener;
import state.StateBalise;
import state.StateCollect;

public class Balise extends ElementMobile implements SatelliteMoveListener {

	protected StateBalise state;

	public Balise(int memorySize) {
		super(memorySize);
		this.setState(new StateCollect(this));
	}

	public int profondeur() {
		return this.getPosition().y;
	}

	public void readSensors() {
		this.dataSize++;
	}

	@Override
	public void tick() {
		if (this.memoryFull()) {

		}
		super.tick();
		this.state.handleState();
	}

	@Override
	public void whenSatelitteMoved(SatelliteMoved arg) {
		StateBalise state = this.state;
		state.whenSatelitteMoved(arg);
	}

	public void setState(StateBalise state) {
		if (state != null) {
			this.state = state;
		}
	}

	@Override
	public void checkSatelliteSynchro(Satelitte satellite) {
		satellite.registerListener(SatelliteMoved.class, this);
	}

	@Override
	public void checkSatelliteSynchroDone(Satelitte satellite) {
		satellite.unregisterListener(SatelliteMoved.class, this);
	}

}
