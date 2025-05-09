// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.awt.Graphics;
// import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600, SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS], y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten = 0, appleX, appleY;
	char direction = 'R';
	// directions: U - up, D - down, L - left, R - right
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
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
		for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
		}
		for (int j = 0; j < SCREEN_WIDTH / UNIT_SIZE; j++) {
			g.drawLine(0, j * UNIT_SIZE, SCREEN_WIDTH, j * UNIT_SIZE);
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
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
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
	public void gameOver(Graphics g) {

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
