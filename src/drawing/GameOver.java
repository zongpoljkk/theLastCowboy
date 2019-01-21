package drawing;

import drawing.GameScreen;
import drawing.GameWindow;
import drawing.GameOver;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOver {
	public static final Font GAMEOVER_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("Digital_tech.otf"),80);
	public static final Font CONTENT_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("Digital_tech.otf"),40);
	public static final Font MAIN_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("CourierNew.ttf"),20);
	public static Image gameover;
	public static AudioClip gameoversound = new AudioClip(ClassLoader.getSystemResource("GameOver.wav").toString());
	private static boolean isFinished;
	private static Thread t;
	public GameOver() {
		
	}
	
	public static void draw(GraphicsContext gc, GameScreen gamescreen) {
		setImage();
		playSong();
		
		t = new Thread(new Runnable() {
			public void run() {
				int framebg = 0;
				int framepress = 0;
				int count = 0;
					setGameOver(gc);
					try {
						Thread.sleep(100);
						System.out.println("FINISH");
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					while (count!=3) {
						System.out.println("COUNT :" +count);
						if (framebg==60 && count!=3) {
							setBackground(gc);
						}
						if (framepress==120 && count!=3) {
							setSentence(gc, gamescreen);
							framebg=0;
							framepress=0;
							count++;
						}
						framebg++;
						framepress++;
						System.out.println(framebg+" "+framepress);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (count==3) {
							setBackground(gc);
							setSentence(gc, gamescreen);
							setFinished(true);
							
						}
					}
				}
		});	
		t.setDaemon(true);
		t.start();
		
	}
	
	public static void setGameOver(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFont(GAMEOVER_FONT);
		gc.setFill(Color.ORANGERED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2 );
		gc.fillText("GAME OVER", 220, 250);
		gc.strokeText("GAME OVER", 220, 250);
		
	}
	public static void setBackground(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 800, 450);
		gc.drawImage(gameover, 0, 0);
	}
	public static void setSentence(GraphicsContext gc, GameScreen gamescreen) {
		gc.setFill(Color.ORANGERED);
		gc.setFont(Font.font ("Verdana", 40));
		gc.fillText("YOUR SCORE IS ", 390, 75);
		gc.setFont(Font.font ("Verdana", 80));
		gc.fillText(String.valueOf(gamescreen.getScore()), 480, 165);
		gc.setFont(Font.font ("Verdana", 30));
		gc.fillText("HIGH SCORE IS " + GameWindow.high_score, 400, 220);
		gc.setFont(CONTENT_FONT);
		gc.setFill(Color.DARKSALMON);
		gc.fillText("try harder\nnext time bro", 440, 280);
		gc.setFill(Color.WHITE);
		gc.setFont(MAIN_FONT);
		gc.fillText("Press Enter to go to Main Menu", 370, 380);
	}
	public static void setImage() {
		gameover = new Image("gameover.png");
		
	}
	public static void playSong() {
		gameoversound.play();
	}
	public static void startAnimation(GraphicsContext gc, GameScreen gameScreen) {
		draw(gc, gameScreen);
	}
	public static boolean isFinished() {
		return isFinished;
	}
	public static void setFinished(boolean isFinished) {
		GameOver.isFinished = isFinished;
	}
}
