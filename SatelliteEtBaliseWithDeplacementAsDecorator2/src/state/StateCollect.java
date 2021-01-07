package state;

import model.Balise;

public class StateCollect implements StateBalise {

	protected Balise balise;

	public StateCollect(Balise balise) {
		this.balise = balise;
	}

	@Override
	public void handleState() {
		this.balise.readSensors();
	}

}
