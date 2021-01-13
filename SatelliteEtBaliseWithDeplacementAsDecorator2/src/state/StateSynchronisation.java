package state;

import deplacement.Deplacement;
import deplacement.balise.DeplRedescendre;
import deplacement.balise.DeplVersSurface;
import event.SatelliteMoved;
import event.SynchroEvent;
import model.Balise;
import model.Satelitte;

public class StateSynchronisation extends StateBalise {

	protected Balise balise;
	protected Deplacement originDepl;
	protected int profondeur;
	private int synchroTimeLeft;
	private int synchroTime;
	private Satelitte synchro;

	public Boolean synchroStarted() {
		return this.synchro != null;
	}

	public StateSynchronisation(Balise balise, int profondeur) {
		this.balise = balise;
		this.originDepl = balise.getDeplacement();
		this.profondeur = profondeur;
		this.synchroTime = 10;
		this.synchroTimeLeft = this.synchroTime;
		this.synchro = null;

		// On doit remonter à la surface pour se synchroniser
		this.balise.setDeplacement(new DeplVersSurface());
	}

	@Override
	public void handleState() {

		// On est à la surface donc on doit se synchroniser
		if (this.balise.profondeur() == 0) {
			this.synchro();
		}

		// On vérifie que la synchro n'est pas déjà finie
		if (synchroTimeLeft <= 0) {
			if (this.balise.profondeur() == 0) {
				// On doit redescendre
				this.synchroEnd();
			} else if (this.balise.profondeur() == this.profondeur) {
				// On est redescendu
				this.next();
			}
		}

	}

	public void synchro() {
		balise.getManager().checkSynchronisation(balise);

		if (this.synchro == null)
			return;
		this.synchroTimeLeft--;
		this.balise.addData(Math.round(this.balise.memorySize() / this.synchroTime) * -1);
	}

	public void synchroEnd() {
		Satelitte sat = this.synchro;
		this.balise.resetData();
		this.synchro = null;
		balise.send(new SynchroEvent(this));
		sat.send(new SynchroEvent(this));
		this.balise.setDeplacement(new DeplRedescendre(this.profondeur));
		balise.getManager().checkSynchroDone(balise);
	}

	@Override
	public void next() {
		this.synchroTimeLeft = 10;
		this.balise.setDeplacement(this.originDepl);
		this.balise.setState(new StateCollect(this.balise));
	}

	@Override
	public void whenSatelitteMoved(SatelliteMoved arg) {
		if (this.synchro != null)
			return;
		Satelitte sat = (Satelitte) arg.getSource();
		int satX = sat.getPosition().x;
		int tarX = balise.getPosition().x;
		if (satX > tarX - 10 && satX < tarX + 10) {
			this.synchro = sat;
			balise.send(new SynchroEvent(this));
			this.synchro.send(new SynchroEvent(this));
		}
	}

}
