package com.xiaoqiu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.xiaoqiu.start.MainActivity;
import com.xiaoqiu.util.CreateUtil;
import com.xiaoqiu.util.OpenPassword;

public class MainScreen implements Screen {
	TextureRegion background;
	SpriteBatch batch;
	private Stage stage;
	private int width;
	private int height;

	private TextureRegion title;
	private Image btn, left, right;//, start;
	
	private Image start;
	private MyGame game;

	private boolean BackHasTouched = false;
	
	public MainScreen(MyGame game) {
		this.game = game;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float arg0) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if (!BackHasTouched && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			BackHasTouched = true;
			System.exit(0);
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		BackHasTouched = false;
		initView();
		addListener();
	}
	
	private void initView()
	{
		width = 480;
		height = 800;
		batch = new SpriteBatch();
	//	background = new TextureRegion(new Texture(
//				CreateUtil.getFile("backgroundnew")));
		background = new TextureRegion(
				OpenPassword.getTexture("backgroundnew"));
		
		Image image = new Image(background);
		image.setPosition(0, 0);
		image.setSize(480, 800);
		// title
		btn = CreateUtil.createButton("hualiuliu", 2);
		btn.setPosition(240 - btn.getWidth() / 2, 600 - btn.getHeight()  /2 );
		
		// start
		//start = new Image(new Texture(CreateUtil.getFile("play")));
		start = new Image(OpenPassword.getTexture("play"));
		start.setSize(start.getWidth() / 2.6f, start.getHeight() / 2.5f);
		start.setOrigin(start.getWidth() / 2, start.getHeight() / 2);
		start.setPosition(240 - start.getWidth() / 2,
				0);
		CreateUtil.addPlayAction(start);

		// left
		left = CreateUtil.createButton("giftiz", 1.4f);
		left.setPosition(10, 10);
		//left.setSize(left.getWidth() / 1.5f, left.getHeight() / 1.5f);
		left.setOrigin(left.getWidth() / 2, left.getHeight() / 2);
		CreateUtil.AddApeare(left);
		
		// right
		right = CreateUtil.createButton("settings", 1.4f);
		right.setSize(left.getWidth(), left.getHeight());
		right.setPosition(480 - 10 - right.getWidth(), left.getY());
		right.setOrigin(left.getWidth() / 2, left.getHeight() / 2);
		CreateUtil.AddApeare(right);
		
		stage = new Stage(480, 800, false);
		stage.addActor(image);
		stage.addActor(btn);
		stage.addActor(start);
		stage.addActor(left);
		stage.addActor(right);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void addListener()
	{
		start.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.getGs());
				
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		final Action up = CreateUtil.getUpAction();
		final Action down = CreateUtil.getDownAction();
		
		left.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				left.clearActions();
				left.addAction(down);
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				left.addAction(up);
				MainActivity.getInstance().statrtUmeng();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		right.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				left.clearActions();
				right.addAction(down);
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				right.addAction(up);
				MainActivity context = MainActivity.getInstance();
				
				if (context.getMusic()) {
					context.setMusic(false);
					context.Tip("音效已关闭！，再次点击开启");
				}else{
					context.setMusic(true);
					context.Tip("音效已开启！");
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}
