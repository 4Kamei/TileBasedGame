package Main;

import Util.Block;
import com.sun.xml.internal.stream.writers.WriterUtility;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Aleksander on 12/12/2014.
 */
public class Map implements TileBasedMap{

    private int width;
    private int height;
    private Block[][] map;

    public Map (String file) throws IOException {
        Block.BlockType b;
        b = Block.BlockType.STONE;
        Color c;
        File f = new File(file);
        System.out.println(f.getAbsoluteFile());
        BufferedImage image = ImageIO.read(f);
        this.width = image.getWidth();
        this.height = image.getHeight();
        map = new Block[width][height];
        for(int i = 0; i < image.getWidth();i++){
            for(int j = 0; j < image.getHeight();j++){
                c = new Color(image.getRGB(i,j));
                if(c.getBlue() == 255)
                    b = Block.BlockType.WALL;
                if(c.getRed() == 255)
                    b = Block.BlockType.STONE;
                if(c.getGreen() == 255)
                    b = Block.BlockType.DIRT;
                System.out.println(i + ", " + j + " of type " + b);
                map[i][j] = new Block(i,j, b);
            }
        }
    }
    public Map (String file, int width, int height) throws IOException {
        //ImageIO.read
        String line;
        String[] block;
        Block.BlockType type = null;
        Block blockl;
        ArrayList<Block> blocks = new ArrayList<Block>();
        int x = 0;
        int y = 0;
        if(file != null){
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = " ";
            while(line != null){
                line = reader.readLine();
                if(line != null) {
                    block = line.split(",");
                    if (block[2].equals("DIRT"))
                        type = Block.BlockType.DIRT;
                    if (block[2].equals("STONE"))
                        type = Block.BlockType.STONE;
                    if (block[2].equals("WALL")) {
                        type = Block.BlockType.WALL;
                    }
                    if (Integer.valueOf(block[0]) > x){
                        x = Integer.valueOf(block[0]);
                    }
                    if (Integer.valueOf(block[1]) > y){
                        y = Integer.valueOf(block[1]);
                    }
                    blockl = new Block(Integer.valueOf(block[0]), Integer.valueOf(block[1]), type);
                    blocks.add(blockl);
                }
            }
            this.map = getRandomMap(width,height);
            for(Block b : blocks){
                map[b.getX()][b.getY()] = b;
            }
        }
    }

    public Map (int width, int height, boolean randomise){
        if(randomise){
            this.map = getRandomMap(width,height);
        }else{
            this.map = getBlankMap(width,height);
        }
    }


    private Block[][] getRandomMap(int width, int height){
        this.width = width;
        this.height = height;
        map = new Block[width][height];
        for(int i = 0; i < width;i++){
            for(int j = 0; j < height;j++){
                    if(Math.random() > 0.1){
                        map[i][j] = new Block(i,j, Block.BlockType.DIRT);
                    }else if(Math.random() > 0.1){
                        map[i][j] = new Block(i,j, Block.BlockType.STONE);
                    }else {
                        map[i][j] = new Block(i,j, Block.BlockType.WALL);
                    }
            }
        }
        return map;
    }

    private Block[][] getBlankMap(int width, int height){
        this.width = width;
        this.height = height;

        System.out.println(this.width);
        System.out.println(this.height);
        map = new Block[width][height];
        for(int i = 0; i < width;i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Block(i,j, Block.BlockType.DIRT);
            }
        }
        return map;
    }
    public void saveMap(Map map) throws IOException {
        if(this != null){
            Writer printWriter = new FileWriter("map.txt",false);
            for(Block block : map.asArrayList())
                printWriter.write(block.getX() + "," + block.getY() + "," + block.getBlockType().toString() + "\n");
            printWriter.close();

        }

    }
    public Block getBlock(int x, int y){
        return map[x][y];
    }


    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
    }

    public Path pathTo(int sx, int sy, int tx, int ty){
        AStarPathFinder pathFinder = new AStarPathFinder(this, Integer.MAX_VALUE, false);
        return pathFinder.findPath(null,sx,sy,tx,ty);
    };

    public ArrayList<Block> asArrayList(){
        ArrayList<Block> blockList = new ArrayList<Block>();
        for(Block[] b : map){
            for(Block b1 : b){
                blockList.add(b1);
            }
        }
        return blockList;
    }
    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int x, int y) {
        if(map[x/32][y/32].getBlockType() == Block.BlockType.WALL){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int x, int y) {
        if(map[x][y].getBlockType() == Block.BlockType.WALL){
            return Float.POSITIVE_INFINITY;
        }else if(map[x][y].getBlockType() == Block.BlockType.STONE){
            return 10;
        }else if(map[x][y].getBlockType() == Block.BlockType.DIRT) {
            return 1;
        }else{
            return 0;
        }
    }
}
