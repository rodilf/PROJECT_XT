import java.util.ArrayList;

import java.awt.Point;
import java.awt.Graphics;

public class Chunk {
    public static double size = 512;
	public ArrayList<Tre> tre = new ArrayList<Tre>();
	public Point pointer = new Point();
	public Vilager vilager = new Vilager();
	public ArrayList<Gras> gras = new ArrayList<Gras>();
	
	public Chunk (Point p) {

		pointer = p;
		for(int i = 0; i < (Math.pow((size/128), 2)*20); ++i) {
				tre.add(new Tre((int)(Math.random()*(size+1)), (int)(Math.random()*(size+1))));
		}
		for(int i = 0; i < size/16; ++i) {
			for(int c = 0; c < size/16; ++c) {
				gras.add(new Gras(i*16, c*16));
			}
		}
	}
	public void draw(Graphics g, Point q) {

			vilager.draw(g, pointer, q);
		for(int i = 0; i < tre.size(); ++i) {
			tre.get(i).draw(g, pointer, q);
		}
	}




}