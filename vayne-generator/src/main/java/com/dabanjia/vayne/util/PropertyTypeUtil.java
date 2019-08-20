package com.dabanjia.vayne.util;

/**
 * @author xujiajun
 * @date 2019/1/16
 */
public class PropertyTypeUtil {

	private PropertyTypeUtil() {}

	/**
	 * 数据库字段类型转为java数据类型
	 * @param columnType
	 * @return
	 */
	public static String columnTypeToJavaType(String columnType) {
		String javaType = null;
		switch (columnType) {
			case "BIGINT":
				javaType = "Long";
				break;
			case "VARCHAR":
				javaType = "String";
				break;
			case "DATETIME":
				javaType = "Date";
				break;
			case "BIT":
				javaType = "Boolean";
				break;
			case "DECIMAL":
				javaType = "BigDecimal";
				break;
			case "INT":
				javaType = "Integer";
				break;
			case "CHAR":
				javaType = "String";
				break;
			case "TINYINT":
				javaType = "Integer";
				break;
			case "FLOAT":
				javaType = "float";
				break;
			case "TINYINT UNSIGNED":
				javaType = "Integer";
				break;
			case "TIMESTAMP":
				javaType = "Date";
			default:
				break;
		}
		return javaType;
	}

	/**
	 * 生成需要导入的包的包名
	 * @param columnType
	 * @return
	 */
	public static String buildImportPackage(String columnType) {
		String packageName = null;
		switch (columnType) {
			case "DATETIME":
				packageName = "java.util.Date";
				break;
			case "DECIMAL":
				packageName = "java.math.BigDecimal";
				break;
			case "TIMESTAMP":
				packageName = "java.util.Date";
			default:
				break;
		}
		return packageName;
	}
}
