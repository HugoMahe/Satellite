package deplacement.balise;

import deplacement.Deplacement;
import elements.Balise;
import elements.ElementMobile;
import event.SatelitteMoved;

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

	public void whenSatelitteMoved(SatelitteMoved arg, Balise target) { }

}
