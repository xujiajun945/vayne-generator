package com.dabanjia.vayne.util;

import com.dabanjia.vayne.exception.DataBaseQueryException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;

/**
 * JDBC工具类
 * @author xujiajun
 * @date 2019/1/15
 */
public class DataBaseUtil {

	private static String driverClassName;

	private static String url;

	private static String username;

	private static String password;

	static {
		Properties properties = new Properties();
		try {
			properties.load(DataBaseUtil.class.getClassLoader().getResourceAsStream("generator.properties"));
			driverClassName = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DataBaseUtil() {}

	/**
	 * 创建连接
	 * @return
	 */
	public static Connection createConnection() {
		Connection connection = null;
		try {
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * DML语句
	 * @param connection
	 * @param sql
	 * @param obj
	 * @return
	 */
	public static int update(Connection connection, String sql, Object... obj) {
		int num = 0;
		PreparedStatement preparedStatement = createPreparedStatement(connection, sql, obj);
		try {
			num = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * DQL查询语句返回多条结果
	 * @param connection
	 * @param sql
	 * @param clazz
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> selectList(Connection connection, String sql, Class<T> clazz, Object... obj) {
		ResultSet resultSet = getResultSet(connection, sql, obj);
		List<T> result = new ArrayList<>();
		try {
			while (resultSet.next()) {
				T instance = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (Modifier.isFinal(field.getModifiers())) {
						continue;
					}
					String fieldName = field.getName();
					String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method method = clazz.getDeclaredMethod(methodName, field.getType());
					String column = StringTranslateUtil.propertyToColumn(fieldName);
					method.invoke(instance, resultSet.getObject(column));
				}
				result.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.size() > 0 ? result : null;
	}

	/**
	 * DQL查询语句返回List, 手动指定映射
	 * @param connection
	 * @param sql
	 * @param resultMap k: property  v: column
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> selectList(Connection connection, String sql, Class<T> clazz, Map<String, String> resultMap, Object... obj) {
		ResultSet resultSet = getResultSet(connection, sql, obj);
		List<T> result = new ArrayList<>();
		try {
			while (resultSet.next()) {
				T instance = clazz.newInstance();
				Set<Map.Entry<String, String>> entries = resultMap.entrySet();
				for (Map.Entry<String, String> entry : entries) {
					String key = entry.getKey();
					String value = entry.getValue();
					String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
					Field field = clazz.getField(key);
					Method method = clazz.getDeclaredMethod(methodName, field.getType());
					method.invoke(instance, resultSet.getObject(value));
				}
				result.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.size() > 0 ? result : null;
	}

	/**
	 * DQL查询返回单条结果
	 * @param connection
	 * @param sql
	 * @param clazz
	 * @param obj
	 * @param <T>
	 * @return
	 * @throws DataBaseQueryException
	 */
	public static <T> T selectOne(Connection connection, String sql, Class<T> clazz, Object... obj) throws DataBaseQueryException {
		List<T> result = selectList(connection, sql, clazz, obj);
		if (result.size() > 1) {
			throw new DataBaseQueryException("select one but return more then one");
		}
		return result.get(0);
	}

	/**
	 * DQL查询返回单条结果, 手动指定映射
	 * @param connection
	 * @param sql
	 * @param clazz
	 * @param resultMap k: property  v: column
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> T selectOne(Connection connection, String sql, Class<T> clazz, Map<String, String> resultMap, Object... obj) {
		List<T> result = selectList(connection, sql, clazz, resultMap, obj);
		if (result.size() > 1) {
			throw new DataBaseQueryException("select one but return more then one");
		}
		return result.get(0);
	}

	/**
	 * 释放资源
	 * @param closeables
	 */
	public static void close(AutoCloseable... closeables) {
		for (AutoCloseable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取SQL语句预编译发送器
	 * @param connection
	 * @param sql
	 * @param obj
	 * @return
	 */
	private static PreparedStatement createPreparedStatement(Connection connection, String sql, Object... obj) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {
					preparedStatement.setObject(i + 1, obj[i]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	/**
	 * 获取结果集
	 * @param connection
	 * @param sql
	 * @param obj
	 * @return
	 */
	private static ResultSet getResultSet(Connection connection, String sql, Object... obj) {
		PreparedStatement preparedStatement = createPreparedStatement(connection, sql, obj);
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}
