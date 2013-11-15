package com.xiaoqiu.util;

import com.xiaoqiu.view.MyImage;

public class Rule {
	public static boolean check(MyImage pree, CCPoint curr) {
		if (pree == null) {
			return true;
		}
		CCPoint pre = pree.getPos();

		boolean one = (curr.y == pre.y)
				&& ((curr.x == (pre.x + 1)) | (curr.x == (pre.x - 1)));
		boolean two = (pre.x == curr.x)
				&& ((curr.y == (pre.y - 1)) | (curr.y == (pre.y + 1)));
		return one | two;
	}
}
