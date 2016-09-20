package Arkanoid;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Ball extends Sprite {
	
	private double dy = -1, dx = -1;
	
	private int frozenTime = 0;
	private int frozenDelay = 65;
	private boolean frozen = false;

	public Ball() {
		super(4, 4, Color.BLACK);
	}
	
	public void move() {
		super.move(dx, dy);
	}
	
	public void xAxis() {
		dx *= -1;
	}
	
	public void yAxis() {
		dy *= -1;
	}
	
	public void setVelocity(double x, double y) {
		this.dx = x;
		this.dy = y;
	}
	
	public boolean getFrozenStatus() {
		return this.frozen;
	}
	
	public int getFrozenTime() {
		return this.frozenTime;
	}
	
	public void setFrozenStatus(boolean status) {
		this.frozen = status;
	}
	
	public boolean frozen(Paddle paddle) {
		Rect paddlePosition = paddle.getBounds();
		
		this.setVelocity(0, 0);
		this.setPosition((int) paddlePosition.x + 10 - 2, (int) paddlePosition.y - 6);
		
		if(this.frozenDelay > this.frozenTime) {
			frozenTime++;
			
			return true;
		} else {
			this.setFrozenStatus(false);
			
			if(Math.random() > 0.5) {
				this.setVelocity(-1, -1);
			} else {
				this.setVelocity(1, 1);
			}
			
			frozenTime = 0;
			
			return false;
		}
	}
}
