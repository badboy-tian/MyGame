package com.xiaoqiu.util;

import java.io.BufferedReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ReadLevels {
	boolean canRead = false;

	public ReadLevels() {

	}
	public String[][] getLevel(int i) {
		
		ArrayList<String[]> datas = new ArrayList<String[]>();

		FileHandle files = Gdx.files.internal("lvl/levels.png");
		try {
			
			BufferedReader reader = files.reader(1024);
			String str = null;
			Q: while ((str = reader.readLine()) != null) {
				if (str.startsWith("//")) {
					String temp = str.split(" ")[1];
					if (Integer.parseInt(temp) == i && !canRead) {
						canRead = true;
						continue;
					}
				}

				if (canRead == true) {
					if (str.contains("-")) {
						int length = str.length()
								- str.replace("-", "").length();
						if (length == 4) {

						} else if (length == 6) {
							break Q;
						}
					} else {
						String data[] = str.split(" ");
						datas.add(data);
					}
				}
			}
		} catch (Exception e) {
		}
		
		String[][] gameData = new String[datas.size()][datas.get(0).length];
		for (int j = 0; j < datas.size(); j++) {
			for (int j2 = 0; j2 < datas.get(0).length; j2++) {
				gameData[j][j2] = datas.get(j)[j2];
				LogHelper.LogE("-" + datas.get(j)[j2]);
			}
			LogHelper.LogE("\n");
		}
		return gameData;
	}
}
