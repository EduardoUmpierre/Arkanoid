package Arkanoid;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bola extends Sprite {
	
	private double dy = -2, dx = -2;

	public Bola() {
		super(5, 5, Color.BLACK);
	}
	
	public void move() {
		super.move(dx, dy);
	}
	
	public void inverteEixoX() {
		dx *= -1;
	}
	
	public void inverteEixoY() {
		dy *= -1;
	}

}
