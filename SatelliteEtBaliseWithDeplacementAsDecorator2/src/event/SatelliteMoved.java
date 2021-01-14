package event;

import eventHandler.AbstractEvent;
import listener.SatelliteMoveListener;

public class SatelliteMoved extends AbstractEvent {
	private static final long serialVersionUID = 480096146703824993L;

	public SatelliteMoved(Object source) {
		super(source);
	}

	public void runOn(Object target) {
		SatelliteMoveListener listener = (SatelliteMoveListener) target;
		listener.whenSatelitteMoved(this);
	}
}