import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;
import java.awt.Point;
import java.lang.Math;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tre {

	BufferedImage tre;
	int x, y;

	public Tre() {
		x = (int)(Math.random()*129);
		y = (int)(Math.random()*129);
		try {
			tre = ImageIO.read(new File("images/tree.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, Point p, Point q) {
		g.drawImage(tre,(int)(x+p.getX()*128-q.getX()), (int)(y+p.getY()*128-q.getY()), null);
	}

	public void update() {
		System.out.println("update");
	}
}