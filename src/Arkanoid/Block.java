package Arkanoid;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Block extends Sprite {
	
	private boolean isAlive = true;
	private int hits;

	public Block(int x, int y, Color color, int w, int h, int hits) {		
		super(w, h, color);
		super.setPosition(x, y);
		
		this.hits = hits;
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public void die() {
		this.isAlive = false;
	}
	
	public boolean wasTouched(Ball ballObject) {		
		if(this.isAlive == true) {
			Rect ball = ballObject.getBounds();
			Rect block = this.getBounds();
			
			if(
				!(ball.y + ball.height <= block.y ||
				ball.y >= block.y + block.height ||
				ball.x >= block.x + block.width ||
				ball.x + ball.width <= block.x)
			) {				
				this.hits--;
				
				if(this.hits == 0)
					this.die();
				
				return true;
			}
		}
		
		return false;
	}
	
}
