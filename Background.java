import javax.swing.JPanel;

import java.util.List;
import java.util.ArrayList;

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
		List<Point> drawChunks = Main.loadHandler.drawChunks(Main.core.currentPoint);
        for(Point chunkPos : drawChunks) {
            Chunk chunk = Main.core.zones.get(new Point((int) chunkPos.getX()/16, (int) chunkPos.getY()/16)).get(chunkPos);
            for(int i = 0; i < chunk.gras.size(); ++i) 
            try {
                chunk.gras.get(i).draw(g2, chunk.pointer, Main.core.currentPoint);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }       
	}
}