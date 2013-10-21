import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.ThreadGroup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

public class Core extends JPanel {

	ArrayList<Chunk> chunk = new ArrayList<Chunk>();
	ArrayList<Point> point = new ArrayList<Point>();
	ArrayList<Vilager> villager = new ArrayList<Vilager>();
	ArrayList<Chunk> chunkBuffer = new ArrayList<Chunk>();
	ArrayList<Integer> drawIndex = new ArrayList<Integer>();
	ArrayList<Integer> indexBuffer = new ArrayList<Integer>();

	Point currentPoint = new Point(0,0);
	Point lastPoint = new Point(0,0);
	double x, y = 0;
	private final Lock lock = new ReentrantLock();
	public static Images images = new Images();
	SwingWorker peon;


	public static ThreadGroup loadGroup = new ThreadGroup("loadGroup");

	public Core() {
		villager.add(new Vilager());
		(peon = new loadChunks()).execute();
		Main.frame.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_UP) {
							currentPoint.y -= 8;
						}
						if(e.getKeyCode() == KeyEvent.VK_DOWN) {
							currentPoint.y += 8;
						}
						if(e.getKeyCode() == KeyEvent.VK_LEFT) {
							currentPoint.x -= 8;
						}
						if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
							currentPoint.x += 8;
						}
						if(e.getKeyCode() == KeyEvent.VK_SPACE) {
							System.out.println(chunk.size());
						}
						if((currentPoint.x%128 == 0 || currentPoint.y%128 == 0) && (loadGroup.activeCount() == 0)) {
						    (new Thread(loadGroup, new loadChunks())).start();
						}
					}
					public void keyReleased(KeyEvent e) {

					}
					public void keyTyped(KeyEvent e) {

					}
		});
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		super.paintComponent(g);
		for(int i = 0; i < drawIndex.size(); i++)
		    chunk.get(drawIndex.get(i)).draw(g, currentPoint);
		g.drawString("("+currentPoint.getX()+";"+currentPoint.getY()+")", 10, 10);
		g.drawString("cd: " + drawIndex.size(), 10, 20);
		g.drawString("cl: " + chunk.size(), 10, 30);
	}

	public class Pair<U, V> {
		private U first;
		private V second;

		public Pair(U first, V second) {
			this.first = first;
			this.second = second;
		}
	}

	public class loadChunks extends SwingWorker<Pair<ArrayList<Integer>, ArrayList<Chunk>>, Void> {
		@Override
    	public Pair<ArrayList<Integer>, ArrayList<Chunk>> doInBackground() {
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

            for(int i = (int)x-2; i < (int)x+2 + Math.ceil(Main.frame.getWidth()/128); ++i) {
                for(int c = (int)y-2; c < (int)y+2+Math.ceil(Main.frame.getHeight()/128); ++c) {
                    if(point.indexOf(new Point(i, c)) == -1) {
                        point.add(new Point(i, c));
                        chunkBuffer.add(new Chunk(point.get(point.size()- 1)));
                    }
                    indexBuffer.add(point.indexOf(new Point(i, c)));

                }
            }
            return new Pair<ArrayList<Integer>, ArrayList<Chunk>>(indexBuffer, chunkBuffer);
		}
		@Override
		public void done() {
            if(chunkBuffer.size() != 0) {
                drawIndex.clear();
                try {
                	drawIndex.addAll(get().first);
				} catch (Exception ignore) {
				}
				try {
					chunk.addAll(get().second);
				} catch (Exception ignore) {
				}
                System.out.println(chunk.size() + " and " + chunkBuffer.size());
            }else if((currentPoint.x%128 == 0 || currentPoint.y%128 == 0) && !lastPoint.equals(currentPoint)) {
                drawIndex.clear();
				try {
					drawIndex.addAll(get().first);
				} catch (Exception ignore) {
				}
            }
            lastPoint.x = (int)currentPoint.getX();
            lastPoint.y = (int)currentPoint.getY();
            chunkBuffer.clear();
            indexBuffer.clear();
    	}
	}
	public void update() {
		if(peon.getState() == SwingWorker.StateValue.valueOf("DONE"))
		    (new loadChunks()).execute();
	}
}