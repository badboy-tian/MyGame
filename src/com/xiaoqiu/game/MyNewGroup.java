package com.xiaoqiu.game;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.xiaoqiu.Listener.OnClickListener;
import com.xiaoqiu.Listener.OnPassListener;
import com.xiaoqiu.start.MainActivity;
import com.xiaoqiu.util.CCPoint;
import com.xiaoqiu.util.Contants;
import com.xiaoqiu.util.CreateUtil;
import com.xiaoqiu.util.LogHelper;
import com.xiaoqiu.util.OpenPassword;
import com.xiaoqiu.util.Rule;
import com.xiaoqiu.view.MyImage;

public class MyNewGroup extends Group implements OnClickListener {
	//private AssetManager assetManager;
	private Map<String, Texture> textures;
	private String[][] buttonsData;
	private String[][] userData;
	private boolean isCan = false;
	private float w, h;
	private ArrayList<MyImage> items;
	private boolean canTouch = true;
	private boolean isDisplay = false;
	private OnPassListener onPassListener;
	private MyImage sender;
	private MyImage preButton;
	private Stack<MyImage> mStack;
	private Stack<Integer> backStack;
	private boolean isClick = false;
	private GameScreen gameScreen;
	private Image preCur, currCur;

	public MyNewGroup(GameScreen gameScene, String[][] buttonsData) {
		this.textures = gameScene.getAssetManager();
		this.buttonsData = buttonsData;
		this.gameScreen = gameScene;
		preCur = new Image(OpenPassword.getTexture("cursor"));
		currCur = new Image(OpenPassword.getTexture("cursor"));
		items = new ArrayList<MyImage>();
		mStack = new Stack<MyImage>();
		backStack = new Stack<Integer>();
	}

	void initView() {
		w = getWidth();
		h = getHeight();

		int rows = buttonsData.length;
		int colls = buttonsData[0].length;
		userData = new String[rows][colls];
		clearUserData();

		float length = w / colls;// 480 - 10 - 20 - 20
		if (isDisplay) {// 显示颜色 用于title
			if (rows == 1) {// 当只有一行的时候
				if (colls == 1) {
					MyImage temp = CreateUtil.getMyImage(textures,
							new CCPoint(0, 0), new CCPoint(w, h), new CCPoint(
									length / 2, length / 2), 1);
					items.add(temp);
				} else {
					for (int i = 0; i < colls; i++) {
						float y = h / 2 - length / 2;
						float x = i * length;

						MyImage temp = CreateUtil.getMyImage(textures,
								new CCPoint(x, y), new CCPoint(length, length),
								new CCPoint(length / 2, length / 2), 1);
						items.add(temp);
					}
				}
			} else {
				for (int i = rows - 1; i >= 0; i--) {
					for (int j = 0; j < colls; j++) {
						float x = j * length;
						float y = (rows - 1 - i) * length;

						System.out.print(buttonsData[i][j]);

						String str = buttonsData[i][j];

						if (str.equals(".")) {

						} else {
							MyImage temp = CreateUtil.getMyImage(textures,
									new CCPoint(x, y), new CCPoint(length,
											length), new CCPoint(length / 2,
											length / 2), Integer.parseInt(str));
							items.add(temp);
						}
					}
					System.out.print("\n");
				}
			}
		} else {// 下面的大标题，没有显示颜色
			if (rows == 1) {// 当只有一行的时候
				if (colls == 1) {
					LogHelper.LogE("--------" + buttonsData[0][0]);

					MyImage temp = CreateUtil.getMyImage(textures,
							new CCPoint(0, 0), new CCPoint(w, h), new CCPoint(
									length / 2, length / 2));
					temp.setCCPoint(new CCPoint(0, 0));
					temp.setOnClickListener(this);
					items.add(temp);
				} else {
					for (int i = 0; i < colls; i++) {
						float y = h / 2 - length / 2;
						float x = i * length;

						MyImage temp = CreateUtil.getMyImage(textures,
								new CCPoint(x, y), new CCPoint(length, length),
								new CCPoint(length / 2, length / 2));
						temp.setCCPoint(new CCPoint(i, 0));
						temp.setOnClickListener(this);
						items.add(temp);
					}
				}
			} else {
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < colls; j++) {
						float x = j * length;
						float y = (rows - 1 - i) * length;
						System.out.println("----->i=" + i + ",j=" + j);
						String str = buttonsData[i][j];
						if (str.equals(".")) {
							userData[i][j] = ".";
						} else {
							if (str.equals("0")) {
								userData[i][j] = "0";
							}
							MyImage temp = CreateUtil.getMyImage(textures,
									new CCPoint(x, y), new CCPoint(length,
											length), new CCPoint(length / 2,
											length / 2));

							temp.setCCPoint(new CCPoint(j, i));
							temp.setOnClickListener(this);
							items.add(temp);
						}
					}
				}
			}
		}

		for (int i = 0; i < items.size(); i++) {
			MyImage temp = items.get(i);
			if (!isDisplay) {
				temp.addAction(CreateUtil.getAppearActon(0.4f * (i + 1)));
			}
			this.addActor(temp);
		}
	}

	@Override
	public void act(float delta) {
		if (!isCan) {
			initView();

			if (!isDisplay) {
				float length = items.get(0).getWidth();
				Image hand = new Image(textures.get("hand"));
				hand.setSize(hand.getWidth() / 2.5f, hand.getHeight() / 2.5f);
				hand.setPosition(items.get(0).getX() + length / 2, items.get(0)
						.getY());

				Action a1 = Actions.moveTo(items.get(0).getX()
						+ items.get(0).getWidth() / 2,
						items.get(0).getY() - 25, 0.5f);

				Action actionremove = Actions.removeActor();
				Action actionout = Actions.fadeOut(0.8f);

				SequenceAction actions = null;

				if (gameScreen.getWhat() == MainActivity.getInstance()
						.getCompleted()) {

					if (gameScreen.getWhat() == 1) {// 在第一关提示
						actions = Actions.sequence(a1, actionout, actionremove);
						hand.addAction(actions);
						this.addActor(hand);
					} else if (gameScreen.getWhat() == 2) {

						Action action = Actions.moveTo(items.get(1).getX()
								+ items.get(1).getWidth() / 2, items.get(1)
								.getY() - 25, 1);
						actions = Actions.sequence(a1, action, actionout,
								actionremove);
						hand.addAction(actions);
						this.addActor(hand);

					} else if (gameScreen.getWhat() == 6) {// 在第六关提示
						Action go1 = Actions.moveTo(items.get(2).getX()
								+ length / 2, items.get(2).getY(), 0.8f);

						Action go2 = Actions.moveTo(items.get(3).getX()
								+ length / 2, items.get(3).getY() + 10, 0.8f);

						Action go3 = Actions.moveTo(items.get(1).getX()
								+ length / 2, items.get(1).getY() + length / 2
								- hand.getHeight(), 0.8f);

						Action go4 = Actions.moveTo(items.get(0).getX()
								+ length / 2, items.get(0).getY() + length / 2
								- hand.getHeight(), 0.8f);

						actions = Actions.sequence(go1, go2, go3, go4,
								actionout, actionremove);
						hand.addAction(actions);
						this.addActor(hand);
					}
				}

			}

			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {// 当手指抬起，把全部按钮变成可click状态
					LogHelper.LogE("up");
					check();
					for (int i = 0; i < mStack.size(); i++) {
						mStack.get(i).setClick(false);
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {// 判断哪个按钮在手指触控范围内，是则使它状态为click
					for (int i = 0; i < items.size(); i++) {
						MyImage image = items.get(i);
						boolean xx = x >= image.getX()
								&& x <= image.getX() + image.getWidth();
						boolean yy = y >= image.getY()
								&& y <= image.getY() + image.getHeight();
						if (xx && yy) {
							if (!image.getClick()) {
								Click(image);
								image.setClick(true);
								image.addAction(CreateUtil.getClickActon());
							}
						} else {// 手指离开范围，让按钮变成可触摸状态
							image.setClick(false);
						}
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
			isCan = true;
		}
		if (!canTouch) {
			this.setTouchable(Touchable.disabled);
		} else {
			this.setTouchable(Touchable.enabled);
		}
		super.act(delta);
	}

	public void setCanTouch(boolean canTouch) {
		this.canTouch = canTouch;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	@Override
	public void Click(MyImage sender) {

		CCPoint point = sender.getPos();
		boolean isValid = true;
		boolean canContinue = true;// 如果找到需要的结果则不再循环查找
		int size = mStack.size();
		if (size >= 2) {// 判断触摸的点是否是已经触摸过的点主要是想让它不能逆向点击
			if (sender.equals(mStack.get(size - 2))) {// 栈的长度是size，那么最后一个的序号为size-1,so
				MyImage temp = mStack.get(size - 1);// 获取栈顶元素
				int type = backStack.get(size - 1);
				temp.setState(type);// 把栈顶的按钮的状态设置成没有点之前的状态
				// 把要回退的按钮的状态设置为之前的状态以便于校验是否过关
				userData[(int) temp.getPos().y][(int) temp.getPos().x] = (type - 1)
						+ "";
				preButton = mStack.get(size - 2);

				mStack.pop();
				backStack.pop();

				if (mStack.size() >= 2) {
					MyImage temp1 = mStack.get(mStack.size() - 2);
					preCur.setSize(temp1.getWidth() / 5, temp1.getHeight() / 5);
					preCur.setPosition(
							temp1.getX() + temp1.getWidth() / 2
									- preCur.getWidth() / 2,
							temp1.getY() + temp1.getHeight() / 2
									- preCur.getHeight() / 2);

					addActor(preCur);
				}

				currCur.setSize(preButton.getWidth() / 3,
						preButton.getHeight() / 3);
				currCur.setPosition(
						preButton.getX() + preButton.getWidth() / 2
								- currCur.getWidth() / 2,
						preButton.getY() + preButton.getHeight() / 2
								- currCur.getHeight() / 2);

				addActor(currCur);

				isValid = false;
			}
		}
		int type = sender.getState();
		if (isValid && type < 5 && !sender.equals(preButton)// 判断是不是在同一个键上点两下
				&& Rule.check(preButton, point)) {// 检测当前点是否在上一个点的前，左，右三个方向上
			SoundManager.getIntance().play(Contants.PUT);
			type++;

			preButton = sender;
			if (mStack.size() >= 1) {
				MyImage temp = mStack.get(mStack.size() - 1);
				preCur.setSize(temp.getWidth() / 5, temp.getHeight() / 5);
				preCur.setPosition(
						temp.getX() + temp.getWidth() / 2 - preCur.getWidth()
								/ 2, temp.getY() + temp.getHeight() / 2
								- preCur.getHeight() / 2);

				addActor(preCur);
			}
			mStack.push(sender);// 把合法的sender压入栈
			backStack.push(sender.getState());// 把合法的sender当前的颜色入栈
			sender.setState(type);// 把合法的sender设置新的颜色
			currCur.setSize(sender.getWidth() / 3, sender.getHeight() / 3);
			currCur.setPosition(
					sender.getX() + sender.getWidth() / 2 - currCur.getWidth()
							/ 2, sender.getY() + sender.getHeight() / 2
							- currCur.getHeight() / 2);

			addActor(currCur);
			userData[(int) point.y][(int) point.x] = (type - 1) + "";
		} else {
			SoundManager.getIntance().play(Contants.REVERT);
		}

		LogHelper.LogE("(x, " + point.x + "y:" + point.y + ") type = " + type
				+ "......click");
	}

	public void setOnPassListener(OnPassListener listener) {
		this.onPassListener = listener;
	}

	private void check() {
		boolean isEquals = true;
		for (int i = 0; i < buttonsData.length; i++) {
			for (int j = 0; j < buttonsData[0].length; j++) {
				if (!buttonsData[i][j].equals(userData[i][j])) {
					LogHelper.LogE("userData = " + userData[i][j]
							+ " buttonsData=" + buttonsData[i][j]);
					isEquals = false;
				}
			}
		}

		if (isEquals) {
			onPassListener.pass();
		}
		System.out.println("result=" + isEquals);
	}

	private void clearUserData() {
		int rows = userData.length;
		int colls = userData[0].length;

		LogHelper.LogE(rows + "行" + colls + "列");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < colls; j++) {
				userData[i][j] = "";
			}
		}
	}
}
