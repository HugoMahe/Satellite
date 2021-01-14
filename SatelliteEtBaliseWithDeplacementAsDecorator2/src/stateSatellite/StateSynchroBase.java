package stateSatellite;

import deplacement.balise.DeplStandBy;
import model.BaseNavale;
import model.Satelitte;

public class StateSynchroBase extends StateSatellite {

	protected Satelitte sat;
	private BaseNavale synchro;
	private int synchroTimeLeft;
	private int synchroTime;
	
	public StateSynchroBase(Satelitte satParam) {
		
	}
	
	public boolean synchroStarted() {
		return this.synchro!=null;
	}

	@Override
	public void handleState() {
		// TODO Auto-generated method stub
		System.out.println("En attente d'une base navale");
		this.synchro();
		
		if(this.synchroTimeLeft <=0) {
			this.next();
		}
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		this.synchroTimeLeft=10;
		this.sat.setState(new StateCollectSatellite(this.sat));
		System.out.println("Retour en état de collect");
	}
	
	public void synchro() {
		this.sat.getManager().checkSynchronisationBaseNaval(sat);
		
		if(this.synchro==null)
			return;
		if(this.synchroTimeLeft == this.synchroTime) {
			this.sat.setDeplacement(new DeplStandBy());
		}
		
		this.synchroTime--;
		this.sat.addData(Math.round(this.sat.memorySize() / this.synchroTime) * -1);
		this.synchro.addData(Math.round(this.sat.memorySize() / this.synchroTime) * 1);
	}


}
