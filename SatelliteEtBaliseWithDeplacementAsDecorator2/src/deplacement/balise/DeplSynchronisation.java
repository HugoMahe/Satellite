package deplacement.balise;

import deplacement.Deplacement;
import model.Balise;

public class DeplSynchronisation extends DeplBalise {

	public DeplSynchronisation(Deplacement next) {
		super(next);
	}

	@Override
	public void bouge(Balise target) {
		if (target.dataSize() == 0) {
			target.setDeplacement(this.next);
		}
	}
}
