package Util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
	
	public Image getImage(String path) throws SlickException{
		return new Image(path);
	}

}
