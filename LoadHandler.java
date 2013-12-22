import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class LoadHandler {
    
    public LoadHandler() {
        
    }
    
    /*
     * Returns the points referring to chunks that need to be drawn, which are the ones around the screen. If these doesn't exist, they are loaded thru loadChunk()
     */
    public List<Point> drawChunks(Point screenPos) {
        List<Point> result = new ArrayList<Point>();
        for(int i = (int)Main.core.x-(int)Math.ceil(256/Chunk.size)-1; i < (int)Main.core.x+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getWidth()/Chunk.size); ++i) {
            for(int c = (int)Main.core.y-(int)Math.ceil(512/Chunk.size)-1; c < (int)Main.core.y+(int)Math.ceil(640/Chunk.size)+1+Math.ceil(Main.frame.getHeight()/Chunk.size); ++c) {
                if(Main.core.zones.containsKey(new Point(i/16, c/16)))
                    if(Main.core.zones.get(new Point(i/16, c/16)).containsKey(new Point(i, c)))
                        result.add(new Point(i, c));
                    else {
                        Main.core.zones.get(new Point(i/16, c/16)).put(new Point(i, c), loadChunk(new Point(i, c)));
                        result.add(new Point(i, c));
                    }

            }
        }
        return result;
    }

    public Chunk loadChunk(Point chunkPos) {
        return new Chunk(chunkPos);
    }
}
