package simulation;

import java.awt.Graphics2D;
import java.awt.Point;

import graphicLayer.GElement;
import graphicLayer.GString;
import model.Balise;
import model.Satelitte;

public class GrDescription extends GElement {
	public GrBarreChargement barre;
	public GString label;
	public GString labelProfondeur;
	public Balise baliseModel;
	public Satelitte satModel;

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void translate(Point gap) {
		// TODO Auto-generated method stub
	}

}
