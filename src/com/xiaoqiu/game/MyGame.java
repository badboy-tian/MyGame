package com.xiaoqiu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.xiaoqiu.util.LogHelper;

public class MyGame extends Game{
	private MainScreen ms;
	private GameScreen gs;
	@Override
	public void create() {
		ms = new MainScreen(this);
		gs = new GameScreen(this);
		
		setScreen(ms);
	}
	
	public MyGame() {
		super();
	}
	
	public GameScreen getGs() {
		return gs;
	}
	
	public MainScreen getMs() {
		return ms;
	}
	
	@Override
	public void dispose() {
		LogHelper.LogE("MyGame  dispose");
		super.dispose();
	}
}
