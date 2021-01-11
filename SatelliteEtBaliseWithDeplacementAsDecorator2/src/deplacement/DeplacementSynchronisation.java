package deplacement;

import event.SatelitteMoved;
import event.SynchroEvent;
import model.Balise;
import model.Satelitte;

public class DeplacementSynchronisation extends DeplacementBalise {
	private int synchroTime;
	private Satelitte synchro;
	
	public Boolean synchroStarted() {
		return this.synchro != null;
	}
	
	public DeplacementSynchronisation(Deplacement next) {
		super(next);
		this.synchroTime = 10;
		this.synchro = null;
	}
	
	@Override
	public void whenSatelitteMoved(SatelitteMoved arg, Balise target) {
		if (this.synchro != null) return;
		Satelitte sat = (Satelitte) arg.getSource();
		int satX = sat.getPosition().x;
		int tarX = target.getPosition().x;
		if (satX > tarX - 10 && satX < tarX + 10) {
			this.synchro = sat;
			target.send(new SynchroEvent(this));
			this.synchro.send(new SynchroEvent(this));
		}
	}

	@Override
	public void bouge(Balise target) {
		System.out.println("Lancement deplacement synchro");
		if (this.synchro == null) return;
		this.synchroTime--;
		if (synchroTime <= 0) {
			Satelitte sat = this.synchro;
			this.synchro = null;
			this.synchroTime = 10;
			target.send(new SynchroEvent(this));
			sat.send(new SynchroEvent(this));
			target.getManager().baliseSynchroDone(target);
			target.setDeplacement(next);
		}		
	}
}
