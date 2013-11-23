import java.awt.Point;
import java.awt.Graphics;

public class Gras {

    int x, y;

    public Gras(int x, int y) {

    this.x = x;
    this.y = y;
    }

    public void draw(Graphics g, Point p, Point q) {

        g.drawImage(Core.images.gras, (int) (x+p.getX()*Chunk.size-q.getX()), (int) (y+p.getY()*Chunk.size-q.getY()), null);
    }

    public void update() {

        System.out.println("update");
    }
}