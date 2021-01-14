package listener;

import event.SynchroBaseEvent;

public interface SynchroBaseEventListener {

	public void whenStartBaseSynchro(SynchroBaseEvent arg);
	public void whenStopBaseSynchro(SynchroBaseEvent arg);
}
