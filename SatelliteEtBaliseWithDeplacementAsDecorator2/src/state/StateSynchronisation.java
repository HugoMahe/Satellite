package state;

import event.SatelliteMoved;
import event.SynchroEvent;
import model.Balise;
import model.Satelitte;

public class StateSynchronisation extends StateBalise {

	protected Balise balise;
	protected int profondeur;
	private int synchroTimeLeft;
	private int synchroTime;
	private Satelitte synchro;

	public Boolean synchroStarted() {
		return this.synchro != null;
	}

	public StateSynchronisation(Balise balise, int profondeur) {
		this.balise = balise;
		this.profondeur = profondeur;
		this.synchroTime = 10;
		this.synchroTimeLeft = this.synchroTime;
		this.synchro = null;
	}

	@Override
	public void handleState() {
		balise.getManager().checkSynchronisation(balise);

		if (this.synchro == null)
			return;
		this.synchroTimeLeft--;
		this.balise.addData(Math.round(this.balise.memorySize() / this.synchroTime) * -1);

		if (synchroTimeLeft <= 0) {
			Satelitte sat = this.synchro;
			this.synchro = null;
			this.synchroTimeLeft = 10;
			this.balise.resetData();
			balise.getManager().checkSynchroDone(balise);
			balise.send(new SynchroEvent(this));
			sat.send(new SynchroEvent(this));
			this.next();
		}
	}

	@Override
	public void next() {
		this.balise.setState(new StatePlonge(this.balise, this.profondeur));
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
