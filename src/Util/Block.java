package Util;

import java.util.ArrayList;

/**
 * Created by Aleksander on 08/12/2014.
 */
public class Block extends Object{

    public enum BlockType{
        STONE,
        DIRT,
        WALL;
    }

    private int x;
    private int y;
    private BlockType blockType;
    // Properties

    public Block (int x, int y, BlockType b){
        this.x = x;
        this.y = y;
        this.blockType = b;
    }


    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BlockType getBlockType() {return blockType;}


}
