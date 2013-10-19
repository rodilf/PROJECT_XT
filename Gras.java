import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Gras {

	BufferedImage gras;
	private int x = 0, y = 0;

	public Gras() {
	    
		try {
			gras = ImageIO.read(new File("images/grass.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("gras");
	}

	public void draw(Graphics g, int a, int b) {
	    
		x = a;
		y = b;
		g.drawImage(gras, x, y, null);
	}

	public void update() {
	    
		System.out.println("update");
	}
}