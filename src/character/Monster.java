package character;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Monster extends Entity {
	private int way;
	private static double speed = 1;
	private Random rand = new Random();
	private boolean isVisible = true;
	private Cowboy cowboy;
	private int tick;
	public Image monster1 = new Image("monster1.png");
	public Image monster2 = new Image("monster2.png");
	public Image monsterPic;

	public Monster(Cowboy cowboy) {
		way = rand.nextInt(4) + 1;
		System.out.println(way);
		if (way == 1) {
			y = (double) rand.nextInt(311) + 60;
			x = 35;
		} else if (way == 2) {
			y = (double) rand.nextInt(311) + 60;
			x = 710;
		} else if (way == 3) {
			x = (double) rand.nextInt(677) + 35;
			y = 60;
		} else if (way == 4) {
			x = (double) rand.nextInt(677) + 35;
			y = 370;
		}
		setImage();
		setX(x);
		setY(y);
		this.cowboy = cowboy;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// System.out.println("monster");
		if ((tick / 20) % 2 == 0)
			monsterPic = monster1;
		else
			monsterPic = monster2;
		gc.drawImage(monsterPic, x, y);
		tick++;
	}

	public void setImage() {
		monsterPic = monster1;
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isDestroyed(double x, double y) {
		if ((this.x < x + 20 && x - 20 < this.x) && (this.y < y + 20 && y - 20 < this.y)) {
			isVisible = false;
			return true;
		}
		return false;
	}

	public void updatePos() {
		if (cowboy.getSandglassed()) {
			x += 0.5 * (calculateCos(cowboy.getX(), cowboy.getY()));
			y += 0.5 * (calculateSin(cowboy.getX(), cowboy.getY()));
		}
		else {
			setSpeed(1.5);
			x += getSpeed() * (calculateCos(cowboy.getX(), cowboy.getY()));
			y += getSpeed() * (calculateSin(cowboy.getX(), cowboy.getY()));
		}
		boolean IsCowboyAttacked;
		IsCowboyAttacked = cowboy.isAttacked(x, y);
		if (IsCowboyAttacked) {
			isVisible = false;
		}
	

	}

	public double calculateSin(double cowboyx, double cowboyy) {
		double kam = cowboyy - this.y;
		double chid = cowboyx - this.x;
		double chack = Math.sqrt((kam * kam) + (chid * chid));
		double sin = kam / chack;
		return sin;

	}

	public double calculateCos(double cowboyx, double cowboyy) {
		double kam = cowboyy - this.y;
		double chid = cowboyx - this.x;
		double chack = Math.sqrt((kam * kam) + (chid * chid));
		double cos = chid / chack;
		return cos;
	}
	
	public static double getSpeed() {
		return speed;
	}

	public static void setSpeed(double speed) {
		Monster.speed = speed;
	}
	
	public void sandglass() {
		Monster.setSpeed(0.5);
	}

}
