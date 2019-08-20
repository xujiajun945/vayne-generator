package com.dabanjia.vayne.test;

import com.dabanjia.vayne.connect.GeneratorConfig;
import com.dabanjia.vayne.connect.GeneratorExecutor;
import com.dabanjia.vayne.connect.JavaModel;
import com.dabanjia.vayne.connect.Parser;
import com.dabanjia.vayne.model.User;
import com.dabanjia.vayne.util.DataBaseUtil;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author xujiajun
 * @date 2019/1/16
 */
public class MyTest {

//	private String filePath = "C:/Users/Administrator/Desktop/test.json";
//
//	@Test
//	public void test01() {
////		String data = this.readFile(filePath);
////		System.out.println(data);
//		String data2 = this.readFile0(filePath);
//		System.out.println(data2);
//	}
//
//	@Test
//	public void test02() throws SQLException {
//		Connection connection = DataBaseUtil.createConnection();
//		String sql = "SELECT * FROM t_user; ";
//		ResultSet resultSet = DataBaseUtil.getResultSet(connection, sql);
//		while (resultSet.next()) {
//			String string = resultSet.getString(1);
//			System.out.println(string);
//		}
//		DataBaseUtil.close(connection);
//	}
//
//	@Test
//	public void test04() throws Exception {
//		Connection connection = DataBaseUtil.createConnection();
//		// 获取连接的元数据
//		DatabaseMetaData databaseMetaData = connection.getMetaData();
//		// 获取表信息
//		ResultSet resultSet = databaseMetaData.getTables(null, "%", "%", new String[] {"TABLE"});
//		while (resultSet.next()) {
//			String tableName = resultSet.getString("TABLE_NAME");
//			String databaseName = resultSet.getString("TABLE_SCHEM");
//			String tableType = resultSet.getString("TABLE_TYPE");
//			System.out.println(tableName);
//			System.out.println(databaseName);
//			System.out.println(tableType);
//			System.out.println("============================");
//		}
//		// 获取字段信息
//		ResultSet columnResultSet = databaseMetaData.getColumns(null, "%", "%", "%");
//		while (columnResultSet.next()) {
//			String columnName = columnResultSet.getString("COLUMN_NAME");
//			String columnType = columnResultSet.getString("TYPE_NAME");
////			int dataSize = columnResultSet.getInt("COLUMN_SIZE");
////			int digits = columnResultSet.getInt("DECIMAL_DIGITS");
////			int nullable = columnResultSet.getInt("NULLABLE");
//			System.out.println(columnName);
//			System.out.println(columnType);
////			System.out.println(dataSize);
////			System.out.println(digits);
////			System.out.println(nullable);
//			System.out.println("##############################");
//		}
//	}
//
//	@Test
//	public void test05() throws Exception {
//		Connection connection = DataBaseUtil.createConnection();
//		Parser parser = new Parser(connection);
//		List<JavaModel> javaModelList = parser.getJavaModelList();
//		System.out.println(javaModelList);
//	}
//
//	@Test
//	public void test07() throws Exception {
//		GeneratorConfig config = new GeneratorConfig();
//		GeneratorExecutor executor = new GeneratorExecutor();
//		executor.execute(config);
//	}
//
//	@Test
//	public void test08() throws SQLException {
//		Connection connection = DataBaseUtil.createConnection();
//		connection.setAutoCommit(false);
//		String sql = "SELECT * FROM t_user WHERE username = ?; ";
//		Object[] obj = {"徐家俊"};
//		List<User> userList = DataBaseUtil.selectList(connection, sql, User.class, obj);
//		User user = DataBaseUtil.selectOne(connection, sql, User.class, obj);
//		System.out.println(userList);
//		System.out.println(user);
//		String sql2 = "INSERT INTO t_user (phone, username, created_time) VALUES(?, ?, ?); ";
//		Object[] obj2 = {"13277327335", "张三", new Date(System.currentTimeMillis())};
//		DataBaseUtil.update(connection, sql2, obj2);
//		connection.commit();
//		DataBaseUtil.close(connection);
//	}
//
//	private String readFile(String filePath) {
//		String text = "";
//		try (
//			InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath))
//		) {
//			byte[] buf = new byte[inputStream.available()];
//			inputStream.read(buf);
//			text = new String(buf);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return text;
//	}
//
//	private String readFile0(String filePath) {
//		StringBuilder sb = new StringBuilder();
//		try (
//			BufferedReader reader = new BufferedReader(new FileReader(filePath))
//		) {
//			String temp = null;
//			while ((temp = reader.readLine()) != null) {
//				sb.append(temp);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}

}
