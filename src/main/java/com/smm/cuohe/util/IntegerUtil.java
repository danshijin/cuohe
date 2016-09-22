package com.smm.cuohe.util;

import org.apache.commons.lang.StringUtils;

public class IntegerUtil {

	public static Integer StringToInt(String input) {
		Integer output = 0;

		// System.out.println(aaString.replace(aaString.substring(aaString.indexOf("."),
		// aaString.length()),""));
		try {
			if (StringUtils.isBlank(input)) {// 1.为空时
				output = 0;
			} else if (input.indexOf('.') > 0) {// 2.有小数点
				output = Integer
						.parseInt(input.replace(
								input.substring(input.indexOf("."),
										input.length()), ""));
			} else // 3.没有小数点
			{
				output = Integer.parseInt(input);
			}
		} catch (Exception e) {
			output = 0;
		}

		return output;
	}
}
