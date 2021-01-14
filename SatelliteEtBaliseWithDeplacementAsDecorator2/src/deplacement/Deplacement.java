package deplacement;

import model.ElementMobile;

public abstract class Deplacement {
	abstract public void bouge(ElementMobile target) ;
	public Deplacement replacement() { return this; }
}
