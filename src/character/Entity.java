package character;

import sharedObject.IRenderable;

public abstract class Entity implements IRenderable {
	protected double x;
	protected double y;
	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Entity() {
		
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public abstract void updatePos();
	
}
