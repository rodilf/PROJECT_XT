import javax.imageio.ImageIO;
import java.io.File;
import java.lang.Exception;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;

public class Vilager {

	BufferedImage vilager;

	public Vilager() {
		try {
			vilager = ImageIO.read(new File("images/villager.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, Point p,Point q) {
		g.drawImage(vilager,(int)(3*128-q.getX()),(int)(3*128-q.getY()),null);
	}
}