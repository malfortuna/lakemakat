package net.cutgar.makat.model;

import org.flixel.FlxSprite;

public class Splash extends FlxSprite {

	public Splash(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:splash", true, true, 8, 8);
		addAnimation("splash", new int[]{0,1,2,3,4,5,6}, 12, false);
		
		velocity.x = -80;
	}
	
	public void splash(){
		play("splash");
	}
	
}
