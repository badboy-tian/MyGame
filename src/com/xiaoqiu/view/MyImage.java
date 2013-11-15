package com.xiaoqiu.view;

import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xiaoqiu.Listener.OnClickListener;
import com.xiaoqiu.game.MyNewGroup;
import com.xiaoqiu.util.CCPoint;
import com.xiaoqiu.util.CreateUtil;
import com.xiaoqiu.util.OpenPassword;

public class MyImage extends Image {
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int THREE = 3;
	public final static int FOUR = 4;
	public final static int FIVE = 5;
	private int State = 1;

	private Drawable d1, d2, d3, d4, d5;
	private Map<String, Texture> assetManager = null;
	private boolean isClick = false;
	private OnClickListener clickListener;
	private CCPoint pos;

	public MyImage(Map<String, Texture> assetManager) {
		this.assetManager = assetManager;
		
		d1 = new TextureRegionDrawable(new TextureRegion(open("gray_temp")));
		d2 = new TextureRegionDrawable(new TextureRegion(open("blue")));
		d3 = new TextureRegionDrawable(new TextureRegion(open("green")));
		d4 = new TextureRegionDrawable(new TextureRegion(open("yellow")));
		d5 = new TextureRegionDrawable(new TextureRegion(open("orange")));
		
		pos = new CCPoint(0, 0);
		this.setDrawable(d1);
	}
	
	private Texture open(String name)
	{
		return this.assetManager.get(name);
	}
	
	public void setCCPoint(CCPoint point)
	{
		this.pos = point;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		switch (State) {
		case ONE:
			MyImage.this.setDrawable(d1);
			break;
		case TWO:
			MyImage.this.setDrawable(d2);
			break;
		case THREE:
			MyImage.this.setDrawable(d3);
			break;
		case FOUR:
			MyImage.this.setDrawable(d4);
			break;
			
		case FIVE:
			MyImage.this.setDrawable(d5);
			break;

		default:
			break;
		}
		Color color = new Color(this.getColor().r, this.getColor().g,
				this.getColor().b, this.getColor().a * parentAlpha);

		batch.setColor(color);
		if (State == ONE) {
			parentAlpha = 0.15f;
		}else{
			parentAlpha = 1;
		}
		super.draw(batch, parentAlpha);
	}
	
	public void setState(int state) {
		State = state;
	}
	
	public int getState() {
		return State;
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
		}else{
			isClick = false;
		}
		
		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this : null;
	}
	
	public void setOnClickListener(MyNewGroup listener)
	{
		this.clickListener = (OnClickListener)listener;
	}
	
	public CCPoint getPos() {
		return pos;
	}

	public boolean getClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + State;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyImage other = (MyImage) obj;
		if (State != other.State)
		
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}
	
	
}
