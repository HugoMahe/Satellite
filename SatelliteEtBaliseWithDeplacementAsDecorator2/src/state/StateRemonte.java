package state;

import deplacement.Deplacement;
import deplacement.balise.DeplRedescendre;
import deplacement.balise.DeplSynchronisation;
import deplacement.balise.DeplVersSurface;
import model.Balise;

public class StateRemonte extends StateBalise {

	protected Balise balise;
	protected int profondeur;

	public StateRemonte(Balise balise, int profondeur) {
		this.balise = balise;
		this.profondeur = profondeur;
		Deplacement redescendre = new DeplRedescendre(balise.deplacement(), balise.profondeur());
		Deplacement deplSynchro = new DeplSynchronisation(redescendre);
		Deplacement nextDepl = new DeplVersSurface(deplSynchro);
		balise.setDeplacement(nextDepl);
	}

	@Override
	public void handleState() {
		if (this.balise.getPosition().y <= 0) {
			this.next();
		}
	}

	@Override
	public void next() {
		this.balise.setState(new StateSynchronisation(this.balise, this.profondeur));
	}

}
