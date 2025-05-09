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
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	public void move() {

	}
	public void checkApple() {

	}
	public void checkCollisions() {

	}
	public void gameOver(Graphics g) {

	}
	@Override
	public void actionPerformed(ActionEvent e) {


	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

		}
	}
}
