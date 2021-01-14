package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import event.PositionChanged;
import graphicLayer.GImage;
import graphicLayer.GString;
import model.Balise;
import model.ElementMobile;

public class GrBalise extends GrElementMobile {
	Balise model;

	public GrBalise() {
		File path = new File("SatelliteEtBaliseWithDeplacementAsDecorator2/balise.png");
		this.withoutBorder();
		this.withoutBackground();
		BufferedImage rawImage = null;
		try {
			rawImage = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addElement(new GImage(rawImage));
		this.setDimension(new Dimension(rawImage.getWidth()+50, rawImage.getHeight()+50));
	}
	
	@Override
	public void whenPositionChanged(PositionChanged arg) {
		super.whenPositionChanged(arg);
		this.refresh();
	}

	@Override
	public void ajoutDescription(String label, ElementMobile element) {
		this.model = (Balise) element;
		super.ajoutDescription(label, element);
		//System.out.println("ajout dans balise");
		this.description.label.setColor(Color.WHITE);
		// INSTANCE DU LABEL PROFONDEUR
		this.description.labelProfondeur = new GString("0");
		this.description.labelProfondeur.setPosition( new Point(5,80));
		this.description.labelProfondeur.setFont(new Font("Arial", 1, 10));
		this.description.labelProfondeur.setColor(Color.WHITE);
		this.addElement(this.description.labelProfondeur);
		this.description.barre = new GrBarreChargement(this);
	}
	
	
	@Override
	public void refresh() {
		this.description.labelProfondeur.setString("Profondeur :" + this.model.profondeur());
		if(this.model!=null) {
			this.description.barre.refresh(this.model);
		}
	}
	
}
