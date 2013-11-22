import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.event.MouseInputAdapter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.ThreadGroup;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;

public class Core extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    ConcurrentHashMap<Point, ConcurrentHashMap<Point, Chunk>> zones = new ConcurrentHashMap<Point, ConcurrentHashMap<Point, Chunk>>();
    
	Point currentPoint = new Point(0,0);
	Point lastPoint = new Point(0,0);
	double x, y = 0;

	int cl = 0;
	
	public static Images images = new Images();

	SwingWorker<ConcurrentHashMap<Point, Chunk>, Void> peon;
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
						if((currentPoint.x%Chunk.size == 0 || currentPoint.y%Chunk.size == 0) && (loadGroup.activeCount() == 0)) {
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
		for(int i = (int)x-(int)Math.ceil(256/Chunk.size)-1; i < (int)x+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getWidth()/Chunk.size); ++i) {
			for(int c = (int)y-(int)Math.ceil(512/Chunk.size)-1; c < (int)y+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getHeight()/Chunk.size); ++c) {
				if(zones.containsKey(new Point(i/16, c/16)))
    			    if(zones.get(new Point(i/16, c/16)).containsKey(new Point(i, c))) 
    				    try {
    				        zones.get(new Point(i/16, c/16)).get(new Point(i, c)).draw(g2, currentPoint);
    				    }catch(Exception e) {
    				        e.printStackTrace();
    				    }
			}
		}

		g2.drawString("("+currentPoint.getX()+";"+currentPoint.getY()+")", 10, 10);
        g2.drawString("cl: " + cl, 10, 25);
        g2.drawString("zl: " + zones.size(), 10, 40);
    }

    public class loadChunks extends SwingWorker<ConcurrentHashMap<Point, Chunk>, Void> {
        @Override
        public ConcurrentHashMap<Point, Chunk> doInBackground() {
            ConcurrentHashMap<Point, Chunk> chunkBuffer = new ConcurrentHashMap<Point, Chunk>();
            Point currentPos = new Point((int)currentPoint.getX(), (int)currentPoint.getY());
            int sizeX, sizeY;
            x = currentPos.getX()/Chunk.size;
            if(x < 0)
                Math.floor(x);
            else
                Math.ceil(x);
            y = currentPos.getY()/Chunk.size;
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
            for(int i = (int)x-(int)Math.ceil(512/Chunk.size)-1; i < (int)x+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(sizeX/Chunk.size); ++i) {
                for(int c = (int)y-(int)Math.ceil(512/Chunk.size)-1; c < (int)y+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(sizeY/Chunk.size); ++c) {
                    zones.putIfAbsent(new Point(i/16, c/16), new ConcurrentHashMap<Point, Chunk>());
                    if(zones.get(new Point(i/16, c/16)).get(new Point(i, c)) == null) {
                        chunkBuffer.put(new Point(i, c), new Chunk(new Point(i, c)));
                        cl++;
                    }
                }
            }
            return chunkBuffer;
		}
        @Override
        public void done() {
            ConcurrentHashMap<Point, Chunk> chunkBuffer = new ConcurrentHashMap<Point, Chunk>();
            Point[] keys;
            try {
                chunkBuffer.putAll(get());
                keys = chunkBuffer.keySet().toArray(new Point[chunkBuffer.size()]);
                for(int i = 0; i < keys.length; ++i) {
                   zones.get(new Point(keys[i].x/16, keys[i].y/16)).put(keys[i], chunkBuffer.get(keys[i]));
                }
                
            } catch(Exception e) {
                e.printStackTrace();
            }    
        }
	}
	public void update() {
		if(peon.getState() == SwingWorker.StateValue.valueOf("DONE"))
		    (new loadChunks()).execute();
	}
}