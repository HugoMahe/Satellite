package state;

import model.Balise;

public class StateSync implements StateBalise {

	protected Balise balise;
	protected int profondeur;

	public StateSync(Balise balise, int profondeur) {
		this.balise = balise;
		this.profondeur = profondeur;
	}

	@Override
	public void handleState() {
		if (this.balise.getPosition().y >= this.profondeur) {
			this.next();
		}
	}

	@Override
	public void next() {
		this.balise.setState(new StateCollect(this.balise));
	}

}
