import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.event.MouseInputAdapter;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;
import java.util.HashMap;
import java.lang.ThreadGroup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;

public class Core extends JPanel {

	HashMap chunk = new HashMap<Point, Chunk>();

	Point currentPoint = new Point(0,0);
	Point lastPoint = new Point(0,0);
	double x, y = 0;

	public static Images images = new Images();

	SwingWorker peon;
	Robot robot;

	static boolean mousePressed = false;
    boolean doneDone = true;
    boolean backgroundDone = true;

	public static ThreadGroup loadGroup = new ThreadGroup("loadGroup");

	public Core() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("awt error thrown");
		} catch (SecurityException e){
			System.err.println("SECURITY error thrown");
		}
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
		MotionListener motionListener = new MotionListener();
		Main.frame.addMouseListener(motionListener);
		Main.frame.addMouseMotionListener(motionListener);
	}

	private class MotionListener extends MouseInputAdapter {
		int button;
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				robot.mouseMove(Main.frame.getLocationOnScreen().x+Main.frame.getSize().width/2,Main.frame.getLocationOnScreen().y+Main.frame.getSize().height/2);
				button = e.getButton();
			}
		}
		public void mouseDragged(MouseEvent e) {
			if(button == MouseEvent.BUTTON1) {
				currentPoint.x += (e.getX()-Main.frame.getSize().width/2);
				currentPoint.y += (e.getY()-Main.frame.getSize().height/2);
				robot.mouseMove(Main.frame.getLocationOnScreen().x+Main.frame.getSize().width/2,Main.frame.getLocationOnScreen().y+Main.frame.getSize().height/2);
				update();
			}
		}
	}
	@Override
	public void paintComponent(Graphics g) {
	    Graphics g2 = g;
		g2.setColor(Color.RED);
		super.paintComponent(g2);
		for(int i = (int)x-2; i < (int)x+5 + Math.ceil(Main.frame.getWidth()/128); ++i) {
			for(int c = (int)y-4; c < (int)y+5+Math.ceil(Main.frame.getHeight()/128); ++c) {
				try {
					chunk.get(new Point(i, c)).draw(g2, currentPoint);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		g2.drawString("("+currentPoint.getX()+";"+currentPoint.getY()+")", 10, 10);
		g2.drawString("cd: " + drawIndex.size(), 10, 20);
        g2.drawString("cl: " + chunk.size(), 10, 30);
        g2.drawString("di: " + point.size(), 10, 40);
    }

    public class Pair<U, V> {
        private U first;
        private V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public class loadChunks extends SwingWorker<Void, Void> {
        @Override
        public void doInBackground() {
			int index;
            Point currentPos = new Point((int)currentPoint.getX(), (int)currentPoint.getY());
            int sizeX, sizeY;
            x = currentPos.getX()/128;
            if(x < 0)
                Math.floor(x);
            else
                Math.ceil(x);
            y = currentPos.getY()/128;
            if(y < 0)
                    Math.floor(y);
            else
                Math.ceil(y);

            if(Main.frame.getWidth() != 0) {
                sizeX = Main.frame.getWidth();
                sizeY = Main.frame.getHeight();
            }else {
                sizeX = 512;
                sizeY = 512;
            }
            int ce = 0;
            for(int i = (int)x-4; i < (int)x+5 + Math.ceil(sizeX/128); ++i) {
                for(int c = (int)y-4; c < (int)y+5+Math.ceil(sizeY/128); ++c) {
                    if(chunk.get(new Point(i, c)) == null) {
                        chunk.put(new Point(i, c), new Chunk(new Point(i, c)));
                    }
                }
            }
            return;
		}
	}
	public void update() {
		if(peon.getState() == SwingWorker.StateValue.valueOf("DONE"))
		    (new loadChunks()).execute();
	}
}