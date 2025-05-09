import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
	GameFrame() {
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		// Set the preferred size for the panel
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));

		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
}
