package deplacement;

import java.awt.Point;

import model.ElementMobile;

public class DeplacementSatellite extends Deplacement {
	Integer start;
	Integer end;
	int vitesse;

	public DeplacementSatellite(Integer start, Integer end, int vitesse) {
		this.start = start;
		this.end = end;
		this.vitesse = vitesse;
	}
	
	@Override
	public void bouge(ElementMobile target) {
		Point p = target.getPosition();
		int x = p.x;
		x += vitesse;
		if (x > end) x = start;
		target.setPosition(new Point(x, p.y));
	}

}
