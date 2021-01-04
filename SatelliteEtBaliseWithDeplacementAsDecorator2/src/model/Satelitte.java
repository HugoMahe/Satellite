package model;

public class Satelitte extends ElementMobile {
			
	public Satelitte(int memorySize) {
		super(memorySize);
	}
	
	public void bouge () {
		super.bouge();
		this.send(new SatelitteMoved(this));		
	}
}
