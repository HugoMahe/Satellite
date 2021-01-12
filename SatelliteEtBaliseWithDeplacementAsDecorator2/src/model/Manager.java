package model;

import java.util.HashSet;
import java.util.Set;

public class Manager {
	Set<ElementMobile> elMobs = new HashSet<>();

	public void addElement(ElementMobile elMob) {
		this.elMobs.add(elMob);
		elMob.setManager(this);
	}

	public void tick() {
		for (ElementMobile elMob : this.elMobs) {
			elMob.tick();
		}
	}

	public Set<ElementMobile> getElementsMobiles() {
		return elMobs;
	}

	public void setElementsMobiles(Set<ElementMobile> elMobs) {
		this.elMobs = elMobs;
	}

	public void checkSynchronisation(ElementMobile element) {
		for (ElementMobile elMob : this.getElementsMobiles()) {
			elMob.checkReceiverSynchro(element);
		}
	}

	public void checkSynchroDone(ElementMobile element) {

		for (ElementMobile elMob : this.getElementsMobiles()) {
			elMob.checkReceiverSynchroDone(element);
		}
	}
}
