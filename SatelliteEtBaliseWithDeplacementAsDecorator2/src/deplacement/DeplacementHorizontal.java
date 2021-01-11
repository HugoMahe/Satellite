package deplacement;

import java.awt.Point;

import model.ElementMobile;

public class DeplacementHorizontal extends DeplacementBalise {
	Integer start;
	Integer end;
	Boolean fromStartToEnd = true;

	public DeplacementHorizontal(Integer start, Integer end) {
		super (null);
		this.start = start;
		this.end = end;
	}
	
	@Override
	public void bouge(ElementMobile target) {
		Point p = target.getPosition();
		int x = p.x;
		if (fromStartToEnd) {
			x += 6;
			if (x > end) fromStartToEnd = false;
		} else {
			x -= 2;
			if (x < start) fromStartToEnd = true;
		}
		target.setPosition(new Point(x, p.y));
	}

}
