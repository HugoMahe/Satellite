package state;

import model.Balise;

public class StateCollect extends StateBalise {

	protected Balise balise;

	public StateCollect(Balise balise) {
		this.balise = balise;
	}

	@Override
	public void handleState() {
		this.balise.readSensors();
		if (this.balise.memoryFull()) {
			this.next();
		}
	}

	@Override
	public void next() {
		this.balise.setState(new StateSynchronisation(this.balise, this.balise.getPosition().y));
	}

}
