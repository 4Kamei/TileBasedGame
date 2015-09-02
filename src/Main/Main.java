package Main;

import Util.Block;
import Util.ImageLoader;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends BasicGame{

    private Path.Step currrStep;

    private enum GameState {
		TITLEMENU,
        LOADING,
        PLAYING;
	}
    int cameraX = 0;
    int cameraY = 0;
    GameContainer gameContainer;
	GameState gamestate;
	boolean goingUp, goingDown, goingRight, goingLeft;
    ImageLoader il = new ImageLoader();
    Image img;
    Image background1;
    Image background2;
    Image background3;
    Image background4;
    Sprite Indicator;
    int state = 0;
    Sprite player;
    int lastx = 0;
    int lasty = 0;
    Map map;
    private Path path;
    Image imageStone;
    Image imageDirt;
    Image imageWall;
    boolean toggle;
    boolean toggleFullscreen;
    int pathStep;
    int fpscounter;
    public Main(String gamename)
	{
		super(gamename);
	}

   	@Override
	public void init(GameContainer gc) throws SlickException
	{
        Animation a1 = new Animation();
        a1.addFrame(il.getImage("/res/Indicator.png"),100);
        Indicator = new Sprite(a1, Sprite.EntityType.NA);
        imageStone = il.getImage("/res/Stone.png");
        imageDirt = il.getImage("/res/Dirt.png");
        imageWall = il.getImage("/res/Wall.png");
        gc.getInput().initControllers();
        gamestate = GameState.TITLEMENU;
        background1 = il.getImage("/res/Background1.png");
        background2 = il.getImage("/res/Background2.png");
        background3 = il.getImage("/res/Background3.png");
        background4 = il.getImage("/res/Background4.png");
        Animation a = new Animation();
        a.addFrame(background1,100);
        a.addFrame(background2,100);
        a.addFrame(background3,100);
        a.addFrame(background4,100);
        toggleFullscreen = false;
        player = new Sprite(a, Sprite.EntityType.PLAYER);
	    player.setX(20);
        player.setY(20);
        gameContainer = gc;
        toggle = false;
        gc.getInput().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if(Keyboard.KEY_UP == i && (gamestate == GameState.PLAYING) && (!goingUp))
                    goingUp = true;
                if(Keyboard.KEY_DOWN == i && (gamestate == GameState.PLAYING) && (!goingDown))
                    goingDown = true;
                if(Keyboard.KEY_LEFT == i && (gamestate == GameState.PLAYING) && (!goingLeft))
                    goingLeft = true;
                if(Keyboard.KEY_RIGHT == i && (gamestate == GameState.PLAYING) && (!goingRight))
                    goingRight = true;
            }

            @Override
            public void keyReleased(int i, char c) {
                if(Keyboard.KEY_UP == i && (gamestate == GameState.PLAYING) && (goingUp))
                    goingUp = false;
                if(Keyboard.KEY_DOWN == i && (gamestate == GameState.PLAYING) && (goingDown))
                    goingDown = false;
                if(Keyboard.KEY_LEFT == i && (gamestate == GameState.PLAYING) && (goingLeft))
                    goingLeft = false;
                if(Keyboard.KEY_RIGHT == i && (gamestate == GameState.PLAYING) && (goingRight))
                    goingRight = false;
            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {

            }

            @Override
            public void inputStarted() {

            }
        });
        gc.getInput().addMouseListener(new MouseListener() {
            @Override
            public void mouseWheelMoved(int i) {

            }

            @Override
            public void mouseClicked(int i, int i2, int i3, int i4) {
            }

            @Override
            public void mousePressed(int i, int i2, int i3) {

                if((lastx != 0) && (lasty != 0) && (path == null)) {
                    int intx;
                    int inty;
                    intx = (i2+ cameraX) / 32;
                    inty = (i3+ cameraY) / 32;
                    Indicator.setX(intx * 32);
                    Indicator.setY(inty * 32);
                    path = map.pathTo((lastx)/ 32, (lasty) / 32, (i2 - cameraX) / 32, (i3 - cameraY) / 32);
                    lastx = i2;
                    lasty = i3;
                    pathStep = 0;
                }else if(path == null){
                    lastx = i2;
                    lasty = i3;
                }
            }

            @Override
            public void mouseReleased(int i, int i2, int i3) {
            }

            @Override
            public void mouseMoved(int i, int i2, int i3, int i4) {

            }

            @Override
            public void mouseDragged(int i, int i2, int i3, int i4) {
            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {

            }

            @Override
            public void inputStarted() {

            }
        });
        gc.getInput().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if(Keyboard.KEY_RETURN == i && (gamestate != GameState.PLAYING)) {
                    System.out.println(gameContainer);
                    map = new Map(gameContainer.getWidth() / 32, gameContainer.getHeight() / 32, false);
                    gamestate = GameState.PLAYING;
                }else if((Keyboard.KEY_R == i ) && (gamestate == GameState.PLAYING)){
                    map = new Map(gameContainer.getWidth()/32,gameContainer.getHeight()/32, true);
                }else if((Keyboard.KEY_L == i ) && (gamestate == GameState.PLAYING)){
                    try {
                        map = new Map("Hello/res/Save.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if((Keyboard.KEY_F == i ) && (gamestate == GameState.PLAYING)){
                    if(toggleFullscreen){
                        try {
                            gameContainer.setFullscreen(true);
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                        toggleFullscreen = false;
                    }else{
                        try {
                            gameContainer.setFullscreen(false);
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                        toggleFullscreen = true;

                    }
                }else if((Keyboard.KEY_S == i ) && (gamestate == GameState.PLAYING)){
                    try {
                        map.saveMap(map);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedImage image = new BufferedImage(map.getWidthInTiles(),map.getHeightInTiles(),BufferedImage.TYPE_INT_RGB);
                    for (int ii = 0; ii != image.getWidth(); ii++) {
                        for (int jj = 0; jj != image.getHeight(); jj++) {
                            int r = 0;
                            int g = 0;
                            int b = 0;
                            switch (map.getBlock(ii,jj).getBlockType()){
                                case STONE:
                                    r = 255;
                                    break;
                                case DIRT:
                                    g = 255;
                                    break;
                                case WALL:
                                    b = 255;
                                    break;
                            }
                            int col = (r << 16) | (g << 8) | b;
                            image.setRGB(ii, jj, col);
                        }
                    }
                    try {
                        File savefile = new File("Hello/res/Save.png");
                        ImageIO.write(image, "png", savefile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if((Keyboard.KEY_Y == i ) && (gamestate == GameState.PLAYING)) {
                    System.out.println("OMGHOMGOMG");
                    BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
                    for (int ii = 0; ii != image.getWidth(); ii++) {
                        for (int jj = 0; jj != image.getHeight(); jj++) {
                            int r = (32/256) * ii;
                            int g = (32/256) * jj;
                            int b = (255);
                            int col = (r << 16) | (g << 8) | b;
                            image.setRGB(ii, jj, col);
                        }
                    }
                    try {
                        File savefile = new File("/res/Hello.png");
                        ImageIO.write(image, "png", savefile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(int i, char c) {}


            @Override
            public void setInput(Input input) {}

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {}

            @Override
            public void inputStarted() {}
        });
    }


	@Override
	public void update(GameContainer gc, int i) throws SlickException {
        fpscounter++;
        float cons = 1f * 1/gc.getFPS() * 100;
        if(goingUp){
            cameraY -= cons;
        }
        if(goingDown){
            cameraY += cons;
        }
        if(goingLeft){
            cameraX -= cons;
        }
        if(goingRight){
            cameraX += cons;
        }
        if(cameraX > 2000){
            cameraX = 2000;
        }
        if(cameraX < -2000){
            cameraX = -2000;
        }
        if(cameraY > 2000){
            cameraY = 2000;
        }
        if(cameraY < -2000){
            cameraY = -2000;
        }
        System.out.println(cameraX);
        System.out.println(cameraY);
    }


	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{

		if (gamestate == GameState.TITLEMENU){

            Integer y = gc.getHeight();
            Integer x = gc.getWidth();
            Integer imgTime = (int) gc.getTime()/500 % 4;
            switch (imgTime) {
                case 0:
                    img = background1;
                    break;
                case 1:
                    img = background2;
                    break;
                case 2:
                    img = background3;
                    break;
                case 3:
                    img = background4;
                    break;
            }
            for(int i = 0;i < x;i = i + 32)
            {
                for(int j = 0;j < y;j = j + 32)
                {
                    img.draw(i ,j);
                }
            }

            Image title = il.getImage("/res/Title.png");
            title = title.getScaledCopy(2);
            title.draw((gc.getWidth() - title.getWidth()) / 2,100);
		}else if(gamestate == GameState.PLAYING) {

            for (int i = 0; i != map.asArrayList().size(); i++) {
                Block b = map.asArrayList().get(i);
                if (b != null){
                    switch (b.getBlockType()) {
                        case STONE:
                            imageStone.draw((float) b.getX() * 32 + cameraX, (float) b.getY() * 32 + cameraY);

                            break;
                        case DIRT:
                            imageDirt.draw((float) b.getX() * 32 + cameraX, (float) b.getY() * 32 + cameraY);

                            break;
                        case WALL:
                            imageWall.draw((float) b.getX() * 32 + cameraX, (float) b.getY() * 32 + cameraY);

                            break;
                    }

                }
            }
            Animation texture = player.getTexture();

            if((fpscounter*6 >= gc.getFPS()) && (path != null) && (pathStep != path.getLength())){
                fpscounter = 0;
                if(state == 1){
                    state = 0;
                }else{
                    state = 1;
                }
                System.out.println(pathStep);
                currrStep = path.getStep(pathStep);
                player.setY(currrStep.getY()*32 + cameraY);
                player.setX(currrStep.getX()*32 + cameraX);
                pathStep++;
                if (pathStep == path.getLength()){
                    path = null;
                }
            }

            texture.draw((float) player.getX() + cameraX, (float) player.getY() + cameraY);
            if (Indicator != null){
                Indicator.getTexture().draw((float) Indicator.getX() + cameraX ,(float) Indicator.getY() + cameraY);

            }
        }
    }

public static void main(String[] args)
        {
        try
        {
        AppGameContainer appgc;
        appgc = new AppGameContainer(new Main("Lorem Ipsum"));
        appgc.setDisplayMode(1280, 800, false);
        appgc.setUpdateOnlyWhenVisible(false);
        System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation());
        appgc.start();
        appgc.getGraphics().setBackground(Color.cyan);
        }
        catch (SlickException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
