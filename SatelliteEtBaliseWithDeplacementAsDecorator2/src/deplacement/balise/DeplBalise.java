package deplacement.balise;

import deplacement.Deplacement;
import event.SatelliteMoved;
import model.Balise;
import model.ElementMobile;

public class DeplBalise extends Deplacement {

	protected Deplacement next;
	
	public DeplBalise (Deplacement next) {
		this.next = next;
	}
	
	public void bouge(Balise target) {
	}

	@Override
	public void bouge(ElementMobile target) {
		this.bouge((Balise) target);
	}

	public void whenSatelitteMoved(SatelliteMoved arg, Balise target) { }

}
