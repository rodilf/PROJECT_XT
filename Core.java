import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.ThreadGroup;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.Graphics;

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
	
	public static ThreadGroup loadGroup = new ThreadGroup("loadGroup");
	
	public Core() {

		villager.add(new Vilager());
		if(loadGroup.activeCount() == 0)
		    (new Thread(loadGroup, new loadChunks())).start();
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
		super.paintComponent(g);
		for(int i = 0; i < drawIndex.size(); i++) 
		    chunk.get(drawIndex.get(i)).draw(g, currentPoint);		    
	}
	public class loadChunks implements Runnable {
    	public void run() {
    	    
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
            if(chunkBuffer.size() != 0) {
                lock.lock();
                System.out.println("loadChunks locked");
                chunk.addAll(chunkBuffer);
                drawIndex.clear();
                drawIndex.addAll(indexBuffer);
                System.out.println(chunk.size() + " and " + chunkBuffer.size());
                lock.unlock();
                System.out.println("loadChunks unlocked");              
            }else if(currentPoint.x%128 == 0 || currentPoint.y%128 == 0) {
                lock.lock();
                drawIndex.clear();
                drawIndex.addAll(indexBuffer);
                lock.unlock();                
            }
            chunkBuffer.clear();
            indexBuffer.clear();
    	}
	}
	public void update() {
	    
		if(/*!(lastPoint.equals(currentPoint)) && */loadGroup.activeCount() == 0) {
		    (new Thread(loadGroup, new loadChunks())).start();
		    
			lastPoint.y = (int)currentPoint.getY();
			lastPoint.x = (int)currentPoint.getX();
		}
	}
}