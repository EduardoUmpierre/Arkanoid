package Arkanoid;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {
	
	private Ball ball;
	private Paddle paddle;
	private Sprite background;
	private Block[][] blocks = new Block[6][13];
	
	private int scenarioWidth = Resolution.MSX.width - 100;
	private int score = 0;
	private int highscore = 10000;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		background.draw(canvas);
		
		canvas.putText(scenarioWidth + 6, 5, 9, "SCORE: " + score);
		canvas.putText(scenarioWidth + 6, 20, 9, "HIGHSCORE: " + highscore);
		
		// Blocks		
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 13; x++) {
				if(blocks[y][x].getIsAlive()) {
					blocks[y][x].draw(canvas);
				}
			}
		}
		
		ball.draw(canvas);
		paddle.draw(canvas);
	}

	@Override
	protected void setup() {
		this.setTitle("Arkanoid");
		this.setResolution(Resolution.MSX);
		this.setFramesPerSecond(60);
		
		background = new Sprite(scenarioWidth, Resolution.MSX.height, Color.LIGHTGRAY);		
		
		// Ball instance
		ball = new Ball();
		ball.setPosition(scenarioWidth / 2 - 2, 150);
		
		// Paddle instance
		paddle = new Paddle();
		paddle.setPosition(scenarioWidth / 2 - 10, Resolution.MSX.height - 20);
		
		// Blocks		
		for(int y = 0; y < 6; y++) {
			Color color;
			int hits = 1;
			
			switch(y) {
				case 0:
					hits = 2;
					color = Color.GRAY;
					break;
				case 1:
					color = Color.RED;
					break;
				case 2:
					color = Color.YELLOW;
					break;
				case 3:
					color = Color.BLUE;
					break;
				case 4:
					color = Color.MAGENTA;
					break;
				case 5:
					color = Color.GREEN;
					break;
				default:
					color = Color.RED;
			}
			
			for(int x = 0; x < 13; x++) {
				blocks[y][x] = new Block(x * 11 + 6, y * 6 + 10, color, 10, 5, hits);
			}
		}
	}

	@Override
	protected void loop() {		
		ball.move();
		
		Rect ballPosition = ball.getBounds();
		Point paddlePosition = paddle.getPosition();
		
		/*
		 * Mudanca do sentido da bola
		 */
		if(ballPosition.y <= 0 || ballPosition.y >= Resolution.MSX.height - 5 || (ballPosition.y + 5 >= paddlePosition.y && ballPosition.x + 5 >= paddlePosition.x && ballPosition.x <= paddlePosition.x + 20)) {
			ball.yAxis();
		}
		
		if(ballPosition.x <= 0 || ballPosition.x >= scenarioWidth - 5) {
			ball.xAxis();
		}
		
		if(ballPosition.y + 5 >= paddlePosition.y && (ballPosition.x + 5 <= paddlePosition.x || ballPosition.x >= paddlePosition.x + 20)) {
			Console.println("Morreu");
		}
		
		/*
		 * Movimento do Paddle
		 */
		this.bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x <= 0)
					paddle.move(10, 0);
				else
					paddle.move(-3, 0);
			}
		});
		
		this.bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x + 20 >= scenarioWidth)
					paddle.move(-10, 0);
				else
					paddle.move(3, 0);
			}
		});
		
		/*
		 * Blocos
		 */		
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 13; x++) {				
				if(blocks[y][x].wasTouched(ball)) {
					if(!blocks[y][x].getIsAlive())
						this.score += 100;
					
					ball.yAxis();
				}
			}
		}
		
		redraw();
	}
}
