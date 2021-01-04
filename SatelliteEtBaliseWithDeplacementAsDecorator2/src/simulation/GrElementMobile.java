package simulation;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import graphicLayer.GRect;
import model.ElementMobile;
import model.PositionChangeListener;
import model.PositionChanged;
import model.SynchroEvent;
import model.SynchroEventListener;

public class GrElementMobile extends GRect implements PositionChangeListener, SynchroEventListener  {
	ElementMobile model;
	Boolean duringSynchro = false;

	Object getModel() { return this.model; }
	
	public void setModel(ElementMobile model) {
		this.model = model;
		model.registerListener(PositionChanged.class, this);
		model.registerListener(SynchroEvent.class, this);
		this.setPosition(this.model.getPosition());
		this.repaint();		
	}
	
	@Override
	public void whenStartSynchro(SynchroEvent arg) {
		duringSynchro = true;
	}

	@Override
	public void whenStopSynchro(SynchroEvent arg) {
		duringSynchro = false;
	}


	@Override
	public void whenPositionChanged(PositionChanged arg) {
		this.setPosition(this.model.getPosition());
		this.repaint();				
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		if (duringSynchro) {
			Color c = g.getColor();
			Stroke s = g.getStroke();
			Rectangle bounds = this.getBounds();
			g.setColor(Color.ORANGE);
			g.setStroke(new BasicStroke(2));
			for (int i = 10; i < 150; i += 25) {
				g.drawOval(bounds.x-i,bounds.y-i,bounds.width+i+i,bounds.height+i+i);
			}
			g.setStroke(s);
			g.setColor(c);
		}
	}

	
	
}