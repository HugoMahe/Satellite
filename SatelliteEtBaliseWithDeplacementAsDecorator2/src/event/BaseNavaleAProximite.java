package event;

import eventHandler.AbstractEvent;
import listener.BaseNavaleAProximiteListener;

public class BaseNavaleAProximite extends AbstractEvent{

	private static final long serialVersionUID = -4370138242791280128L;

	public BaseNavaleAProximite(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runOn(Object target) {
		// TODO Auto-generated method stub
		BaseNavaleAProximiteListener listener = (BaseNavaleAProximiteListener) target;
		listener.whenBaseNavaleAProximite(this, listener);
	}

}
