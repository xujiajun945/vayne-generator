package com.dabanjia.vayne.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xujiajun
 * @date 2019/1/16
 */
public class StringTranslateUtil {

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	private StringTranslateUtil() {}

	/**
	 * 通过表名生成实体类的类名
	 * @param tableName
	 * @return
	 */
//	public static String createModelName(String tableName) {
//		String[] tableNameArr = tableName.split("_");
//		if (tableNameArr.length <= 1) {
//			return tableNameArr[0];
//		}
//		StringBuilder sb = new StringBuilder();
//		for (int i = 1; i < tableNameArr.length; i++) {
//			sb.append(tableNameArr[i].substring(0, 1).toUpperCase() + tableNameArr[i].substring(1));
//		}
//		return sb.toString();
//	}

	public static String createModelName(String tableName) {
		tableName = tableName.toLowerCase();
		Matcher matcher = linePattern.matcher(tableName);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 将包名转化为文件夹层级架构名
	 * @param packages
	 * @return
	 */
	public static String packagesToFolders(String packages) {
		String result = packages.replaceAll("[.]", "/");
		return result + "/";
	}

	/**
	 * 将数据库字段名转化为java类属性名
	 * @param column
	 * @return
	 */
	public static String columnToProperty(String column) {
		String[] columnArray = column.split("_");
		StringBuilder sb = new StringBuilder("is".equals(columnArray[0]) && columnArray.length > 1 ? "" : columnArray[0]);
		if (columnArray.length > 1) {
			for (int i = 1; i < columnArray.length; i++) {
				sb.append(columnArray[i].substring(0, 1).toUpperCase() + columnArray[i].substring(1));
			}
		}
		String result = sb.toString();
		return result.substring(0, 1).toLowerCase() + result.substring(1);
	}

	/**
	 * 将java属性名转化为数据库字段名
	 * @param property
	 * @return
	 */
	public static String propertyToColumn(String property) {
		String extra = "deleted";
		StringBuilder sb = new StringBuilder(property);
		int temp = 0;
		for (int i = 0; i < property.length(); i++) {
			if (Character.isUpperCase(property.charAt(i))) {
				sb.insert(i + temp, "_");
				temp += 1;
			}
		}
		String result = sb.toString();
		if (extra.equals(result)) {
			return "is_deleted";
		}
		return result;
	}

}
