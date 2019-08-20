package com.dabanjia.vayne.connect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author xujiajun
 * @date 2019/1/7
 */
@Setter
@Getter
@ToString
public class JavaModel {

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * model类名
	 */
	private String modelName;

	/**
	 * 类名首字符小写
	 */
	private String modelNameFirstWordLowercase;

	/**
	 * dao的名称
	 */
	private String daoName;

	/**
	 * mapper的名称
	 */
	private String mapperName;

	/**
	 * 表的主键
	 */
	private String primaryKey;

	/**
	 * 表的主键(首字符大写)
	 */
	private String primaryKeyFistWordUppercase;

	/**
	 * 主键的javaType
	 */
	private String primaryKeyType;

	/**
	 * 主键的字段名
	 */
	private String primaryKeyColumnName;

	/**
	 * 每一列的属性
	 */
	private List<ColumnModel> columnModelList;

	/**
	 * 是否具有逻辑删除
	 */
	private Boolean havingLogicDeleted;

	/**
	 * 表的注释
	 */
	private String tableRemark;

	/**
	 * 需要导入的包
	 */
	private List<String> importPackages;

	/**
	 * 所有字段(以","分割)
	 */
	private String baseColumnList;

}
