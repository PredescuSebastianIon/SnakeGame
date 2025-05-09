import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600, SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS], y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten = 0, appleX, appleY;
	char direction = 'R';
	// directions: U - up, D - down, L - left, R - right
	boolean running = false;
	Timer timer;
	Random random;
	JButton restartButton;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		// Create a restart button
		restartButton = new JButton("Restart");
		restartButton.setBounds(100, 100, 50, 50);
		restartButton.setFont(new Font("Serif", Font.PLAIN, 20));
		restartButton.setFocusable(false);
		restartButton.setVisible(false);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});

		this.add(restartButton);
		startGame();
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		// if the game isn't running, don't draw
		if (!running) {
			gameOver(g);
			return;
		}

		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

		for (int i = 0; i < bodyParts; i++) {
			// painting head of snake in a different color (White)
			// Otherwise, paint the body of snake in green
			if (i == 0)
				g.setColor(Color.white);
			else
				g.setColor(Color.green);
			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		}


		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten,
					(SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
					g.getFont().getSize());

	}
	public void newApple() {
		boolean appleOnSnake = true;

		while (appleOnSnake) {
			appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
			appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
			
			// 
			appleOnSnake = false;
			for (int i = 0; i < bodyParts; i++)
				if (appleX == x[i] && appleY == y[i]) {
					appleOnSnake = true;
					break;
				}
		}
	}
	public void move() {
		// Move snake
		for (int i = bodyParts - 1; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		// Direction of snake's head
		if (direction == 'R') {
			x[0] = x[0] + UNIT_SIZE;
		} else if (direction == 'L') {
			x[0] = x[0] - UNIT_SIZE;
		} else if (direction == 'U') {
			y[0] = y[0] - UNIT_SIZE;
		} else if (direction == 'D') {
			y[0] = y[0] + UNIT_SIZE;
		}
	}
	public void checkApple() {
		if (x[0] == appleX && y[0] == appleY) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		// Check if the head collides with the body
		for (int i = bodyParts - 1; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		// check if head touches some borders
		// left border
		if (x[0] < 0)
			running = false;
		// right border
		if (x[0] > SCREEN_WIDTH)
			running = false;
		// top border
		if (y[0] < 0)
			running = false;
		// botton border
		if (y[0] > SCREEN_HEIGHT)
			running = false;

		if (!running)
			timer.stop();

	}

	public void restartGame() {
		applesEaten = 0;
		bodyParts = 6;
		for (int i = 0; i < bodyParts; i++) {
			x[i] = y[i] = 0;
		}
		direction = 'R';
		running = true;
		timer.restart();
		restartButton.setVisible(false);
		repaint();
	}

	public void gameOver(Graphics g) {
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		FontMetrics metricsScore = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten,
					(SCREEN_WIDTH - metricsScore.stringWidth("Score: " + applesEaten)) / 2,
					g.getFont().getSize());


		// Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metricsGameOver = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", 
					(SCREEN_WIDTH - metricsGameOver.stringWidth("GAME OVER")) / 2,
					(SCREEN_HEIGHT) / 2);


		// restart Button
		g.setFont(new Font("Serif", Font.PLAIN, 20));
    	FontMetrics metricsRestart = getFontMetrics(g.getFont());
		restartButton.setBounds(
					(SCREEN_WIDTH - metricsRestart.stringWidth("Restart") * 3 / 2) / 2,
			   		SCREEN_HEIGHT / 2 + 120,
					120, 40);
		restartButton.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				// Case left arrow
				case KeyEvent.VK_LEFT:
					if (direction != 'R')
						direction = 'L';
					break;

				// Case right arrow
				case KeyEvent.VK_RIGHT:
					if (direction != 'L')
						direction = 'R';
					break;

				// Case up arrow
				case KeyEvent.VK_UP:
					if (direction != 'D')
						direction = 'U';
					break;

				// Case down arrow
				case KeyEvent.VK_DOWN:
					if (direction != 'U')
						direction = 'D';
					break;
			}

		}
	}
}
