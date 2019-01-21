package drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sharedObject.IRenderable;
import character.Cowboy;

public class GameScreen implements IRenderable{
	private int lv;
	private int exp;
	private int maxexp;
	private int score;
	private int life;
	public Image bggame;
	public Image skillBullet;
	public Image skillHorse;
	public Image skillBarrier;
	public Image skillSandglass;
	
	public GameScreen() {
		setImage();
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage(bggame, 0, 0);
		String image_path = "heart.png";
		Image heart = new Image(image_path);
		Font font = new Font("Monospace", 18);
		gc.setFont(font);
		gc.drawImage(heart, 20,0);
		gc.setFill(Color.WHITE);
		gc.fillText(" : "+life, 50, 20);
		gc.fillText("Lv: "+lv, 440, 20);
		gc.fillText("Exp : "+exp+"/"+maxexp,500, 20);
		gc.fillText("Score : "+score,600, 20);
		gc.setFill(Color.WHITE);
		gc.drawImage(skillBullet, 180,5);
		gc.fillText("A",191 , 55);
		gc.drawImage(skillHorse, 220, 5);
		gc.fillText("S",231,55);
		gc.drawImage(skillBarrier, 260 , 5);
		gc.fillText("D", 271, 55);
		gc.drawImage(skillSandglass, 300 , 5);
		gc.fillText("F", 311, 55);
		
	}
	
	public void setImage() {
		bggame = new Image("gameBackground.jpg");
		
	}
	@Override
	public boolean isVisible() {
		return true;
	}
	public int getScore() {
		return score;
	}
	public void setCowboyData(int lv,int exp,int maxexp,int score, int life,GraphicsContext gc) {
		this.lv =lv;
		this.exp = exp;
		this.maxexp = maxexp;
		this.score = score;
		this.life = life;
	}
	public void setCoolDown(Cowboy cowboy) {
		 skillBullet = new Image("gunSkill.jpg");
		 skillHorse = new Image("horseSkill.jpg");
		 skillBarrier = new Image("Baria.png");
		 skillSandglass = new Image("sandGlassSkill.jpg");
		if(!cowboy.pistolAvailable()) skillBullet = new Image("gunSkillCooldown.jpg");
		if(!cowboy.horseAvailable()) skillHorse = new Image("horseSkillCooldown.jpg");
		if(!cowboy.barrierAvailable())skillBarrier = new Image("BariaCoolDown.png");
		if(!cowboy.sandglassAvailable()) skillSandglass = new Image("sandGlassSkillCooldown.jpg");
		
	}
	
}
