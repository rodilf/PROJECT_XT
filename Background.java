import javax.swing.JPanel;

import java.awt.Point;
import java.awt.Graphics;

public class Background extends JPanel {

	/**
     *
     */
    private static final long serialVersionUID = 1L;

	public Background() {
	}

	public void paintComponent(Graphics g) {
	    Graphics g2 = g;
		super.paintComponent(g2);
		for(int i = (int)Main.core.x-(int)Math.ceil(256/Chunk.size)-1; i < (int)Main.core.x+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getWidth()/Chunk.size); ++i) {
			for(int c = (int)Main.core.y-(int)Math.ceil(512/Chunk.size)-1; c < (int)Main.core.y+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getHeight()/Chunk.size); ++c) {
				if(Main.core.chunk.containsKey(new Point(i, c)))
					for(int d = 0; d < Main.core.chunk.get(new Point(i, c)).gras.size(); ++d) {
				    	try {
				        	Main.core.chunk.get(new Point(i, c)).gras.get(d).draw(g, Main.core.chunk.get(new Point(i, c)).pointer, Main.core.currentPoint);
				    	} catch(Exception e) {
				        	e.printStackTrace();
				    	}
					}
			}
		}
	}
}