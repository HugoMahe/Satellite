package model;

import java.util.HashSet;
import java.util.Set;

public class Manager {
	Set<ElementMobile> elMobs = new HashSet<>();
	BaseNavale base;

	public void addElement(ElementMobile elMob) {
		this.elMobs.add(elMob);
		elMob.setManager(this);
	}

	public void tick() {
		for (ElementMobile elMob : this.elMobs) {
			elMob.tick();
		}
	}
	
	public void setBaseNavale(BaseNavale baseParam) {
		this.base=baseParam;
	}
	
	public BaseNavale getBaseNavale() {
		return this.base;
	}
//
//	public Set<ElementMobile> getElementsMobiles() {
//		return elMobs;
//	}
//
//	public void setElementsMobiles(Set<ElementMobile> elMobs) {
//		this.elMobs = elMobs;
//	}

	public void checkSynchronisation(ElementMobile element) {
		for (ElementMobile elMob : this.elMobs) {
			elMob.checkReceiverSynchro(element);
		}
	}

	public void checkSynchroDone(ElementMobile element) {

		for (ElementMobile elMob : this.elMobs) {
			elMob.checkReceiverSynchroDone(element);
		}
	}

	
	public void checkSynchronisationBaseNaval(Satelitte sat) {
		this.base.checkReceiverSynchro(sat);
	}
}
