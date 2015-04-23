package Util;

import org.newdawn.slick.Image;

public class Button {
	
	Image texture;
	int x;
	int y;
	public Button(Image texture, int x, int y){
		this.texture = texture;
		this.x = x;
		this.y = y;
	}

	public int GetX(){
		return this.x;
	}
	
	public int GetY(){
		return this.y;
	}
}
