package Arkanoid;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {
	// Bola, paddle e blocos
	private Ball ball;
	private Paddle paddle;
	private Block[][] blocks;
	
	// Background
	private Sprite background;
	
	// Resolução e área jogável
	private final Resolution resolution = Resolution.MODE_X;
	private final int scenarioWidth = resolution.width - 100;
	
	// Variáveis de jogo
	private int score = 0;
	private int highscore = 10000;
	private int lifes = 3;	
	private int level = 1;
	
	// Largura dos blocos
	private int blockWidth;
	
	// Número de colunas e linhas de blocos
	private int blocksCol;
	private int blocksRow;
	
	// Score mínimo para passar de nível
	private int minScore;
	
	// Mensagem a ser exibida para o usuário
	private String message = "";
	private boolean messageVisible = false;

	@Override
	protected void draw(Canvas canvas) {
		// Limpa o canvas
		canvas.clear();
		
		// Background
		background.draw(canvas);
		
		// Textos
		canvas.putText(scenarioWidth + 6, 5, 10, "SCORE: " + score);
		canvas.putText(scenarioWidth + 6, 20, 9, "HIGHSCORE: " + highscore);
		canvas.putText(scenarioWidth + 6, 35, 9, "VIDAS: " + lifes);
		canvas.putText(scenarioWidth + 6, resolution.height - 20, 9, "NÍVEL: " + level);
		
		// Blocos
		for(int y = 0; y < blocksRow; y++) {
			for(int x = 0; x < blocksCol; x++) {
				if(blocks[y][x].getIsAlive()) {
					blocks[y][x].draw(canvas);
				}
			}
		}
		
		// Desenha a bola
		ball.draw(canvas);
		
		// Desenha o paddle
		paddle.draw(canvas);
		
		// Se a exibição de mensagem for verdadeira
		if(messageVisible) {
			// Mostra a mensagem contida na variável `message`, caso ela não esteja vazia, senão mostra o nível
			canvas.putText(scenarioWidth / 2 - 40, resolution.height / 2 - 10, 20, message != "" ? message : "Nível " + level);
		}
	}

	@Override
	protected void setup() {
		// Configurações do jogo
		this.setTitle("Arkanoid");
		this.setResolution(resolution);
		this.setFramesPerSecond(60);
		
		// Colore o background
		background = new Sprite(scenarioWidth, resolution.height, Color.LIGHTGRAY);		
		
		// Instância da bola
		ball = new Ball();
		ball.setPosition(scenarioWidth / 2 - 2, 150);
		
		// Instância do paddle
		paddle = new Paddle();
		paddle.setPosition(scenarioWidth / 2 - 10, resolution.height - 20);
		
		// Níveis
		if(level == 1) {
			
			/* 
			 * Nível 1 
			 */
			
			// Configuração dos blocos
			blocksRow = 6;
			blocksCol = 6;
			blocks = new Block[blocksRow][blocksCol];
			blockWidth = (scenarioWidth - blocksCol) / blocksCol - 1;
			
			// Geração dos blocos
			for(int y = 0; y < blocksRow; y++) {
				Color color;
				int hits = 1;
				
				// Determina cor e número de toques necessários para destruir o bloco
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
				
				for(int x = 0; x < blocksCol; x++) {
					// Cria novos blocos com as novas configurações
					blocks[y][x] = new Block(x * blockWidth + x + 5, y * 6 + 10, color, blockWidth, 5, hits);
				}
			}
		} else if(level == 2) {
			
			/* 
			 * Nível 2
			 */
			
			// Configuração dos blocos
			blocksRow = 8;
			blocksCol = 4;
			blocks = new Block[blocksRow][blocksCol];
			blockWidth = (scenarioWidth - blocksCol) / (blocksCol * 2);
			
			for(int y = 0; y < blocksRow; y++) {
				Color color;
				int hits = 1;
				
				// Determina cor e número de toques necessários para destruir o bloco
				switch(y) {
					case 2:
						color = Color.RED;
						break;
					case 3:
						color = Color.GREEN;
						break;
					case 4:
						color = Color.MAGENTA;
						break;
					case 5:
						color = Color.YELLOW;
						break;
					default:
						hits = 2;
						color = Color.GRAY;
				}
				
				for(int x = 0; x < blocksCol; x++) {
					// Cria novos blocos com as novas configurações
					blocks[y][x] = new Block(x * blockWidth + (y % 2 == 0 ? 2 : 29) + (blockWidth * x), 60 + y * 6, color, blockWidth, 5, hits);
				}
			}
		} else if(level == 3) {
			
			/* 
			 * Nível 3
			 */
			
			// Configuração dos blocos
			blocksRow = 10;
			blocksCol = 6;
			blocks = new Block[blocksRow][blocksCol];
			blockWidth = (scenarioWidth - blocksCol) / blocksCol;
			
			for(int y = 0; y < blocksRow; y++) {
				Color color;
				int hits = 1;
				
				// Determina cor e número de toques necessários para destruir o bloco
				switch(y) {
					case 3:
						color = Color.RED;
						break;
					case 4:
						color = Color.GREEN;
						break;
					case 5:
						color = Color.MAGENTA;
						break;
					case 6:
						color = Color.YELLOW;
						break;
					default:
						hits = 2;
						color = Color.GRAY;
				}
				
				for(int x = 0; x < blocksCol; x++) {
					// Cria novos blocos com as novas configurações
					blocks[y][x] = new Block(x * blockWidth + 5, 12 * y - (x * 5) + 30, color, blockWidth, 5, hits);
				}
			}
		}
		
		// Determina o score mínimo para passar de nível
		minScore = blocksCol * blocksRow * 100;
		
		// Congela o jogo e mostra a mensagem
		ball.setFrozenStatus(true);
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
			
			// Verifica se a bola bateu no topo da área jogável ou no paddle  
			if(
					ballPosition.y <= 0 || 
					(ballPosition.y + 5 == paddlePosition.y && 
					ballPosition.x + 5 >= paddlePosition.x && 
					ballPosition.x <= paddlePosition.x + 20)
				) {
				ball.yAxis();
			}
			
			// Verifica se a bola bateu nas laterais da área jogável
			if(
					ballPosition.x <= 0 ||
					ballPosition.x >= scenarioWidth - 5
				) {
				ball.xAxis();
			}
			
			// Verifica se a bola bateu na lateral do paddle
			if(
					ballPosition.y + 5 > paddlePosition.y && 
					ballPosition.x + 5 >= paddlePosition.x && 
					ballPosition.x <= paddlePosition.x + 20
				) {
				ball.yAxis();
				ball.xAxis();
			}
			
			// Verifica se a bola morreu
			if(
					ballPosition.y >= paddlePosition.y + 3 && 
					(ballPosition.x + 5 <= paddlePosition.x || 
					ballPosition.x >= paddlePosition.x + 20)
				) {
				ball.setFrozenStatus(true);
				
				lifes--;
				
				if(lifes == 0) {
					message = "GAME OVER";
					score = 0;
					lifes = 3;
					level = 1;
					
					setup();
				}
			}
			
			/*
			 * Blocos
			 */
			
			// Verifica se o bloco foi tocado
			for(int y = 0; y < blocksRow; y++) {
				for(int x = 0; x < blocksCol; x++) {
					int touched = blocks[y][x].wasTouched(ball);
					
					// Se foi tocado
					if(touched != 0) {

						// Verifica se o bloco foi destruído
						if(!blocks[y][x].getIsAlive()) {
							// Caso sim, atribui pontos
							this.score += 100;
						}
						
						// Verifica orientação do toque e atribui uma nova direção para a bola
						if(touched == 1)
							ball.xAxis();
						else
							ball.yAxis();
					}
				}
			}
			
			/*
			 * Highscore
			 */
			
			// Atualiza o highscore caso o score passá-lo
			if(score > highscore)
				highscore = score;
		}
		
		/*
		 * Movimento do Paddle
		 */
		
		// Movimenta para a esquerda
		this.bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x <= 0)
					paddle.move(0, 0);
				else
					paddle.move(-5, 0);
			}
		});
		
		// Movimenta para a direita
		this.bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(paddlePosition.x + 20 >= scenarioWidth)
					paddle.move(0, 0);
				else
					paddle.move(5, 0);
			}
		});
		
		/*
		 * Score
		 */
		
		// Verifica se o score atual é igual ao score mínimo para passar de nível
		if(score == minScore) {
			// Se for, congela a bola
			ball.setFrozenStatus(true);
			
			// Atribui a mensagem e o próximo nível
			if(level == 3) {
				message = "Você ganhou!";
				
				level = 1;
			} else {
				message = "Próximo nível";
				
				level++;
			}
			
			// Chama a função setup para reorganizar a tela
			setup();
		}
		
		// Redesenha a tela
		redraw();
	}	
}