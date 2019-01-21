package drawing;

import java.util.Random;

import character.Heart;
import character.Pistol;
import character.Horse;
import character.Barrier;
import character.Sandglass;
import character.Cowboy;
import character.Item;
import character.Monster;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import sharedObject.RenderableHolder;

public class GameWindow extends Canvas{
	private static AnimationTimer gamewindowanimation;
	private Cowboy cowboy;
	private GameScreen gamescreen;
	private Monster monster;
	private Random rand = new Random();
	private boolean isOver = false;
	private boolean isStateSeven = false;
	private boolean toMainMenu = false;
	private int FireTimes = 0;
//	private int CoolDownHorse; 
//	private int CoolDownFire;
//	private int CoolDownBarrier;
//	private int CoolDownSandglass;
	private int CoolDownSpeed;
	private String control = "";
	private GraphicsContext gc;
	private Scene scene;
	private Stage primaryStage;
	private char c = 'a';
	private int frame = 0;
	private int monsteramount = 1;
	private int InitialSpeed;
	public static int high_score = 0;
	public AudioClip soundgame;
	public AudioClip fire = new AudioClip(ClassLoader.getSystemResource("Laser.mp3").toString()); 
	public String[] soundgameURL = {"500Miles.mp3","houseOfTheRisingSun.mp3","hurt.mp3","mammasDontLetYourBabiesGrowUpToBeCowboys.mp3",
			"theGoodtheBadAndTheUgly.mp3", "theLastCowboySong.mp3", "wayDownWego.mp3", "westWorld.mp3"};
	
	public GameWindow(Stage primaryStage) {
		setWidth(800);
		setHeight(450);
		this.primaryStage = primaryStage;
		gc = getGraphicsContext2D();
		StackPane s = new StackPane();
		s.getChildren().add(gc.getCanvas());
		scene = new Scene(s);
		this.primaryStage.setScene(scene);
		addAll();
		int x = rand.nextInt(soundgameURL.length);
		soundgame = new AudioClip(ClassLoader.getSystemResource(soundgameURL[x]).toString());
		soundgame.play();
		InitialSpeed = cowboy.getSpeed();
		requestFocus();
	}
	
	public void drawGameWinDow() {
		addMoving(gc);
		frame=0;
		gamewindowanimation = new AnimationTimer() {
			public void handle(long now) {
				updateDetail();
				updateState();
				updateSong();
				isGameEnd();
			}
		};
		gamewindowanimation.start();
	}
	
	public void addMoving(GraphicsContext gc) {
		this.setOnKeyPressed((KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.L) cowboy.setLv(this.cowboy.getLv()+1);
			if (KeyEvent.getCode() == KeyCode.B) cowboy.setLv(7);
			if (KeyEvent.getCode() == KeyCode.O) cowboy.setLife(0);
			if(KeyEvent.getCode() == KeyCode.P) {
				cowboy.setLv(7);
				cowboy.setLife(1000000000);
			
			}
			if (KeyEvent.getCode() == KeyCode.LEFT) {
				control+="a";
				c='a';
				System.out.println(control);
			}
			if (KeyEvent.getCode() == KeyCode.RIGHT) {
				control+="d";
				c='d';
				System.out.println(control);
			}
			if (KeyEvent.getCode() == KeyCode.UP) {
				control+="w";
				c='w';
				System.out.println(control);
				
			}
			if (KeyEvent.getCode() == KeyCode.DOWN ) {
				control+="s";
				c='s';
				System.out.println(control);
				
			}
			if (KeyEvent.getCode() == KeyCode.SPACE) {
				if (!isOver && !toMainMenu) {
					fire.play();
					cowboy.attack(c);
				}
			}
			if (KeyEvent.getCode() == KeyCode.ENTER) {
				if (isOver && GameOver.isFinished()) {
					soundgame.stop();
					StartWindow startwindow =new StartWindow(primaryStage);
					startwindow.startAnimation();
					}
			}
			
			if(KeyEvent.getCode() == KeyCode.A) {                  //gunskill
				if(!isOver) {
				if(cowboy.pistolAvailable()) {FireTimes = 120;
				cowboy.setpistolAvailable(false);
				}
				}

			}
			
			if(KeyEvent.getCode() == KeyCode.S) {                   //horseskill
				if(!isOver) {
				if(cowboy.horseAvailable()) {FireTimes = 120;
				cowboy.sethorseAvailable(false);
				}
				}

			}
			if(KeyEvent.getCode() == KeyCode.D) {                    //barrier
				
				if(!isOver) {
				if(cowboy.barrierAvailable()) {

					cowboy.barrier();
					cowboy.setbarrierAvailable(false);;
				}
				}


			}
			if (KeyEvent.getCode() == KeyCode.F) {                    //sandGlassskill
				if(!isOver) {
				if(cowboy.sandglassAvailable()) {
					
					monster.sandglass();
					cowboy.setSandglassed(true);
					cowboy.setsandglassAvailable(false);;
				}
				}
			}			
		});
		this.setOnKeyReleased((KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.LEFT) {
				control = control.replace("a","");
				RenderableHolder.getinstance().updatePos(control);
			}
			if (KeyEvent.getCode() == KeyCode.RIGHT) {
				control = control.replace("d", "");
				RenderableHolder.getinstance().updatePos(control);
			}
			if (KeyEvent.getCode() == KeyCode.UP) {
				control = control.replace("w", "");
				RenderableHolder.getinstance().updatePos(control);
			}
			if (KeyEvent.getCode() == KeyCode.DOWN ) {
				control = control.replace("s", "");
				RenderableHolder.getinstance().updatePos(control);
			}
			if (KeyEvent.getCode() == KeyCode.SPACE) {
				RenderableHolder.getinstance().updatePos(control);
			}
		});
		
	}
	
	public void updateDetail() {
		frame++;
		if ((frame%600)<500)
			{
				if (frame%60 ==0) {
					for(int i = 0 ; i<monsteramount ; i++) addMonster();
				}
				if(frame %300==0) addItem();
			}
			RenderableHolder.getinstance().remove();
			RenderableHolder.getinstance().draw(gc);
			int exp = RenderableHolder.getinstance().setVisible();
			int score = RenderableHolder.getinstance().setVisible();
			RenderableHolder.getinstance().Collision(cowboy);
			cowboy.setExp(cowboy.getExp()+exp);
			cowboy.setScore(cowboy.getScore()+score);
			cowboy.updateLv();
			gamescreen.setCowboyData(cowboy.getLv(),cowboy.getExp(),cowboy.getMaxexp(),cowboy.getScore(), cowboy.getLife(),gc);
			fire();
			if(FireTimes != 0 ) FireTimes--;
//			if(CoolDownFire !=0 )CoolDownFire--;
//			if(CoolDownHorse != 0 )CoolDownHorse--;
//			if(CoolDownBarrier != 0 )CoolDownBarrier--;
//			if(CoolDownSandglass != 0 )CoolDownSandglass--;
			if(CoolDownSpeed != 0) {
				CoolDownSpeed--;
				cowboy.setSpeed(8);
			}
			
			else {cowboy.setSpeed(InitialSpeed);}
			gamescreen.setCoolDown(cowboy);
			RenderableHolder.getinstance().updatePos(control);
	}
	
	public void updateSong() {
		if (soundgame.isPlaying()==false && !isOver && !isStateSeven) playSong();
		setStateDetails();
	}
	public void updateState() {
		if (cowboy.getLv()==2 && cowboy.isLvtwobefore()==false && !isOver) {
			cowboy.setLvtwobefore(true);
			cowboy.setSandglassed(false);
		}
		
		if (cowboy.getLv()==3 && cowboy.isLvthreebefore()==false && !isOver) {
			cowboy.setLvthreebefore(true);
			cowboy.setSandglassed(false);
		}
		
		if (cowboy.getLv()==4 && cowboy.isLvfourbefore()==false && !isOver) {
			cowboy.setLvfourbefore(true);
			cowboy.setSandglassed(false);
		}
		if (cowboy.getLv()==5 && cowboy.isLvfivebefore()==false && !isOver) {
			cowboy.setLvfivebefore(true);
			cowboy.setSandglassed(false);
		}
		if (cowboy.getLv()==6 && cowboy.isLvsixbefore()==false && !isOver) {
			cowboy.setLvsixbefore(true);
			cowboy.setSandglassed(false);
		}
		if (cowboy.getLv()==7 && cowboy.isLvsevenbefore()==false && !isOver) {
			cowboy.setLvsevenbefore(true);
			cowboy.setSandglassed(false);
			setHighscore();
			isStateSeven = true;
		
		}
//		if (cowboy.getLv() == 8 && !isOver) {
//			cowboy.setSandglassed(false);
//		}
	}
	public void isGameEnd() {
		if (cowboy.getLife()<0) {
			RenderableHolder.getinstance().clearList();
			gamewindowanimation.stop();
			GameOver.startAnimation(gc, gamescreen);
			GameOver.setFinished(false);
			isOver = true;
			setHighscore();
			stopAllSong();
		}
	}
	
	public void addAll() {
		addGameScreen();
		addHero();
		addMonster();	
	}
	public void addMonster() {
		monster = new Monster(cowboy);
		RenderableHolder.getinstance().add(monster);
	}
	public void addItem() {  
		Random rand = new Random();
		int n = rand.nextInt(7) + 1;
		switch(n) {
		case 1: Item item1 = new Heart();
			RenderableHolder.getinstance().add(item1);
			break;
		case 2: Item item2 = new Heart();
			RenderableHolder.getinstance().add(item2);
			break;
		case 3: Item item3 = new Heart();
			RenderableHolder.getinstance().add(item3);
			break;
		case 4: Item item4 = new Pistol();
			RenderableHolder.getinstance().add(item4);
			break;
		case 5: Item item5 = new Horse();
			RenderableHolder.getinstance().add(item5);
			break;
		case 6: Item item6 = new Barrier();
			RenderableHolder.getinstance().add(item6);
			break;
		case 7: Item item7 = new Sandglass();
			RenderableHolder.getinstance().add(item7);
			break;
		}
	}
	public void addHeart() {                                              
		Item item = new Heart();
		RenderableHolder.getinstance().add(item);
	}
	public void addPistol() {                                              
		Item item = new Pistol();
		RenderableHolder.getinstance().add(item);
	}
	public void addHorse() {                                          
		Item item = new Horse();
		RenderableHolder.getinstance().add(item);
	}
	public void addBarrier() {                                              
		Item item = new Barrier();
		RenderableHolder.getinstance().add(item);
	}
	public void addSandglass() {                                         
		Item item = new Sandglass();
		RenderableHolder.getinstance().add(item);
	}
	public void addHero() {
		cowboy = new Cowboy();
		RenderableHolder.getinstance().add(cowboy);
	}
	public void addGameScreen() {
		gamescreen = new GameScreen();
		RenderableHolder.getinstance().add(gamescreen);
	}
	public void playSong() {
		int x = rand.nextInt(soundgameURL.length);
		soundgame = new AudioClip(ClassLoader.getSystemResource(soundgameURL[x]).toString());
		soundgame.play();
	}
	public void stopAllSong() {
		soundgame.stop();
	}
	public static AnimationTimer getGamewindowanimation() {
		return gamewindowanimation;
	}
	public void fire() {
		if(FireTimes %30 ==  0 && FireTimes!=0 )
		{	
			fire.play();
			cowboy.attack('s');
			cowboy.attack('w');
			cowboy.attack('d');
			cowboy.attack('a');
		    cowboy.attack('r');
		    cowboy.attack('t');
		    cowboy.attack('u');
		    cowboy.attack('y');
		}	
	}
	public void setStateDetails() {
		if(cowboy.getLv() ==3) {
			Monster.setSpeed(1.5);
			cowboy.setSpeed(5);
			InitialSpeed = 5;
		}
		if(cowboy.getLv() == 4) {
			Monster.setSpeed(2);
		}
		if(cowboy.getLv() == 5) {
			Monster.setSpeed(2.5);
			cowboy.setSpeed(6);
			InitialSpeed = 6;
		}
		if(cowboy.getLv() == 6) {
			monsteramount = 2;
			Monster.setSpeed(1.5);
		}
		if(cowboy.getLv() == 7) {
			monsteramount = 3;
			Monster.setSpeed(1.2);
		}
	}
	
	public void setHighscore() {
		if (gamescreen.getScore() > high_score) {
			high_score = gamescreen.getScore();
		}
	}

}
