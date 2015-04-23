package Main;

import Util.Block;
import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import java.util.ArrayList;

import static Util.Block.BlockType;

/**
 * Created by Aleksander on 07/12/2014.
 */
public class Sprite {
    double x;
    double y;
    Animation animation;
    EntityType type;
    public enum EntityType {
        PLAYER,
        PASSIVE,
        AGGRESIVE,
        NEUTRAL,
        NA;
    }
    public Sprite(Animation texture, EntityType entityType) {
        this.type = entityType;
        this.animation = texture;
    }

    public Sprite(){

    }
    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setSprite(Animation texture){
        this.animation = texture;
   }

    public void addFrame(Image i, int duration){
        this.animation.addFrame(i, duration);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Animation getTexture() {
        return animation;
    }



}
