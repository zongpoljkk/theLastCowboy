package character;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Barrier extends Item{
	private int n =0;
	public List<Image> image = new ArrayList<>();
	public Barrier() {
		Image item = new Image("barrier.png",40,40,false,false);
		image.add(item);
	}
	@Override
	public void effect(Cowboy cowboy) {
		cowboy.setbarrierAvailable(true);
		this.isVisible =false;
	}
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
			gc.drawImage(image.get(n), x, y);
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return this.isVisible;
	}

}

