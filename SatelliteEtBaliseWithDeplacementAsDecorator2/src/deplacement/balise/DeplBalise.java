package deplacement.balise;

import deplacement.Deplacement;
import model.Balise;
import model.ElementMobile;

public class DeplBalise extends Deplacement {

	public void bouge(Balise target) {
	}

	@Override
	public void bouge(ElementMobile target) {
		this.bouge((Balise) target);
	}

}
