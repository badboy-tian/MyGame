package com.xiaoqiu.util;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xiaoqiu.start.MainActivity;
import com.xiaoqiu.view.MyImage;

public class CreateUtil {
	public static Image createButton(String str, float factor)
	{
		//Texture texture = new Texture(getFile(str));
		Texture texture = OpenPassword.getTexture(str);
		
		
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture);
		TextureRegionDrawable regionDrawable = new TextureRegionDrawable(region);
		
		Image button = new Image(regionDrawable);
		button.setSize(button.getWidth() / factor, button.getHeight() / factor);
		
		return button;
	}
	
	public static FileHandle getFile(String str) {
		return Gdx.files.internal("gfx/" + str + ".png");
	}
	
	public static String getFileName(String str)
	{
		return "gfx/" + str + ".png";
	}
	
	public static FileHandle getMusicFileName(String str)
	{
		return Gdx.files.internal("sfx/" + str + ".wav");
	}
	
	public static FileHandle getFileForFont(String str) {
		return Gdx.files.internal("fnt/" + str);
	}
	//给start键加上动画
	public static void addPlayAction(final Actor actor)
	{
		Action action = Actions.moveTo(240 - actor.getWidth() / 2, 
				300 - actor.getHeight() / 2, 0.6f);
		
		final Action big = Actions.scaleTo(1.0f, 0.9f, 1.0f);
		final Action small = Actions.scaleTo(1.0f, 1.0f, 1.0f);
		
		SequenceAction actions = Actions.sequence(action, Actions.run(new Runnable() {
			@Override
			public void run() {
				SequenceAction actionss = Actions.sequence(big, small);
				actor.addAction(Actions.repeat(RepeatAction.FOREVER, actionss));
			}
		}));
		actor.addAction(actions);
	}
	
	//按钮加上慢慢显示出来
	
	public static void AddApeare(Actor actor)
	{
		Action action1 = Actions.alpha(0);
		Action action2 = Actions.alpha(1, 0.8f);
		SequenceAction actions = Actions.sequence(action1, action2);
		actor.addAction(actions);
	}
	
	public static Action getUpAction()
	{
		Action big = Actions.scaleTo(1.0f, 1.0f, 0.1f);
		return big; 
	}
	
	public static Action getAction(float x, float time)
	{
		Action big = Actions.scaleTo(x, 1.0f, time);
		return big; 
	}
	
	public static Action getAction(float x, float y, float time)
	{
		Action big = Actions.scaleTo(x, y, time);
		return big; 
	}
	
	public static Action getDownAction()
	{
		Action small = Actions.scaleTo(0.9f, 0.9f, 0.1f);
		return small; 
	}
	
	public static SequenceAction getAppearActon(float factor)
	{
		SequenceAction action = Actions.sequence(Actions.scaleTo(0.4f, 0.4f), 
				Actions.scaleTo(1.0f, 1.0f, 0.2f * factor));
		return action;
	}
	
	public static Action getPassAction()
	{
		Action action = Actions.scaleTo(1.5f, 1.5f, 0.1f);
		return action;
	}
	
	public static SequenceAction getClickActon()
	{
		SequenceAction action = Actions.sequence(
				Actions.scaleTo(1.1f, 0.9f, 0.15f),
				Actions.scaleTo(0.92f, 1.08f, 0.15f),
				Actions.scaleTo(1.06f, 0.94f, 0.15f),
				Actions.scaleTo(0.96f, 1.04f, 0.15f),
				Actions.scaleTo(1.0f, 1.0f, 0.15f));
		return action;
	}
	
	public static MyImage getMyImage(Map<String, Texture> assetManager, CCPoint position
			,CCPoint size, CCPoint oright)
	{
		MyImage image = new MyImage(assetManager);
		image.setSize(size.x, size.y);
		image.setOrigin(oright.x, oright.y);
		image.setPosition(position.x, position.y);
		return image;
	}
	
	public static MyImage getMyImage(Map<String, Texture> assetManager, CCPoint position
			,CCPoint size, CCPoint oright,  int which)
	{
		MyImage image = new MyImage(assetManager);
		image.setSize(size.x, size.y);
		image.setOrigin(oright.x, oright.y);
		image.setPosition(position.x, position.y);
		image.setState(which + 1);
		
		return image;
	}
}
