package event;

import deplacement.DeplacementSynchronisation;
import eventHandler.AbstractEvent;
import modelListener.SynchroEventListener;

public class SynchroEvent extends AbstractEvent {
	private static final long serialVersionUID = 480096146703824993L;

	public SynchroEvent(Object source) {
		super(source);
	}

	public void runOn(Object target) {
		SynchroEventListener listener = (SynchroEventListener) target;
		DeplacementSynchronisation depl = (DeplacementSynchronisation) this.getSource();
		if (depl.synchroStarted())
			listener.whenStartSynchro(this);
		else 
			listener.whenStopSynchro(this);
	}
}
