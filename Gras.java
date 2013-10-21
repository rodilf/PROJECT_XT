import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Gras {
	private int x = 0, y = 0;

	public Gras() {
	}

	public void draw(Graphics g, int a, int b) {

		x = a;
		y = b;
		g.drawImage(Core.images.gras, x, y, null);
	}

	public void update() {

		System.out.println("update");
	}
}