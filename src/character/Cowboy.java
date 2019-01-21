package character;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class Cowboy extends Entity implements IRenderable {
	private boolean lvtwobefore = false;
	private boolean lvthreebefore = false;
	private boolean lvfourbefore = false;
	private boolean lvfivebefore = false;
	private boolean lvsixbefore = false;
	private boolean lvsevenbefore = false;
	private boolean pistolAvailable = true;
	private boolean horseAvailable = true;
	private boolean barrierAvailable = true;
	private boolean sandglassAvailable = true;
	private boolean sandglassed = false;
	private String control;
	private int life = 10;
	public Image cowboypic;
	private int exp = 0;
	private int lv = 1;
	private int maxexp = 80;
	private int score = 0; // self implement
	private int timeOfPics = 0;
	private List<Image> left = new ArrayList<>();
	private List<Image> right = new ArrayList<>();
	private List<Image> front = new ArrayList<>();
	private List<Image> back = new ArrayList<>();
	private boolean isBarrierOn = false;
	private int barrierCount;
	public Image barrier = new Image("BariaEffect.png");
	private int speed = 4;
	public AudioClip collidesound = new AudioClip(ClassLoader.getSystemResource("dying.mp3").toString());

	public Cowboy() {
		super(400, 225);
		for (int i = 1; i < 4; i++) {
			left.add(new Image("left" + i + ".png"));
			right.add(new Image("right" + i + ".png"));
			front.add(new Image("front" + i + ".png"));
			back.add(new Image("back" + i + ".png"));
		}
		setCowboy();
	}

	public void gainHP() {
		this.life += 1;
	}

	public boolean pistolAvailable() {
		return pistolAvailable;
	}

	public void setpistolAvailable(boolean pistolAvailable) {
		this.pistolAvailable = pistolAvailable;
	}

	public boolean horseAvailable() {
		return horseAvailable;
	}

	public void sethorseAvailable(boolean horseAvailable) {
		this.horseAvailable = horseAvailable;
	}

	public boolean barrierAvailable() {
		return barrierAvailable;
	}

	public void setbarrierAvailable(boolean barrierAvailable) {
		this.barrierAvailable = barrierAvailable;
	}

	public boolean sandglassAvailable() {
		return sandglassAvailable;
	}

	public void setsandglassAvailable(boolean sandglassAvailable) {
		this.sandglassAvailable = sandglassAvailable;
	}
	
	public boolean isLvtwobefore() {
		return lvtwobefore;
	}
	
	public void setLvtwobefore(boolean lvtwobefore) {
		this.lvtwobefore = lvtwobefore;
	}

	public boolean isLvthreebefore() {
		return lvthreebefore;
	}

	public void setLvthreebefore(boolean lvthreebefore) {
		this.lvthreebefore = lvthreebefore;
	}

	public boolean isLvfourbefore() {
		return lvfourbefore;
	}

	public void setLvfourbefore(boolean lvfourbefore) {
		this.lvfourbefore = lvfourbefore;
	}

	public boolean isLvfivebefore() {
		return lvfivebefore;
	}

	public void setLvfivebefore(boolean lvfivebefore) {
		this.lvfivebefore = lvfivebefore;
	}

	public boolean isLvsixbefore() {
		return lvsixbefore;
	}

	public void setLvsixbefore(boolean lvsixbefore) {
		this.lvsixbefore = lvsixbefore;
	}

	public boolean isLvsevenbefore() {
		return lvsevenbefore;
	}

	public void setLvsevenbefore(boolean lvsevenbefore) {
		this.lvsevenbefore = lvsevenbefore;
	}

	public boolean isDead() {
		if (life == 0)
			return true;
		return false;
	}

	public void decreaseLife() {
		life--;

	}

	public void setCowboy() {
		cowboypic = front.get(0);
	}

	@Override
	public void draw(GraphicsContext gc) {
		// System.out.println("hero");
		timeOfPics++;
		if (timeOfPics >= 30)
			timeOfPics = 0;
		// System.out.println("earth");
		if (isBarrierOn)
			gc.drawImage(barrier, x - 8, y + 15);
		gc.drawImage(cowboypic, x, y);

	}

	public void updatePos() {

		if (control.contains("a"))
			if (x >= 35) {
				x -= speed;
				cowboypic = left.get(timeOfPics / 10);
			}
		if (control.contains("d"))
			if (x + 90 <= 800) {
				x += speed;
				cowboypic = right.get(timeOfPics / 10);

			}
		if (control.contains("w"))
			if (y - 60 >= 0) {
				y -= speed;
				cowboypic = back.get(timeOfPics / 10);

			}
		if (control.contains("s"))
			if (y + 90 <= 460) {
				y += speed;
				cowboypic = front.get(timeOfPics / 10);
			}

	}

	@Override
	public boolean isVisible() {
		return true;
	}

	public void updateLv() {
		if (exp >= maxexp) {
			lv++;
			exp = 0;
			maxexp += 2 * lv;
		}
	}

	// wait for bullet class
	public Bullet attack(char c) {
		Bullet bullet = new Bullet(x, y, c);
		RenderableHolder.getinstance().add(bullet);
		bullet.setBullet();
		return bullet;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getMaxexp() {
		return maxexp;
	}

	public void setMaxexp(int maxexp) {
		this.maxexp = maxexp;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isAttacked(double x, double y) {

		if (Math.abs(this.x - x) <= 25 && Math.abs(this.y - y) <= 25) {
			if (isBarrierOn) {
				barrierCount--;
				if (barrierCount == 0)
					isBarrierOn = false;
			}

			else {
				if (sandglassed) {
					Monster.setSpeed(1.5);
					sandglassed = false;
				}
				life--;
				collidesound.play();
			}
			return true;
		}
		return false;
	}

	public void barrier() {
		isBarrierOn = true;
		barrierCount = 3;
	}

	public int getBariaCount() {
		return barrierCount;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean getSandglassed() {
		return sandglassed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public void setSandglassed(boolean sandglassed) {
		this.sandglassed = sandglassed;
	}
}
