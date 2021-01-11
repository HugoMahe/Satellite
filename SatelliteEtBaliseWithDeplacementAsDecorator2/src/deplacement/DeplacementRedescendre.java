package deplacement;

import java.awt.Point;

import model.Balise;

public class DeplacementRedescendre extends DeplacementBalise {
	int profondeur;
	
	public DeplacementRedescendre(Deplacement next, int profondeur) {
		super (next);
		this.profondeur = profondeur;
	}
	
	@Override
	public void bouge(Balise target) {
		Point p = target.getPosition();
		int y = p.y;
		if (y < this.profondeur) {
			y += 3;
			if (y > this.profondeur) y = this.profondeur;
			target.setPosition(new Point(p.x, y));
		}  else {
			target.setDeplacement(next);
		}
	}

}
