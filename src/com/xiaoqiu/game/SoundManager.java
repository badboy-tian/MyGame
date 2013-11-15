package com.xiaoqiu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.xiaoqiu.start.MainActivity;
import com.xiaoqiu.util.Contants;
import com.xiaoqiu.util.CreateUtil;

public class SoundManager {
	private static SoundManager _instance= null;
	private AssetManager assetManager;
	private Sound[] sounds;
	
	public SoundManager()
	{
		
	}
	
	public static SoundManager getIntance()
	{
		if (_instance == null) {
			_instance = new SoundManager();
		}
		
		return _instance;
	}
	
	public void loadSound()
	{
		sounds = new Sound[Contants.SOUNDCOUNT];
		sounds[Contants.PUT] = Gdx.audio.newSound(CreateUtil.getMusicFileName("put"));
		sounds[Contants.RETRY] = Gdx.audio.newSound(CreateUtil.getMusicFileName("retry"));
		sounds[Contants.SUCCEEDED] = Gdx.audio.newSound(CreateUtil.getMusicFileName("succeeded"));
		sounds[Contants.REVERT] = Gdx.audio.newSound(CreateUtil.getMusicFileName("revert"));
		//sounds[Contants.FINISH] = Gdx.audio.newSound(CreateUtil.getMusicFileName("finish"));
	}
	
	public void play(int id)
	{
		if (MainActivity.getInstance().getMusic()) {
			sounds[id].play();
		}
	}
}
