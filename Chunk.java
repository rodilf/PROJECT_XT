import java.util.ArrayList;

import java.awt.Point;
import java.awt.Graphics;

public class Chunk {
    public static double size = 512;
	public ArrayList<Tre> tre = new ArrayList<Tre>();
	public Point pointer = new Point();
	public ArrayList<Vilager> vilager = new ArrayList<Vilager>();
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
	    for(Vilager vilager : this.vilager)
			vilager.draw(g, pointer, q);
		for(Tre tre : this.tre) {
			tre.draw(g, pointer, q);
		}
	}
	@Override
	public String toString() {
	    String result = "vilager = [";
	    for(int i = 0; i < vilager.size(); ++i) {
	        result += vilager.get(i).toString();
	        if(i != vilager.size()-1)
	            result += ", ";
	    }
	    result += "]";
	    result += "tre = [";
       for(int i = 0; i < tre.size(); ++i) {
            result += tre.get(i).toString();
            if(i != tre.size()-1)
                result += ", ";
        }
        result += "]";
	    return result;
	}




}