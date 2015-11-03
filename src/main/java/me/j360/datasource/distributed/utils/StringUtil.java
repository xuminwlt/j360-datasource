package me.j360.datasource.distributed.utils;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.utils;
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：StringUtil 工具类，暂时省去了commonutil-lang3的依赖
 */
public class StringUtil {

	public static boolean isNullOrEmpty(Object paramObject) {
		return (paramObject == null)
				|| ("".equals(paramObject.toString()))
				|| (paramObject.equals("null") || paramObject.toString().trim()
						.equals(""));
	}

	public static String toString(Object paramObject) {
		if (paramObject == null)
			return "null";
		return paramObject.toString();
	}
}
