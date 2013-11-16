import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;

public class Vilager {

	public Vilager() {
	}

	public void draw(Graphics g, Point p, Point q) {

		g.drawImage(Core.images.vilager, (int)(3*128-q.getX()), (int)(3*128-q.getY()), null);
	}
}