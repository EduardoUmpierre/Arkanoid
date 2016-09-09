package Arkanoid;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;

public class Arkanoid extends GraphicApplication {
	
	private Bola bola;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		bola.draw(canvas);
	}

	@Override
	protected void setup() {
		this.setTitle("Arkanoid");
		this.setResolution(Resolution.MSX);
		this.setFramesPerSecond(60);
		
		bola = new Bola();
		bola.setPosition(150, 150);
	}

	@Override
	protected void loop() {		
		bola.move();
		
		Point bolaPosicao = bola.getPosition();
		
		if(bolaPosicao.y <= 0 || bolaPosicao.y >= Resolution.MSX.height - 5) {
			bola.inverteEixoY();
		}
		
		if(bolaPosicao.x <= 0 || bolaPosicao.x >= Resolution.MSX.width - 5) {
			bola.inverteEixoX();
		}
		
		redraw();
	}

}
