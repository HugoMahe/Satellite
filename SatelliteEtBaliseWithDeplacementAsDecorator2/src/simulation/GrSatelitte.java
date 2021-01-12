package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphicLayer.GImage;
import model.ElementMobile;

public class GrSatelitte extends GrElementMobile {

	public GrDescription descriptionSatellite;

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
		this.setDimension(new Dimension(rawImage.getWidth() + 50, rawImage.getHeight() + 50));
	}

	@Override
	public void ajoutDescription(String label, ElementMobile element) {
		// AJOUT DU LABEL GENERIQUE
		super.ajoutDescription(label, element);
		this.description.label.setColor(Color.RED);
	}
}
