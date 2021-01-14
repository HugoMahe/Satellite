package model;

import java.awt.Point;

import deplacement.DeplStandBy;
import deplacement.Deplacement;
import event.BaseNavaleAProximite;
import event.SatelliteMoved;
import eventHandler.AbstractEvent;
import eventHandler.EventHandler;
import listener.SatelliteMoveListener;

public class BaseNavale extends ElementMobile implements SatelliteMoveListener {
	
	EventHandler eventHandler;
	Manager manager;
	Point position;
	int dataCollected;

	
	public BaseNavale(int memorySize, Point p) {
		super(memorySize);
		this.eventHandler = new EventHandler();
		this.position = p;
		this.dataCollected = 0;
		this.depl = new DeplStandBy();
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	//enregistrement des listeners
	public void registerListener(Class<? extends AbstractEvent> whichEventType, Object listener) {
		eventHandler.registerListener(whichEventType, listener);
	}
	
	public void unregisterListener(Class<? extends AbstractEvent> whichEventType, Object listener) {
		eventHandler.unregisterListener(whichEventType, listener);
	}
	
	public void resetData() {
		this.dataCollected=0;
	}
	
	public void addData(int data) {
		this.dataCollected += data;
	}

	public void checkReceiverSynchro(ElementMobile other) {
		other.checkBaseNavaleSynchro(this);
	}

	public Point getPosition() {
		return this.position;
	}
	
	public void setPosition(Point p) {
		this.position = p;
	}

	@Override
	public void whenSatelitteMoved(SatelliteMoved arg) {
		Satelitte sat = (Satelitte) arg.getSource();
		int coorX = sat.getPosition().x;
		if (coorX > this.getPosition().x -10 &&  coorX < this.getPosition().x +10) {
			System.out.println("Il est là");
			eventHandler.send(new BaseNavaleAProximite(this));
		}
		
	}
}
