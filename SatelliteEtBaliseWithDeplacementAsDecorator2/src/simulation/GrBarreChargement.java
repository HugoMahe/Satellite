package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import graphicLayer.GElement;
import graphicLayer.GRect;
import model.Balise;
import model.ElementMobile;
import model.Satelitte;

public class GrBarreChargement extends GElement {

	public GRect model; 
	public GRect chargementModel;
	public int maxHauteurBarre = 50;
	public Balise balise;
	
	public GrBarreChargement(GrElementMobile gr) {
		// TODO Auto-generated constructor stub
		//System.out.println("lancement chargement barre");
		this.model = new GRect();
		this.model.setColor(Color.cyan);
		this.model.setDimension(new Dimension(20, maxHauteurBarre));
		this.model.setPosition(new Point(this.model.getPosition().x,this.model.getPosition().y+5));
		this.model.setBorderWidth(1);
		this.model.setBorderColor(Color.WHITE);
		this.chargementModel = new GRect();
		this.chargementModel.setDimension(new Dimension(18, maxHauteurBarre));
		this.model.addElement(chargementModel);
		this.chargementModel.setPosition(new Point(this.model.getPosition().x,this.model.getPosition().y-5));
		this.chargementModel.setColor(Color.ORANGE);
		gr.addElement(this.model);
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	@Override
	public void translate(Point gap) {
		// TODO Auto-generated method stub
		
	}
	public void refresh(Balise bal) {
		float pourcentage = ( (float) bal.dataSize() / (float) bal.memorySize()) * 100;
	 	float dimension = (pourcentage / 100 ) * 50;
	 	this.chargementModel.setDimension(new Dimension(20, (int)dimension));	
	 	if(bal.memoryFull()) {
	 		this.chargementModel.setColor(Color.GREEN);
	 	}else {
	 		this.chargementModel.setColor(Color.ORANGE);
	 	}
	}

	public void refresh(Satelitte sat) {
		// TODO Auto-generated method stub
		//System.out.println("refresh sat");
		float pourcentage = ( (float) sat.dataSize() / (float) sat.memorySize()) * 100;
	 	float dimension = (pourcentage / 100 ) * 50;
	 	this.chargementModel.setDimension(new Dimension(20, (int)dimension));	
	 	if(sat.memoryFull()) {
	 		this.chargementModel.setColor(Color.GREEN);
	 	}else {
	 		this.chargementModel.setColor(Color.ORANGE);
	 	}
	}
}
