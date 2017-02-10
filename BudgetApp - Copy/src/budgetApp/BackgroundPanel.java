package budgetApp;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Allows for change of background image. 
 * @author Kyle Platou
 *
 */

public class BackgroundPanel extends JPanel{
	
	/**
	 * This class creates a custom JPanel that draws a new picture
	 * for the background. 
	 */
	private static final long serialVersionUID = 1L;
	Image bgImage = new ImageIcon("green.jpg").getImage();
	
	/**
	 * Default Constructor
	 */
	BackgroundPanel(){
		super();
	}	

	/**
	 * Overrides paintComponent in order to change background image
	 * @param Graphics 
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, null);
	}
}
