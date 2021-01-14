package deplacement.balise;

import java.awt.Point;

import model.ElementMobile;

public class DeplVersSurface extends DeplBalise {

	public DeplVersSurface() {
	}

	@Override
	public void bouge(ElementMobile target) {
		Point p = target.getPosition();
		int y = p.y;
		if (y > 0) {
			y -= 3;
			if (y < 0)
				y = 0;
			target.setPosition(new Point(p.x, y));
		}
	}
}
