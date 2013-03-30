package net.cutgar.makat.model;

import net.cutgar.makat.PlayState;

import org.flixel.FlxEmitter;
import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Hyena extends FlxSprite {
	
	public int speed = 70;
	private boolean jumping;
	private boolean firedSplash;

	public Hyena(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:hyena", true, true, 16, 16);
		
		addAnimation("run", new int[]{0,1,2,3,4,5}, 8, true);
		addAnimation("jump", new int[]{0}, 8, true);
		play("run");
		
		acceleration.y = 200;
		velocity.x = 80;
	}
	
	public void update(){
		if(isTouching(FLOOR)){
			if(jumping){
				jumping = !jumping;
			}
			if(FlxG.keys.UP){
				velocity.y = -70;
				jumping = true;
			}
			play("run");
			if(!firedSplash && (_curFrame == 5 || _curFrame == 2)){
				firedSplash = true;
				FlxEmitter s = PlayState.splashes.remove(0);
				if(_curFrame == 2){
					s.x = this.x+8;
					s.y = this.y+16;
				}
				else{
					s.x = this.x+16;
					s.y = this.y+16;
				}
				
				if(Math.random() < 0.5)
					FlxG.play("wading.mp3");
				else
					FlxG.play("wading2.mp3");
				s.start(true);
				PlayState.splashes.add(PlayState.splashes.size(), s);
			}
			else if(_curFrame == 0 || _curFrame == 3){
				firedSplash = false;
			}
		}
		else
			play("jump");
	}
	
}
