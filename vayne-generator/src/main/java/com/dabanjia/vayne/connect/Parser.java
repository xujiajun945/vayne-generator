package com.dabanjia.vayne.connect;

import com.dabanjia.vayne.util.PropertyTypeUtil;
import com.dabanjia.vayne.util.StringTranslateUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析器
 * @author xujiajun
 * @date 2019/1/16
 */
public class Parser {

	private List<JavaModel> javaModelList = new ArrayList<>();

	public Parser(Connection connection) throws Exception {
		this.parseAllTables(connection);
	}

	public Parser(Connection connection, List<String> tableNames) throws Exception {
		this.parseTables(connection, tableNames);
	}

	public List<JavaModel> getJavaModelList() {
		return this.javaModelList;
	}

	/**
	 * 解析当前数据库所有表
	 * @param connection
	 * @throws Exception
	 */
	private void parseAllTables(Connection connection) throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet tableResult = databaseMetaData.getTables(null, "%", "%", new String[] {"TABLE"});
		List<String> tableNames = new ArrayList<>();
		while (tableResult.next()) {
			// 获取当前表的表名
			String tableName = tableResult.getString("TABLE_NAME");
			tableNames.add(tableName);
		}
		this.parseTables(connection, tableNames);
	}

	/**
	 * 解析当前数据库中指定表名的表
	 * @param connection
	 * @param tableNames
	 * @throws Exception
	 */
	private void parseTables(Connection connection, List<String> tableNames) throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		for (String tableName : tableNames) {
			JavaModel javaModel = new JavaModel();
			this.parseTable(databaseMetaData, javaModel, tableName);
		}
	}

	/**
	 * 解析当前数据库指定表名的表
	 * @param databaseMetaData
	 * @param javaModel
	 * @param tableName
	 * @throws Exception
	 */
	private void parseTable(DatabaseMetaData databaseMetaData, JavaModel javaModel, String tableName) throws Exception {
		ResultSet tableResult = databaseMetaData.getTables(null, "%", tableName, new String[] {"TABLE"});
		tableResult.next();
		String tableRemark = tableResult.getString("REMARKS");
		String modelName = StringTranslateUtil.createModelName(tableName);
		// 获取当前表的主键
		ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);
		primaryKeyResultSet.next();
		String primaryKeyColumn = primaryKeyResultSet.getString("COLUMN_NAME");
		if (primaryKeyColumn != null) {
			String primaryKey = StringTranslateUtil.columnToProperty(primaryKeyColumn);
			javaModel.setPrimaryKey(primaryKey);
			javaModel.setPrimaryKeyFistWordUppercase(primaryKey.substring(0, 1).toUpperCase() + primaryKey.substring(1));
		}
		javaModel.setTableName(tableName);
		javaModel.setModelName(modelName);
		javaModel.setModelNameFirstWordLowercase(modelName.substring(0, 1).toLowerCase() + modelName.substring(1));
		javaModel.setDaoName(modelName + "Mapper");
		javaModel.setMapperName(modelName + "Mapper");
		javaModel.setTableRemark(tableRemark);

		ResultSet columnResult = databaseMetaData.getColumns(null, "%", tableName, "%");
		List<ColumnModel> columnModelList = new ArrayList<>();
		List<String> importPackages = new ArrayList<>();
		while (columnResult.next()) {
			ColumnModel columnModel = new ColumnModel();
			String columnName = columnResult.getString("COLUMN_NAME");
			String propertyName = StringTranslateUtil.columnToProperty(columnName);
			String columnType = columnResult.getString("TYPE_NAME");
			String javaType = PropertyTypeUtil.columnTypeToJavaType(columnType);
			String packageName = PropertyTypeUtil.buildImportPackage(columnType);
			if (packageName != null) {
				importPackages.add(packageName);
			}
			String remark = columnResult.getString("REMARKS");
			columnModel.setColumnName(columnName);
			columnModel.setName(propertyName);
			columnModel.setType(javaType);
			columnModel.setRemark(remark);
			columnModel.setIsPrimaryKey(false);
			columnModelList.add(columnModel);
		}
		javaModel.setColumnModelList(columnModelList);
		javaModel.setImportPackages(importPackages);
		javaModel.setHavingLogicDeleted(false);
		StringBuilder baseColumn = new StringBuilder();
		for (ColumnModel columnModel : columnModelList) {
			baseColumn.append(columnModel.getColumnName() + ", ");
			if ("is_deleted".equals(columnModel.getName())) {
				javaModel.setHavingLogicDeleted(true);
			}
			if (javaModel.getPrimaryKey() != null && javaModel.getPrimaryKey().equals(columnModel.getName())) {
				javaModel.setPrimaryKeyType(columnModel.getType());
				javaModel.setPrimaryKeyColumnName(columnModel.getColumnName());
				columnModel.setIsPrimaryKey(true);
			}
		}
		// 生成mapper中使用的baseColumnList
		String baseColumnList = baseColumn.toString();
		baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(","));
		javaModel.setBaseColumnList(baseColumnList);
		javaModelList.add(javaModel);
	}

}
