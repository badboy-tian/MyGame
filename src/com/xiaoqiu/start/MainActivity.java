package com.xiaoqiu.start;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.domob.android.ads.DomobActivity;
import cn.domob.android.ads.DomobAdView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.umeng.fb.FeedbackAgent;
import com.xiaoqiu.Listener.OnPaidListener;
import com.xiaoqiu.game.MyGame;
import com.xiaoqiu.util.SharePreferenceUtil;

public class MainActivity extends AndroidApplication {
	private static MainActivity instance = null;
	private FeedbackAgent agent;//友盟用户反馈
	private Handler mHandler;
	private OnPaidListener paidListener;//支付监听器
	private SharePreferenceUtil preferenceUtil;//游戏数据工具
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		agent = new FeedbackAgent(this);
		mHandler = new Handler();
		preferenceUtil = new SharePreferenceUtil(this, "xxoo");
		
		//init
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;
		initialize(new MyGame(), cfg);
		
		instance = this;
	}
	
	public MainActivity()
	{
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public static MainActivity getInstance()
	{
		return instance;
	}
		
	public int getCompleted()
	{
		String str = preferenceUtil.loadStringSharedPreference("xxoo");//已经完成的关卡
		if (str.length() == 0) {
			return 1;
		}else{
			return Integer.parseInt(str);
		}
	}
	
	public int getLimit()
	{
		String str = preferenceUtil.loadStringSharedPreference("ooxx");//最大关卡
		if (str.length() == 0) {
			return 30;
		}else{
			return Integer.parseInt(str);
		}
	}
	
	public void setLimit(int what)
	{
		preferenceUtil.saveSharedPreferences("ooxx", what + "");
	}
	
	public void setCompleted(int what)
	{
		preferenceUtil.saveSharedPreferences("xxoo", what + "");
	}
	
	public void statrtUmeng()
	{
		agent.startFeedbackActivity();
	}
	
	public boolean getMusic()
	{
		return preferenceUtil.loadBooleanSharedPreference("music", true);
	}
	
	public void setMusic(boolean value) {
		preferenceUtil.saveSharedPreferences("music", value);
	}
	
	public void Tip(final String str)
	{
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void customTip()
	{
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				View v = getLayoutInflater().inflate(R.layout.custom_toast, null);
				Toast toast = new Toast(MainActivity.this);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(v);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});
	}
	
	/**
	 * 
	 * @param listener
	 * 设置支付成功后的回调函数 同时刷新关卡数
	 */
	public void setOnPaidListener(OnPaidListener listener)
	{
		this.paidListener = listener;
	}
	/**
	 * @author tian
	 * @category 支付窗口
	 */
	public void Dialog(){
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				View v = getLayoutInflater().inflate(R.layout.pay_dialog, null);
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("温馨提醒")
				.setView(v)
				.setNegativeButton("取消", null)
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//如果支付成功
						if (paidListener != null) {
							//paidListener.onPaid();
						}
					}
				}).show();
			}
		});
	}
}