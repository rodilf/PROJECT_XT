import javax.swing.JPanel;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.Graphics;

public class Core extends JPanel {

	ArrayList<Chunk> chunk = new ArrayList<Chunk>();
	ArrayList<Point> point = new ArrayList<Point>();
	ArrayList<Vilager> villager = new ArrayList<Vilager>();
	Point currentPoint = new Point(0,0);
	Point lastPoint = new Point(0,0);
	double x,y = 0;
	public Core() {

		villager.add(new Vilager());

		for(int c=-3; c<6; ++c) {
			for(int i=-3; i<6; ++i) {
				point.add(new Point(c,i));
				chunk.add(new Chunk(point.get(point.size()-1)));
			}
		}

		Main.frame.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_UP) {
							currentPoint.y -= 8;
						}
						if (e.getKeyCode() == KeyEvent.VK_DOWN) {
							currentPoint.y += 8;
						}
						if (e.getKeyCode() == KeyEvent.VK_LEFT) {
							currentPoint.x -= 8;
						}
						if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
							currentPoint.x += 8;
						}
						if (e.getKeyCode() == KeyEvent.VK_SPACE) {
							System.out.println(chunk.size());
						}
					}
					public void keyReleased(KeyEvent e) {
					}
					public void keyTyped(KeyEvent e) {
					}
		});
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		x = currentPoint.getX()/128;
		if(x < 0)
			Math.floor(x);
		else
			Math.ceil(x);
		y = currentPoint.getY()/128;
		if(y < 0)
			Math.floor(y);
		else
			Math.ceil(y);
		for(int i=(int)x-2; i<(int)(x+2+Math.ceil(Main.frame.getWidth()/128)); ++i) {
			for(int c=(int)y-2; c<(int)(y+2+Math.ceil(Main.frame.getHeight()/128)); ++c) {
//				System.out.println("i: " + Integer.toString(i) + "c: " + Integer.toString(c));
				if(!(point.indexOf(new Point(i,c)) == -1)) {
					chunk.get(point.indexOf(new Point(i,c))).draw(g,currentPoint);
				} else update();
			}
		}
		g.drawString("(" + Double.toString(currentPoint.getX()) + " ; " + Double.toString(currentPoint.getY()) + ")",10,10);


	}

	public void update() {
		if(!(lastPoint.equals(currentPoint))) {
			x = currentPoint.getX()/128;
			if(x < 0)
				Math.floor(x);
			else
				Math.ceil(x);
			y = currentPoint.getY()/128;
			if(y < 0)
					Math.floor(y);
			else
				Math.ceil(y);
			for(int i=(int)x-2; i<(int)x+2+Math.ceil(Main.frame.getWidth()/128); ++i) {
				for(int c=(int)y-2; c<(int)y+2+Math.ceil(Main.frame.getHeight()/128); ++c) {
					if(point.indexOf(new Point(i,c)) == -1) {
						point.add(new Point(i,c));
						chunk.add(new Chunk(point.get(point.size()-1)));
					}
				}
			}
			lastPoint.y = (int)currentPoint.getY();
			lastPoint.x = (int)currentPoint.getX();
		}
	}
}