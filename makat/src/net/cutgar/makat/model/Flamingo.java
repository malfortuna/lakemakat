package net.cutgar.makat.model;

import net.cutgar.makat.PlayState;

import org.flixel.FlxSprite;
import org.flixel.FlxTimer;
import org.flixel.event.IFlxTimer;

public class Flamingo extends FlxSprite {
	
	static float timerRange = 5.0f;
	FlxTimer animSwitch = new FlxTimer();
	
	public enum STATE {IDLE, HEAD_DOWN, HEAD_UP, FEED_DOWN, FEED_UP};
	public STATE currentState = STATE.IDLE;
	
	public Flamingo(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:flamingo", true, true, 16, 16);
		
		addAnimation("idle", new int[]{0});
		addAnimation("head_down", new int[]{0,5,6,7}, 4, false);
		addAnimation("head_up", new int[]{7,6,5,0}, 4, false);
		addAnimation("feed_down", new int[]{0,1,2,3,4}, 4, false);
		addAnimation("feed_up", new int[]{4,3,2,1,0}, 4, false);
		
		play("idle");
		
		animSwitch = new FlxTimer();
		animSwitch.start(PlayState.rand.nextFloat() * timerRange, 1, new IFlxTimer() {
			
			@Override
			public void callback(FlxTimer Timer) {
				resetTimer();
				switchAnim();
			}
		});
		
	}
	
	private void switchAnim() {
		switch(currentState){
		case FEED_DOWN: currentState = STATE.FEED_UP; break;
		case HEAD_DOWN: currentState = STATE.HEAD_UP; break;
		case FEED_UP: 
		case HEAD_UP:
			if(PlayState.rand.nextBoolean()){
				currentState = STATE.HEAD_DOWN; break;
			}
			else{
				currentState = STATE.FEED_DOWN; break;
			}
		case IDLE:
			if(PlayState.rand.nextBoolean()){
				currentState = STATE.HEAD_DOWN; break;
			}
			else{
				currentState = STATE.FEED_DOWN; break;
			}
		}
		play(currentState.toString().toLowerCase());
	}

	private void resetTimer() {
		animSwitch.start(timerRange + PlayState.rand.nextFloat() * timerRange, 1, new IFlxTimer() {
			
			@Override
			public void callback(FlxTimer Timer) {
				switchAnim();
				resetTimer();
			}
		});
	}
	

	public void update(){
		super.update();
		animSwitch.update();
	}

}
