import java.util.ArrayList;
import java.awt.Point;
import java.awt.Graphics;

public class Chunk {

	public ArrayList<Tre> tre = new ArrayList<Tre>();
	public Point pointer = new Point();
	public Vilager vilager = new Vilager();

	public Chunk (Point p) {
		pointer = p;
		for(int c=0; c<20; ++c) {
				tre.add(new Tre());
		}
	}

	public void draw(Graphics g, Point q) {
			vilager.draw(g,pointer,q);
		for(int i=0; i<tre.size(); ++i) {
			tre.get(i).draw(g,pointer,q);
		}
	}
}