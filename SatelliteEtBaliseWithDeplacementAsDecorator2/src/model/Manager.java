package model;

import java.util.ArrayList;

public class Manager {
	ArrayList<Satelitte> sats = new ArrayList<Satelitte>();
	ArrayList<Balise> bals = new ArrayList<Balise>();
	public void addBalise(Balise bal) {
		bals.add(bal);
		bal.setManager(this);
	}
	public void addSatellite(Satelitte sat) {
		this.sats.add(sat);
		sat.setManager(this);
	}
	public void tick() {
		for (Balise b : this.bals) {
			b.tick();
		}
		for (Satelitte s : this.sats) {
			s.tick();
		}
	}
	
	public void baliseReadyForSynchro(Balise b) {
		for (Satelitte s : this.sats) {			
			s.registerListener(SatelitteMoved.class, b);
		}
	}
	public void baliseSynchroDone(Balise b) {
		for (Satelitte s : this.sats) {			
			s.unregisterListener(SatelitteMoved.class, b);
		}
	}

}
