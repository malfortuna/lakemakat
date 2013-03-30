package net.cutgar.makat;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.cutgar.makat.model.Flamingo;
import net.cutgar.makat.model.Hyena;

import org.flixel.FlxBasic;
import org.flixel.FlxEmitter;
import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxObject;
import org.flixel.FlxRect;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxTileblock;

public class PlayState extends FlxState
{
	public static Random rand = new Random();
	private Hyena player;
	
	private FlxGroup flamingos_back = new FlxGroup();
	private FlxGroup flamingos_fore = new FlxGroup();
	private FlxGroup riverbed = new FlxGroup();
	
	public static List<FlxEmitter> splashes = new LinkedList<FlxEmitter>();
	
	private int totalWidth;
	
	@Override
	public void create()
	{
		FlxG.setBgColor(0xff43C0DA);
		FlxG.playMusic("music.mp3", 0.2f);
		FlxG.play("water.mp3", 0.5f, true);
		
		for(int i=0; i<6; i++){
			Flamingo f = new Flamingo((int)(Math.random() * FlxG.width), FlxG.height-24);
			if(PlayState.rand.nextBoolean())
				f.setFacing(FlxSprite.LEFT);
			flamingos_back.add(f);
		}
		add(flamingos_back);
		
		int xin = 0;
		for(int i=0; i<4; i++){
			FlxTileblock floor = new FlxTileblock(xin, FlxG.height-8, FlxG.width, 8);
			floor.makeGraphic(150 + (int)(Math.random()*150), 8, 0xff5DD7E2);
			riverbed.add(floor);
			xin += floor.width;
		}
		totalWidth = xin;
		add(riverbed);
		
		player = new Hyena(30, 100);
		add(player);
		
		for(int i=0; i<6; i++){
			FlxEmitter splash = new FlxEmitter(-5, -5);
			splash.makeParticles("pack.atlas:water", 15, 0);
			splash.setYSpeed(-40, -10);
			splash.gravity = 50;
			add(splash);
			splashes.add(splash);
		}
		
		
		FlxG.camera.follow(player);
		FlxG.camera.bounds = new FlxRect(0, 0, 240, 120);
		
		for(int i=0; i<5; i++){
			Flamingo f = new Flamingo((int)(Math.random() * FlxG.width), FlxG.height-16);
			if(PlayState.rand.nextBoolean())
				f.setFacing(FlxSprite.LEFT);
			flamingos_fore.add(f);
		}
		add(flamingos_fore);
		
	}
	
	public void update(){
		super.update();
		FlxG.collide(riverbed, player);
		
		FlxG.camera.bounds.x = player.x - 30;
		FlxG.worldBounds.x = player.x - 30;
		
		for(FlxBasic b : flamingos_back.members){
			FlxObject f = (FlxObject) b;
			if(f.x < player.x-30-16)
				f.x += FlxG.width+16 + (int)(Math.random() * 100);
		}
		for(FlxBasic b : flamingos_fore.members){
			FlxObject f = (FlxObject) b;
			if(f.x < player.x-30-16)
				f.x += FlxG.width+16 + (int)(Math.random() * 100);
		}
		
		for(FlxBasic b : riverbed.members){
			FlxObject f = (FlxObject) b;
			if(f.x+f.width < player.x-30-16){
				f.x += totalWidth;
			}
		}
	}
}