package model;

import deplacement.Deplacement;
import deplacement.balise.DeplBalise;
import deplacement.balise.DeplRedescendre;
import deplacement.balise.DeplSynchronisation;
import deplacement.balise.DeplVersSurface;
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
		System.out.println(this.dataSize);
		if (this.memoryFull()) {
			Deplacement redescendre = new DeplRedescendre(this.deplacement(), this.profondeur());
			Deplacement deplSynchro = new DeplSynchronisation(redescendre);
			Deplacement nextDepl = new DeplVersSurface(deplSynchro);
			this.setDeplacement(nextDepl);
			this.resetData();
		}
		super.tick();
		this.state.handleState();
	}

	@Override
	public void whenSatelitteMoved(SatelliteMoved arg) {
		DeplBalise dp = (DeplBalise) this.depl;
		dp.whenSatelitteMoved(arg, this);
	}
	
	public void setState(StateBalise state) {
		if (state != null) {
			this.state = state;
		}
	}

}
