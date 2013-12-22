import java.awt.Point;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class IOHandler {
    
    public Path world = FileSystems.getDefault().getPath("world");
    
    public IOHandler() {
        if(!world.toFile().exists())
            try {
                world = Files.createDirectory(world);   
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    public void saveZone(ConcurrentHashMap<Point, Chunk> zone, Point zonePos) throws IOException {
        File zoneFile = new File(world.toString(), "" + (int)zonePos.getX() + "," + (int)zonePos.getY());
        if(!zoneFile.exists())
            zoneFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(zoneFile.getAbsoluteFile()));
        for(Entry<Point, Chunk> chunk : zone.entrySet())
            bw.write(chunk.getValue().toString() + "\n");
        bw.close();
    }
    
    public ConcurrentHashMap<Point, Chunk> loadZone(Point zonePos) throws IOException, NullPointerException {
        File zoneFile = new File(world.toString(), "" + (int)zonePos.getX() + "," + (int)zonePos.getY());
        if(!zoneFile.exists())
            return new ConcurrentHashMap<Point, Chunk>();
        BufferedReader br = new BufferedReader(new FileReader(zoneFile.getAbsolutePath()));
        String line;
        Point key;
        Chunk value;
        ArrayList<Tre> tre = new ArrayList<Tre>();
        ArrayList<Vilager> vilager = new ArrayList<Vilager>();
        ArrayList<Gras> gras = new ArrayList<Gras>();
        ConcurrentHashMap<Point, Chunk> result = new ConcurrentHashMap<Point, Chunk>();
        while((line = br.readLine()) != null) {
            String values[] = line.split("\\{|\\}");
            key = new Point(Integer.parseInt(values[0].split("\\(|\\)")[1].split(",")[0]), Integer.parseInt(values[0].split("\\(|\\)")[1].split(",")[1]));
            if(!values[1].split("\\[|\\]")[1].split(";")[0].isEmpty())
                for(String point : values[1].split("\\[|\\]")[3].split(";")) {
                    point = point.replace("(", "");
                    point = point.replace(")", "");
                    tre.add(new Tre(Integer.parseInt(point.split(",")[0]), Integer.parseInt(point.split(",")[1])));
                }
            if(!values[1].split("\\[|\\]")[1].split(";")[0].isEmpty())
                for(String point : values[1].split("\\[|\\]")[1].split(";")) {
                    point = point.replace("(", "");
                    point = point.replace(")", "");
                    vilager.add(new Vilager(Integer.parseInt(point.split(",")[0]), Integer.parseInt(point.split(",")[1])));
                }
            for(int i = 0; i < Chunk.size/16; ++i) {
                for(int c = 0; c < Chunk.size/16; ++c) {
                    gras.add(new Gras(i*16, c*16));
                }
            }
            value = new Chunk(key, tre, vilager, gras);
            result.put(key, value);
        }
        br.close();
        return result;
    }
}
