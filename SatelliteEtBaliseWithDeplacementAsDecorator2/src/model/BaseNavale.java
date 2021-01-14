package model;

import java.awt.Point;

import eventHandler.AbstractEvent;
import eventHandler.EventHandler;

public class BaseNavale extends ElementMobile {
	
	EventHandler eventHandler;
	Manager manager;
	Point position;
	int dataCollected;

	
	public BaseNavale(int memorySize, Point p) {
		super(memorySize);
		this.eventHandler = new EventHandler();
		this.position = p;
		this.dataCollected = 0;
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
}
