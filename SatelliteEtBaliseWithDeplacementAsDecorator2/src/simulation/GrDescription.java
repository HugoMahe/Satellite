package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import graphicLayer.GElement;
import graphicLayer.GString;
import model.Balise;

public class GrDescription extends GElement {
	public GrBarreChargement barre;
	public GString label;
	public GString labelProfondeur;
	public Balise baliseModel;
	
	public GrDescription(GrBalise bal, String label, Balise balModel) {
		// ON RATTACHE NOTRE DESCRIPTION A LA BALISE ASSOCIEE 
		bal.addElement(this);
		// INSTANCE DE BARRE CHARGEMENT
		this.barre = new GrBarreChargement(balModel);
		bal.addElement(barre.model);
		// INSTANCE DU LABEL NOM DE BALISE
		this.label = new GString(label);
		this.label.setDimension(new Dimension(500, 500));
		Point p = new Point(12, 60);
		this.label.setPosition(p);
		this.label.setFont(new Font("Arial", 1, 20));
		this.label.setColor(Color.WHITE);
		bal.addElement(this.label);
		this.baliseModel=balModel;
		// INSTANCE DU LABEL PROFONDEUR
		this.labelProfondeur = new GString("0");
		this.labelProfondeur.setPosition( new Point(5,80));
		this.labelProfondeur.setFont(new Font("Arial", 1, 10));
		this.labelProfondeur.setColor(Color.WHITE);
		bal.addElement(labelProfondeur);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void translate(Point gap) {
		// TODO Auto-generated method stub	
	}
	
	public void refresh() {
		barre.refresh();
		this.labelProfondeur.setString("Profondeur :" + baliseModel.profondeur());
	}
}
