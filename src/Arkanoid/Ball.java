package Arkanoid;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Sprite;

public class Ball extends Sprite {
	
	private double dy = -2, dx = -2;

	public Ball() {
		super(5, 5, Color.BLACK);
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

}
