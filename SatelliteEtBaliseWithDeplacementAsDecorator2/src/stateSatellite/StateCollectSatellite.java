package stateSatellite;

import model.Satelitte;

public class StateCollectSatellite extends StateSatellite {

	protected Satelitte sat;
	
	public StateCollectSatellite(Satelitte satParam) {
		// TODO Auto-generated constructor stub
		this.sat = satParam;
	}
	
	@Override
	public void handleState() {
		// TODO Auto-generated method stub
		System.out.println("VALEUR MEMOIRE "  + this.sat.memoryFull());
		if(this.sat.memoryFull()) {
			this.next();
		}
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		this.sat.setState(new StateSynchroBase(this.sat));
		System.out.println("State synchro base done");
	}

}
