package com.xiaoqiu.util;

import com.badlogic.gdx.Gdx;

public class LogHelper {
	public static final String TAG = "debug";
	public static final boolean isON = true;
	public static void LogE(String str)
	{
		if (isON) {
			Gdx.app.debug(TAG, str);
			Gdx.app.log(TAG, str);
		}
	}
	
	public static void LogV(String str)
	{
		if (isON) {
			Gdx.app.log(TAG, str);
		}
	}
}
