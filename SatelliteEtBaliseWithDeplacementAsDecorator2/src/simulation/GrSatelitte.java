package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import event.PositionChanged;
import graphicLayer.GImage;
import model.Balise;
import model.ElementMobile;
import model.Satelitte;

public class GrSatelitte extends GrElementMobile {

	public GrDescription descriptionSatellite;
	Satelitte model;

	public GrSatelitte() {
		File path = new File("SatelliteEtBaliseWithDeplacementAsDecorator2/satellite.png");
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
	public void ajoutDescription(String label, ElementMobile element) {
		this.model = (Satelitte) element;
		super.ajoutDescription(label, element);
		this.description.label.setColor(Color.RED);
		this.description.barre = new GrBarreChargement(this);
	}
	
	@Override
	public void whenPositionChanged(PositionChanged arg) {
		super.whenPositionChanged(arg);
		this.refresh();
	}
	
	@Override
	public void refresh() {
		if(this.model!=null) {
			this.description.barre.refresh(this.model);
		}
	}
}
