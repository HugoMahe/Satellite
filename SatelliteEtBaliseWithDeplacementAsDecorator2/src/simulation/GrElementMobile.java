package simulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

import event.PositionChanged;
import event.SynchroEvent;
import graphicLayer.GRect;
import graphicLayer.GString;
import listener.PositionChangeListener;
import listener.SynchroEventListener;
import model.ElementMobile;

public class GrElementMobile extends GRect implements PositionChangeListener, SynchroEventListener {
	ElementMobile model;
	Boolean duringSynchro = false;
	GrDescription description;

	Object getModel() {
		return this.model;
	}

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
				g.drawOval(bounds.x - i - 20, bounds.y - i, bounds.width + i + i, bounds.height + i + i);
			}
			g.setStroke(s);
			g.setColor(c);
		}
	}

	public void ajoutDescription(String label, ElementMobile element) {
		// AJOUT DU LABLE GENERIQUE
		this.description = new GrDescription();
		this.description.label = new GString(label);
		this.description.label.setDimension(new Dimension(500, 500));
		Point p = new Point(12, 60);
		this.description.label.setPosition(p);
		this.description.label.setFont(new Font("Arial", 1, 20));
		this.addElement(this.description.label);
	}

	public void refresh() {

	}

}