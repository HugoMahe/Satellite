package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import deplacement.DeplHorizontal;
import deplacement.DeplSatellite;
import deplacement.DeplSinusoidal;
import deplacement.DeplVertical;
import deplacement.Deplacement;
import graphicLayer.GBounded;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import model.Balise;
import model.BaseNavale;
import model.Manager;
import model.Satelitte;

public class Simulation {

	Manager manager = new Manager();
	GSpace world = new GSpace("Satellite & Balises", new Dimension(800, 600));

	public void mainLoop() {
		while (true) {
			manager.tick();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addBalise(GBounded sea, int memorySize, Point startPos, Deplacement depl, String label) {		
		Balise bal = new Balise(memorySize);
		bal.setPosition(startPos);
		bal.setDeplacement(depl);
		//manager.addBalise(bal);
		manager.addElement(bal);
		GrBalise grbal = new GrBalise();
		grbal.setModel(bal);
		sea.addElement(grbal);
		grbal.ajoutDescription(label, bal);
	}

	public void addSatelitte(GBounded sky, int memorySize, Point startPos, int vitesse, String label) {
		Satelitte sat = new Satelitte(memorySize);
		sat.setPosition(startPos);
		sat.setDeplacement(new DeplSatellite(-10,1000, vitesse));
		//manager.addSatellite(sat);
		manager.addElement(sat);
		GrSatelitte grSat = new GrSatelitte();
		grSat.setModel(sat);
		sky.addElement(grSat);
		grSat.ajoutDescription(label, sat);
	}
	
	public void addBaseNavale(GBounded sky, Point startPos) {
		BaseNavale base = new BaseNavale(startPos);
		manager.setBaseNavale(base);
		GrBaseNavale grBase = new GrBaseNavale(base);
		grBase.setModel(base);
		sky.addElement(grBase.modelGraphique);
	}

	public void launch() {
		GRect sky = new GRect();
		sky.setColor(Color.WHITE);
		sky.setDimension(new Dimension(800, 300));
		GRect sea = new GRect();
		sea.setColor(Color.blue);
		sea.setDimension(new Dimension(800, 300));
		sea.setPosition(new Point(0, 300));
		this.world.addElement(sky);
		this.world.addElement(sea);
		this.addSatelitte(sky, 400, new Point(10,50), 2, "Sat 1");
		this.addSatelitte(sky, 400, new Point(100,10), 1, "Sat 2");
		this.addSatelitte(sky, 400, new Point(400,90), 3, "Sat 3");
		this.addSatelitte(sky, 400, new Point(500,140), 4, "Sat 4");
		this.addSatelitte(sky, 400, new Point(600,10), 1, "Sat 5");
		this.addBalise(sea, 300, new Point(400,200), new DeplHorizontal(50,750), "balise 1");
		this.addBalise(sea, 400, new Point(100,100), new DeplVertical(50, 200), "balise 2");
		this.addBalise(sea, 200, new Point(0,160), new DeplSinusoidal(0,800), "balise 3");
		this.addBalise(sea, 500, new Point(200,100), new DeplSinusoidal(130, 270), "balise 4");
		this.addBalise(sea, 50, new Point(300,100), new DeplSinusoidal(200, 600), "balise 5");
		this.addBaseNavale(sky, new Point(30,280));
		this.world.open();
		this.mainLoop();
	}

	public static void main(String[] args) {
		new Simulation().launch();
	}

}
