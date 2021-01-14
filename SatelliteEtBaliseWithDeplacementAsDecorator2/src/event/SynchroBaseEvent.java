package event;

import eventHandler.AbstractEvent;
import listener.SynchroBaseEventListener;
import stateSatellite.StateSynchroBase;

public class SynchroBaseEvent  extends AbstractEvent {
	private static final long serialVersionUID = 3992713354796294022L;

	public SynchroBaseEvent(Object source) {
		super(source);
	}
	
	@Override
	public void runOn(Object target) {
		SynchroBaseEventListener listener = (SynchroBaseEventListener) target;
		StateSynchroBase state = (StateSynchroBase) this.getSource();
		if(state.synchroStarted())
			listener.whenStartBaseSynchro(this);
		else
			listener.whenStopBaseSynchro(this);
	}

}
