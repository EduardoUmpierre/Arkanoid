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
	private Block[][] blocks;
	
	private Sprite background;
	
	private final Resolution resolution = Resolution.MODE_X;
	private final int scenarioWidth = resolution.width - 100;
	
	private int score = 0;
	private int highscore = 1000;
	private int lifes = 3;
	
	private int stage = 2;
	private int blockWidth;
	private int blockNum;
	
	private int minScore;
	
	private String message = "";
	private boolean messageVisible = false;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		// Background
		background.draw(canvas);
		
		// Textos
		canvas.putText(scenarioWidth + 6, 5, 10, "SCORE: " + score);
		canvas.putText(scenarioWidth + 6, 20, 9, "HIGHSCORE: " + highscore);
		canvas.putText(scenarioWidth + 6, 35, 9, "VIDAS: " + lifes);
		
		// Blocks		
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < blockNum; x++) {
				if(blocks[y][x].getIsAlive()) {
					blocks[y][x].draw(canvas);
				}
			}
		}
		
		ball.draw(canvas);
		paddle.draw(canvas);
		
		if(messageVisible) {
			canvas.putText(scenarioWidth / 2 - 40, resolution.height / 2 - 10, 20, message != "" ? message : "Nível " + stage);
		}
	}

	@Override
	protected void setup() {
		this.setTitle("Arkanoid");
		this.setResolution(resolution);
		this.setFramesPerSecond(60);
		
		background = new Sprite(scenarioWidth, resolution.height, Color.LIGHTGRAY);		
		
		// Ball instance
		ball = new Ball();
		ball.setPosition(scenarioWidth / 2 - 2, 150);
		
		// Paddle instance
		paddle = new Paddle();
		paddle.setPosition(scenarioWidth / 2 - 10, resolution.height - 20);
		
		// Blocks		
		if(stage == 1) {			
			// Blocks
			blockNum = 6;
			blocks = new Block[6][blockNum];
			blockWidth = (scenarioWidth - blockNum) / blockNum - 1;
			
			minScore = blockNum * 6 * 100;
			
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
				
				for(int x = 0; x < blockNum; x++) {					
					blocks[y][x] = new Block(x * blockWidth + (x * 1) + (blockNum / 2), y * 6 + 10, color, blockWidth, 5, hits);
				}
			}
			
			// Congela o jogo e mostra a mensagem
			ball.setFrozenStatus(true);
		} else if(stage == 2) {
			// Blocks
			blockNum = 6;
			blocks = new Block[6][blockNum];
			blockWidth = (scenarioWidth - blockNum) / blockNum;
			
			minScore = blockNum * 6 * 100;
			
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
				
				for(int x = 0; x < blockNum; x++) {
					blocks[y][x] = new Block(x * blockWidth + (x * 1) + (blockNum / 3), y * 6 + 10, color, blockWidth, 5, hits);
				}
			}
			
			// Congela o jogo e mostra a mensagem
			ball.setFrozenStatus(true);
		}
	}

	@Override
	protected void loop() {		
		Point paddlePosition = paddle.getPosition();
		
		// Se a bola estiver no modo congelado
		if(ball.getFrozenStatus()) {
			// Mostra a mensagem
			messageVisible = ball.frozen(paddle);
		} else {			
			// Zera a mensagem
			message = "";
			
			// Senão continua o jogo
			ball.move();
			
			Rect ballPosition = ball.getBounds();
			
			/*
			 * Mudanca do sentido da bola
			 */
			if(ballPosition.y <= 0 || ballPosition.y >= resolution.height - 5 || (ballPosition.y + 5 >= paddlePosition.y && ballPosition.x + 5 >= paddlePosition.x && ballPosition.x <= paddlePosition.x + 20)) {
				ball.yAxis();
			}
			
			if(ballPosition.x <= 0 || ballPosition.x >= scenarioWidth - 5) {
				ball.xAxis();
			}
			
			if(ballPosition.y + 5 >= paddlePosition.y && (ballPosition.x + 5 <= paddlePosition.x || ballPosition.x >= paddlePosition.x + 20)) {
				ball.setFrozenStatus(true);
				
				lifes--;
				
				if(lifes == 0) {
					message = "GAME OVER";
					score = 0;
					lifes = 3;
					stage = 1;
					
					setup();
				}
			}
			
			/*
			 * Blocos
			 */		
			for(int y = 0; y < 6; y++) {
				for(int x = 0; x < blockNum; x++) {				
					if(blocks[y][x].wasTouched(ball)) {
						if(!blocks[y][x].getIsAlive())
							this.score += 100;
						
						ball.yAxis();
					}
				}
			}
			
			/*
			 * Atualiza o highscore caso o score passá-lo
			 */
			if(score > highscore)
				highscore = score;
		}
		
		/*
		 * Movimento do Paddle
		 */
		this.bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x <= 0)
					paddle.move(0, 0);
				else
					paddle.move(-5, 0);
			}
		});
		
		this.bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x + 20 >= scenarioWidth)
					paddle.move(0, 0);
				else
					paddle.move(5, 0);
			}
		});
		
		if(score == minScore) {
			ball.setFrozenStatus(true);
			message = "Próximo nível";
			
			stage++;
			
			setup();
		}
		
		redraw();
	}	
}