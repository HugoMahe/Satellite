package event;

import eventHandler.AbstractEvent;
import listener.SynchroEventListener;
import state.StateSynchronisation;

public class SynchroEvent extends AbstractEvent {
	private static final long serialVersionUID = 480096146703824993L;

	public SynchroEvent(Object source) {
		super(source);
	}

	@Override
	public void runOn(Object target) {
		SynchroEventListener listener = (SynchroEventListener) target;
		StateSynchronisation state = (StateSynchronisation) this.getSource();
		if (state.synchroStarted())
			listener.whenStartSynchro(this);
		else
			listener.whenStopSynchro(this);
	}
}
