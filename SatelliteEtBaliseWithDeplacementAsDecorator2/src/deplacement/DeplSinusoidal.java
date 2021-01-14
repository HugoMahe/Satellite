package deplacement;

import java.awt.Point;

import deplacement.balise.DeplBalise;
import model.ElementMobile;

public class DeplSinusoidal extends DeplBalise{

	
	Integer minHorizontal;
	Integer maxHorizontal;
	Integer direction=1;
	Integer delta = 2;
	private double count;
	
	
	public DeplSinusoidal(Integer minHorizontal, Integer maxHorizontal) {
		super();
		this.minHorizontal=minHorizontal;
		this.maxHorizontal=maxHorizontal;
	}
	
	// UPDATE LE X
	@Override
	public void bouge(ElementMobile target) {
		// RECUPERATION DE LA POSITION ACTUELLE
		Point p = target.getPosition();
		
		// GESTION DU X
		int x = p.x;
		if(target.getPosition().x >= maxHorizontal -10) {
			this.direction = -1;
		} else if(target.getPosition().x<=minHorizontal +10){
			this.direction = 1;
		}
		x =  x + (1 * direction);
		
		//GESTION DU Y 
		int y;
		double f = (100 - 30)/2.0;
		y = (int) (30 + f + (f * Math.sin(x/25.0)));
		 System.out.println("POSITION " + ":" + y);
		target.setPosition(new Point(x,  (int) y));
	}	

}
/*
package deplacement;

import java.awt.Point;

import deplacement.balise.DeplBalise;
import model.ElementMobile;

public class DeplSinusoidal extends DeplBalise {

	Integer minHorizontal;
	Integer maxHorizontal;
	Integer direction = 1;
	Integer delta = 2;

	public DeplSinusoidal(Integer minHorizontal, Integer maxHorizontal) {
		this.minHorizontal = minHorizontal;
		this.maxHorizontal = maxHorizontal;
	}

	// UPDATE LE X
	@Override
	public void bouge(ElementMobile target) {
		// RECUPERATION DE LA POSITION ACTUELLE
		Point p = target.getPosition();

		// GESTION DU X
		int x = p.x;
		if (target.getPosition().x >= maxHorizontal - 10) {
			this.direction = -1;
		} else if (target.getPosition().x <= minHorizontal + 10) {
			this.direction = 1;
		}
		x = x + (1 * direction);

		// GESTION DU Y
		int y;
		double f = (100 - 30) / 2.0;
		y = (int) (30 + f + (f * Math.sin(x / 25.0)));
		target.setPosition(new Point(x, y));
	}

}
>>>>>>> 60f97d8c761ccde6daca5b6a8fd1fee09215bc5c
*/