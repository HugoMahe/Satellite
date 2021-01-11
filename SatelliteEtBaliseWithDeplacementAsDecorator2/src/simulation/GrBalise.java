package simulation;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphicLayer.GImage;
import event.PositionChanged;

public class GrBalise extends GrElementMobile {
	public GrDescription descriptionBalise;
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
		System.out.println("ici");
		descriptionBalise.refresh();
	}

}
