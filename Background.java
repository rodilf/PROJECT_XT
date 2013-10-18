import javax.swing.JPanel;

import java.awt.Graphics;

public class Background extends JPanel {

	Gras gras = new Gras();
	int x = 0;
	int c = 0;

	public void Background() {
		//24 16
	}

	public void paintComponent(Graphics g) {
		++x;
		--c;
		super.paintComponent(g);
		for (int yc=-2; yc<2+Math.ceil(Main.frame.getHeight()/128); ++yc) {
					for (int xc=-2; xc<2+Math.ceil(Main.frame.getWidth()/128); ++xc) {
						gras.draw(g,xc*128,yc*128);
					}
		}
		g.drawString(Integer.toString(c),x,10);
	}
}