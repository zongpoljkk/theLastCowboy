package sharedObject;

import java.util.ArrayList;
import java.util.List;
import character.Bullet;
import character.Cowboy;
import character.Item;
import character.Monster;
import javafx.scene.canvas.GraphicsContext;

public class RenderableHolder {
	private static final RenderableHolder r = new RenderableHolder();
	private List<IRenderable> object;
	public RenderableHolder() {
		object = new ArrayList<>();
	}
	public static RenderableHolder getinstance() {
		return r;
	}
	public void add(IRenderable i) {
		object.add(i);
	}
	public void updatePos(String control) {
		for (IRenderable i : object) {
			if ( i instanceof Cowboy) {
				((Cowboy)i).setControl(control);
				((Cowboy)i).updatePos();
			}
			if (i instanceof Bullet) {
				((Bullet)i).updatePos();
			}
			if (i instanceof Monster) {
				((Monster)i).updatePos();
			}
			if(i instanceof Item) {
				((Item)i).updatePos();
			}
		}
	}
	public void draw(GraphicsContext gc) {
		for (int i =0; i<object.size(); i++) {
			object.get(i).draw(gc);
		}
	}
	public int setVisible() {
		int exp =0;
		int score = 0;
		for (IRenderable i : object) {
			if (i instanceof Monster) {
				for (IRenderable j : object) {
					if (j instanceof Bullet) {
						if (((Monster)i).isDestroyed(((Bullet)j).getX(), ((Bullet)j).getY())) {
							((Monster)i).setVisible(false);
							((Bullet)j).setIsvisible(false);
							exp+=10;
							score += 10;
						}
					}
			}
			
			}
			
		}
		return exp;
	}
	public void remove() {
		int n = object.size();
		for (int i=n-1; i>=0; i--) {
			if (object.get(i).isVisible() == false) {
				object.remove(i);
			}
		}
	}
	public void Collision(Cowboy cowboy) {
		for (IRenderable i : object) {
			if(i instanceof Item) {
				
				if(getDist(cowboy.getX(),((Item)i).getX(),cowboy.getY(),((Item)i).getY()) <= 25) {
					System.out.println(""+cowboy.getX()+" "+((Item)i).getX()+" "+cowboy.getY()+" "+((Item)i).getY());
					((Item) i).effect(cowboy);
				}
			}
		}
	}
	public double getDist(double x1,double x2,double y1,double y2) {
		double ans = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		return ans;
	}
	
	public void clearList() {
		this.object = null;
		this.object = new ArrayList<>();
	}
	
}
