package elements;

import deplacement.Deplacement;
import deplacement.balise.DeplBalise;
import deplacement.balise.DeplSynchronisation;
import deplacement.balise.DeplVersSurface;
import deplacement.balise.DeplRedescendre;
import event.SatelliteMoved;
import listener.SatelliteMoveListener;

public class Balise extends ElementMobile implements SatelliteMoveListener {

	protected boolean sync;

	public Balise(int memorySize) {
		super(memorySize);
		sync = false;
	}

	public int profondeur() {
		return this.getPosition().y;
	}

	protected void readSensors() {
		this.dataSize++;
	}

	@Override
	public void tick() {
		System.out.println(this.dataSize);
		if (this.memoryFull()) {
			this.sync = true;
			Deplacement redescendre = new DeplRedescendre(this.deplacement(), this.profondeur());
			Deplacement deplSynchro = new DeplSynchronisation(redescendre);
			Deplacement nextDepl = new DeplVersSurface(deplSynchro);
			this.setDeplacement(nextDepl);
			this.resetData();
		}
		if (!sync) {
			this.readSensors();
		}
		super.tick();
	}

	public void unsync() {
		this.sync = false;
	}

	@Override
	public void whenSatelitteMoved(SatelliteMoved arg) {
		DeplBalise dp = (DeplBalise) this.depl;
		dp.whenSatelitteMoved(arg, this);
	}

}
