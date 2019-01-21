package character;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pistol extends Item{
	private int n =0;
	public List<Image> image = new ArrayList<>();
	public Pistol() {
		Image item = new Image("pistol.png",40,40,false,false);
		image.add(item);
	}
	@Override
	public void effect(Cowboy cowboy) {
		cowboy.setpistolAvailable(true);                                  
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

