package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import graphicLayer.GElement;
import graphicLayer.GRect;
import model.Balise;

public class GrBarreChargement extends GElement {

	public GRect model;
	public GRect chargementModel;
	public int maxHauteurBarre = 50;
	public Balise balise;

	public GrBarreChargement(Balise bal) {
		this.model = new GRect();
		this.model.setDimension(new Dimension(20, maxHauteurBarre));
		this.model.setPosition(new Point(this.model.getPosition().x, this.model.getPosition().y + 5));
		this.model.setBorderWidth(1);
		this.model.setBorderColor(Color.WHITE);
		this.chargementModel = new GRect();
		this.chargementModel.setDimension(new Dimension(18, maxHauteurBarre));
		this.chargementModel.setPosition(new Point(this.model.getPosition().x, this.model.getPosition().y - 5));
		this.chargementModel.setColor(Color.GREEN);
		this.model.addElement(chargementModel);
		this.balise = bal;
	}

	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	public void translate(Point gap) {
		// TODO Auto-generated method stub

	}

	public void refresh() {
		float pourcentage = ((float) this.balise.dataSize() / (float) this.balise.memorySize()) * 100;
		float dimension = (pourcentage / 100) * 50;
		this.chargementModel.setDimension(new Dimension(20, (int) dimension));
	}
}
