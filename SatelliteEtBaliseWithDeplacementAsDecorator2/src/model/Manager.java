package model;

import java.util.HashSet;
import java.util.Set;

public class Manager {
	Set<ElementMobile> elMobs = new HashSet<>();

	public void addElement(ElementMobile elMob) {
		this.elMobs.add(elMob);
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

	public void controle() {
		for (ElementMobile elMob : this.getElementsMobiles()) {
			for (ElementMobile elMob2 : this.getElementsMobiles()) {
				elMob2.isPartOfReceiverRange(elMob);
			}
		}
	}
}
