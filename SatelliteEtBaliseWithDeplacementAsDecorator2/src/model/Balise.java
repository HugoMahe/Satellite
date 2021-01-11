package model;

import deplacement.Deplacement;
import deplacement.DeplacementBalise;
import deplacement.DeplacementRedescendre;
import deplacement.DeplacementSynchronisation;
import event.SatelitteMoved;
import deplacement.DeplacementSurfacePourSynchro;
import modelListener.SatelitteMoveListener;

public class Balise extends ElementMobile implements SatelitteMoveListener{
	
	private boolean synchronisation = false;
	
	public Balise(int memorySize) {
		super(memorySize);
	}
	
	public int profondeur() { 
		return this.getPosition().y; 
	}
	
	protected void readSensors() {
		this.dataSize++;
	}
	
	public void tick() {
		if(this.profondeur()!=0 && !(this.getSynchronisation())) {
			this.readSensors();
		}
		System.out.println(this.dataSize);
		if (this.memoryFull()) {
			this.setSynchronisation(true);
			Deplacement redescendre = new DeplacementRedescendre(this.deplacement(), this.profondeur());
			Deplacement deplSynchro = new DeplacementSynchronisation(redescendre);
			Deplacement nextDepl = new DeplacementSurfacePourSynchro(deplSynchro);
			this.setDeplacement(nextDepl);
			this.resetData();
		}
		super.tick();
	}

	@Override
	public void whenSatelitteMoved(SatelitteMoved arg) {
		DeplacementBalise dp = (DeplacementBalise) this.depl;
		dp.whenSatelitteMoved(arg, this);
	}

	public boolean getSynchronisation() {
		return synchronisation;
	}

	public void setSynchronisation(boolean synchronisation) {
		this.synchronisation = synchronisation;
	}


}
