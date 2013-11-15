package com.xiaoqiu.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xiaoqiu.Listener.OnPaidListener;
import com.xiaoqiu.Listener.OnPassListener;
import com.xiaoqiu.start.MainActivity;
import com.xiaoqiu.util.CCPoint;
import com.xiaoqiu.util.Contants;
import com.xiaoqiu.util.CreateUtil;
import com.xiaoqiu.util.LogHelper;
import com.xiaoqiu.util.OpenPassword;
import com.xiaoqiu.util.ReadLevels;
import com.xiaoqiu.view.MyImage;

public class GameScreen implements Screen, OnPassListener , OnPaidListener{
	private SpriteBatch batch;
	private Stage stage, menu;
	private MyGame myGame;
	private Image background;

	private BitmapFont font;
	private Label label;

	private Image left, right, retry;

	private boolean BackHasTouched = false;

	private ArrayList<MyImage> items;

	private String[][] buttonsData;
	private Map<String, Texture> textures;

	private int what; //关卡数
	private int limit = 0;//限制的关卡
	private int number = 221;
	private boolean canDraw = true;
	// Assets
	private MyNewGroup newGroup, titleGroup;
	private Image mUnKnown, mCompleted;
	
	private ParticleEffect particle;

	public GameScreen(MyGame myGame) {
		this.myGame = myGame;
		what = MainActivity.getInstance().getCompleted();
		limit = MainActivity.getInstance().getLimit();
		items = new ArrayList<MyImage>();
		textures = new HashMap<String, Texture>();
		MainActivity.getInstance().setOnPaidListener(this);//设置支付监听器
		
		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("star.p"),
				Gdx.files.internal(""));
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
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0f, 0f, 0f, 0f);
		if (canDraw) {
			stage.act(Gdx.graphics.getDeltaTime());
			stage.getCamera().update();
			stage.draw();
		}

		if (!BackHasTouched && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			BackHasTouched = true;
			myGame.setScreen(myGame.getMs());
		}
		
		batch.begin();
		batch.setColor(Color.WHITE);
		particle.setPosition(360, 700);
		particle.draw(batch, arg0);
		batch.end();
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
		loadAsset();
		addListener();
	}

	private void loadAsset() {
		textures.put("blue", open("blue"));
		textures.put("gray_temp", open("gray_temp"));
		textures.put("green", open("green"));
		textures.put("orange", open("orange"));
		textures.put("yellow", open("yellow"));
		textures.put("hand", open("hand"));
		textures.put("unknown", open("unknown"));
		textures.put("completed", open("completed"));
		
		SoundManager.getIntance().loadSound();

		initView();
	}
	
	private Texture open(String name)
	{
		return OpenPassword.getTexture(name);
	}

	private void initView() {
		batch = new SpriteBatch();
		stage = new Stage(480, 800, false);

		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);

		// background
		background = new Image(open("backgroundnew"));
		background.setPosition(0, 0);
		background.setSize(480, 800);

		// label
		font = new BitmapFont(CreateUtil.getFileForFont("20th.fnt"),
				CreateUtil.getFileForFont("20th_0.png"), false);
		font.setScale(0.4f);
		label = new Label(what + "/" + number, new Label.LabelStyle(font,
				font.getColor()));
		label.setPosition(480 / 2 - label.getWidth() / 2, 650);

		Texture navigate = open("navigate");
		navigate.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// nav
		left = new Image(navigate);
		left.setSize(left.getWidth() / 2, left.getHeight() / 2);
		TextureRegion mm = new TextureRegion(navigate);
		mm.flip(true, false);
		right = new Image(mm);
		right.setSize(right.getWidth() / 2, right.getHeight() / 2);

		retry = new Image(open("retry"));
		retry.setSize(retry.getWidth() / 3, retry.getHeight() / 3);

		setActorPos(left, new CCPoint(65, 560));
		setActorPos(right, new CCPoint(480 - 65, 560));
		setActorPos(retry, new CCPoint(480 - 65, 650));
		
		stage.addActor(background);
		stage.addActor(label);
		stage.addActor(left);
		stage.addActor(right);
		stage.addActor(retry);

		generalTwoButtons();
	}

	private void generalTwoButtons() {

		ReadLevels levels = new ReadLevels();
		buttonsData = levels.getLevel(what);
		realeaseTitles();
		releaseButtons();

		if (mUnKnown != null) {
			stage.getRoot().removeActor(mUnKnown);
		}
		
		if (mCompleted != null) {
			stage.getRoot().removeActor(mCompleted);
		}
		
		int which =  MainActivity.getInstance().getCompleted();
		if (what > which) {//当前关卡大于已经完成的关卡时，显示问号
			mUnKnown = new Image(textures.get("unknown"));
			mUnKnown.setSize(mUnKnown.getWidth() / 2, mUnKnown.getHeight() / 2);
			mUnKnown.setPosition(240 - mUnKnown.getWidth() / 2, 560 - mUnKnown.getHeight() / 2);
			stage.addActor(mUnKnown);
		}else{
			generalTitles();// 生成标题
			if (what < which) {
				mCompleted = new Image(textures.get("completed"));
				mCompleted.setSize(mCompleted.getWidth() / 2.5f, mCompleted.getHeight() / 2.5f);
				mCompleted.setPosition(240 - mCompleted.getWidth() / 2, 560 - mCompleted.getHeight() / 2);
				stage.addActor(mCompleted);
			}
			generalButtons();// 生成下面的buttons
		}
	}

	private void generalTitles() {
		titleGroup = new MyNewGroup(this, buttonsData);
		titleGroup.setSize(label.getWidth() + 20, label.getWidth() + 20);
		titleGroup.setZIndex(20000);
		titleGroup.setPosition(240 - titleGroup.getWidth() / 2,
				560 - titleGroup.getHeight() / 2);
		titleGroup.setCanTouch(false);
		titleGroup.setDisplay(true);
		stage.addActor(titleGroup);
	}

	private void generalButtons() {
		newGroup = new MyNewGroup(this, buttonsData);
		newGroup.setZIndex(10000);
		newGroup.setSize(440, 440);
		newGroup.setPosition(20, 20);
		newGroup.setOnPassListener(this);
		stage.addActor(newGroup);
	}

	private void realeaseTitles() {
		if (titleGroup != null) {
			stage.getRoot().removeActor(titleGroup);
		}
	}

	private void releaseButtons() {
		if (newGroup != null) {
			stage.getRoot().removeActor(newGroup);
		}
	}

	private void addListener() {
		final Action up = CreateUtil.getUpAction();
		final Action down = CreateUtil.getDownAction();

		left.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				LogHelper.LogE("----------->up");

				if (what > 1) {
					what--;
					label.setText(what + "/" + number);
					generalTwoButtons();
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});

		right.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (what < limit) {
					what++;
					label.setText(what + "/" + number);
					generalTwoButtons();
				}else{
					MainActivity.getInstance().Dialog();
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});

		retry.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundManager.getIntance().play(Contants.RETRY);
				generalTwoButtons();
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	private void setActorPos(Actor actor, CCPoint p) {
		actor.setPosition(p.x - actor.getWidth() / 2, p.y - actor.getHeight()
				/ 2);
	}

	@Override
	public void pass() {
		particle.start();
		if (what < limit) {
			int which = MainActivity.getInstance().getCompleted();
			if (what < which) {
				what = which;
			}else{
				what++;
			}
			MainActivity.getInstance().setCompleted(what);
			label.setText(what + "/" + number);
			SoundManager.getIntance().play(Contants.SUCCEEDED);
			generalTwoButtons();
		}else{
			SoundManager.getIntance().play(Contants.SUCCEEDED);
			MainActivity.getInstance().customTip();
			MainActivity.getInstance().Dialog();
		}
	}
	
	public Map<String, Texture> getAssetManager() {
		return textures;
	}
	
	public int getWhat() {
		return what;
	}
	/**
	 * 支付成功后调用该函数刷新关卡数据
	 */
	@Override
	public void onPaid() {
		MainActivity.getInstance().setLimit(221);
		limit = MainActivity.getInstance().getLimit();
		pass();
	}
}
