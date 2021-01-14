package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import event.SynchroBaseEvent;
import graphicLayer.GImage;
import graphicLayer.GRect;
import listener.SynchroBaseEventListener;
import model.BaseNavale;

public class GrBaseNavale extends GrElementMobile {

	BaseNavale model;
	Boolean duringSynchro = false;
	GRect modelGraphique;
	
	/*
	 * VERSION AVEC IMAGE
	public GrBaseNavale() {
		File path = new File("SatelliteEtBaliseWithDeplacementAsDecorator2/bateau.jpg");
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
	}*/
	
	public GrBaseNavale(BaseNavale base) {
		this.model=base;
		this.modelGraphique = new GRect();
		this.modelGraphique.setColor(Color.RED);
		this.modelGraphique.setPosition(this.model.getPosition());
		this.modelGraphique.setDimension(new Dimension(100, 20));
	}
	
	public void setModel(BaseNavale base) {
		this.model = base;
		this.setPosition(this.model.getPosition());
		this.model.registerListener(SynchroBaseEvent.class, this);
		this.repaint();
	}
	
}
