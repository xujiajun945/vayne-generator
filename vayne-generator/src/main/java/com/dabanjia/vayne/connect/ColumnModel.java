package com.dabanjia.vayne.connect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xujiajun
 * @date 2019/1/17
 */
@Setter
@Getter
@ToString
public class ColumnModel {

	/**
	 * 表中字段名
	 */
	private String columnName;

	/**
	 * 属性名
	 */
	private String name;

	/**
	 * javaType
	 */
	private String type;

	/**
	 * 注释
	 */
	private String remark;

	/**
	 * 是否为主键
	 */
	private Boolean isPrimaryKey;

}
