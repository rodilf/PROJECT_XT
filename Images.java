import javax.imageio.ImageIO;

import java.io.File;
import java.lang.Exception;

import java.awt.image.BufferedImage;

public class Images {
	BufferedImage tre, vilager, gras;
	public Images() {
		try {
			tre = ImageIO.read(new File("images/tree.png"));
			vilager = ImageIO.read(new File("images/villager.png"));
			gras = ImageIO.read(new File("images/grass.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
