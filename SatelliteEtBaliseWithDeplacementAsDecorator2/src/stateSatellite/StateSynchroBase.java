package stateSatellite;

import deplacement.DeplStandBy;
import event.BaseNavaleAProximite;
import listener.BaseNavaleAProximiteListener;
import model.BaseNavale;
import model.Satelitte;

public class StateSynchroBase extends StateSatellite  {

	protected Satelitte sat;
	private BaseNavale synchro;
	
	public StateSynchroBase(Satelitte satParam) {
		this.sat=satParam;
	}
	
	public boolean synchroStarted() {
		return this.synchro!=null;
	}

	@Override
	public void handleState() {
		// TODO Auto-generated method stub
		this.synchro();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		this.sat.getManager().checkSynchroDone(sat);
		this.sat.setState(new StateCollectSatellite(this.sat));
	}
	
	public void synchro() {
		this.sat.getManager().checkSynchronisation(sat);
		/*if(this.synchro==null)
			return;
		if(this.synchroTimeLeft == this.synchroTime) {
			this.sat.setDeplacement(new DeplStandBy());
		}
		
		this.synchroTime--;
		this.sat.addData(Math.round(this.sat.memorySize() / this.synchroTime) * -1);
		this.synchro.addData(Math.round(this.sat.memorySize() / this.synchroTime) * 1);*/
	}

	@Override
	public void whenBaseNavaleAProximite(BaseNavaleAProximite arg, BaseNavaleAProximiteListener arg2) {
		BaseNavale bn = (BaseNavale) arg.getSource();
		System.out.println(this.sat.equals(arg2));
		if(this.sat.equals(arg2)) {
			bn.addData(this.sat.dataSize());
			this.sat.resetData();
			this.next();
		}
	}

}
