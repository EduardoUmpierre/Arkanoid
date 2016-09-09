package Arkanoid;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {
	
	private Ball ball;
	private Paddle paddle;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		ball.draw(canvas);
		paddle.draw(canvas);
	}

	@Override
	protected void setup() {
		this.setTitle("Arkanoid");
		this.setResolution(Resolution.MSX);
		this.setFramesPerSecond(60);
		
		// Ball instance
		ball = new Ball();
		ball.setPosition(150, 150);
		
		// Paddle instance
		paddle = new Paddle();
		paddle.setPosition(Resolution.MSX.width / 2 - 10, Resolution.MSX.height - 20);
	}

	@Override
	protected void loop() {		
		ball.move();
		
		Point ballPosition = ball.getPosition();
		Point paddlePosition = paddle.getPosition();
		
		/*
		 * Mudança do sentido da bola
		 */
		if(ballPosition.y <= 0 || ballPosition.y >= Resolution.MSX.height - 5 || (ballPosition.y + 5 >= paddlePosition.y && ballPosition.x + 5 >= paddlePosition.x && ballPosition.x <= paddlePosition.x + 20)) {
			ball.yAxis();
		}
		
		if(ballPosition.x <= 0 || ballPosition.x >= Resolution.MSX.width - 5) {
			ball.xAxis();
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
				if(paddlePosition.x + 20 >= Resolution.MSX.width)
					paddle.move(-10, 0);
				else
					paddle.move(3, 0);
			}
		});
		
		redraw();
	}

}
