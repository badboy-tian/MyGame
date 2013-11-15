package com.xiaoqiu.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class OpenPassword {

	private static String Key = "RBCXF-CVBGR-382MK-DFHJ4-C69G8";
	private static Texture texture = null;
	private static byte[] allBytes = null;
	private static Pixmap pixmap = null;
	private static String strss = null;
	public static Texture openPassword(String path){
		texture = null;
		allBytes = null;
		pixmap = null;
		try {
			allBytes = Gdx.files.internal(path).readBytes();
			for (int i = 0; i < allBytes.length; i++) {
				allBytes[i] ^=  Key.hashCode();
			}
			pixmap = new Pixmap(allBytes, 0, allBytes.length);
			texture = new Texture(pixmap);
			pixmap.dispose();
			
		} catch (Exception e) {
			try {
				allBytes = Gdx.files.internal(path).readBytes();
				for (int i = 0; i < allBytes.length; i++) {
					allBytes[i] ^=  Key.hashCode();
				}
				pixmap = new Pixmap(allBytes, 0, allBytes.length);
				texture = new Texture(pixmap);
				pixmap.dispose();
			}
			catch (Exception e1){
			}
		}finally{
		}
		
		return texture;
	}
	public static void clear(){
		try {
			texture.dispose();
		} catch (Exception e) {
			// TODO: handle exception
		}
		texture = null;
		allBytes = null;
		pixmap = null;
	}
	
	public static Texture getTexture(String name)
	{
		name = "gfx/" + name + ".tian";
		return openPassword(name);
	}
}
