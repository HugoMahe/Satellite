package model;

import event.SatelliteMoved;

public class Satelitte extends ElementMobile {
			
	public Satelitte(int memorySize) {
		super(memorySize);
	}
	
	public void bouge () {
		super.bouge();
		this.send(new SatelliteMoved(this));		
	}
}
