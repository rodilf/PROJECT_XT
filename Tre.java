import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;
import java.awt.Point;
import java.lang.Math;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tre {
	int x, y;

	public Tre() {
		x = (int)(Math.random()*129);
		y = (int)(Math.random()*129);
	}

	public void draw(Graphics g, Point p, Point q) {
		g.drawImage(Core.images.tre, (int) (x+p.getX()*128-q.getX()), (int) (y+p.getY()*128-q.getY()), null);
	}

	public void update() {
		System.out.println("update");
	}
}