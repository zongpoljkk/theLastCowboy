package sharedObject;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
		public void draw(GraphicsContext gc);
		public boolean isVisible();

}
