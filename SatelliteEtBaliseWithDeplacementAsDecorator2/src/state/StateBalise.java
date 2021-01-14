package state;

import event.SatelliteMoved;

public abstract class StateBalise {

	public abstract void handleState();

	public abstract void next();

	public void whenSatelitteMoved(SatelliteMoved arg) {
	}

}
