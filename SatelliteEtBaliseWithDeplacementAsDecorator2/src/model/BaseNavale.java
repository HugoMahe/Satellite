package model;

import java.util.List;
import java.util.stream.Collectors;

public class BaseNavale {
	protected Manager manager;

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public void controle() {

		if (manager == null) {
			return;
		}

		for (ElementMobile elMob : manager.getElementsMobiles()) {
			for (ElementMobile elMob2 : manager.getElementsMobiles()) {
				elMob2.isPartOfReceiverRange(elMob);
			}
		}

		List<ElementMobile> balises = manager.getElementsMobiles().stream().filter(b -> b instanceof Balise)
				.collect(Collectors.toList());

		List<ElementMobile> satellites = manager.getElementsMobiles().stream().filter(s -> s instanceof Satelitte)
				.collect(Collectors.toList());

//		for (ElementMobile b : balises) {
////			Balise balise = (Balise) b;
//			HashSet<Satelitte> satInRange = new HashSet<>();
//
//			for (ElementMobile s : satellites) {
//
////				Satelitte satellite = (Satelitte) s;
//				int bX = b.getPosition().x;
//				int sX = s.getPosition().x;
//
//				if (sX >= bX - 50 && sX <= bX + 50) {
//					// Emettre un evenement pour que la balise enregistre le satellite
//					System.out.println(b);
//					System.out.println(s);
//
//				}
//			}
	}

}
