package character;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

public class Item extends Entity{
	protected boolean isVisible = true;
	private int tick=200;
	private static Random rand = new Random();
	
	public Item() {
		super(rand.nextInt(677)+35,rand.nextInt(311)+60);
	}
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return this.isVisible;
	}
	public void effect(Cowboy cowboy) {
		
	}
	
	public void updatePos() {
		tick--;
		if(tick <= 0) isVisible = false; 
	}


}
