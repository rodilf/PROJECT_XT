import javax.swing.JPanel;

import java.awt.Graphics;

public class Background extends JPanel {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    Gras gras = new Gras();

	public Background() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int yc=-2; yc<2+Math.ceil(Main.frame.getHeight()/128); ++yc) {
					for (int xc=-2; xc<2+Math.ceil(Main.frame.getWidth()/128); ++xc) {
						gras.draw(g,xc*128,yc*128);
					}
		}
	}
}