import java.awt.Graphics;
import java.awt.Point;

public class Vilager {
    int x, y;

    public Vilager(int x, int y) {

        this.x = x;
        this.y = y;
    }

	public Vilager() {
	}

	public void draw(Graphics g, Point p, Point q) {

	    g.drawImage(Core.images.vilager, (int) (x+p.getX()*Chunk.size-q.getX()), (int) (y+p.getY()*Chunk.size-q.getY()), null);
	}
	@Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}