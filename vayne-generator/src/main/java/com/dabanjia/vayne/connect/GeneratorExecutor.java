package com.dabanjia.vayne.connect;

import com.dabanjia.vayne.util.DataBaseUtil;
import com.dabanjia.vayne.util.FreeMarkerUtil;
import com.dabanjia.vayne.util.StringTranslateUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xujiajun
 * @date 2019/1/17
 */
public class GeneratorExecutor {

	private String basePath = "/src/main/java/";

	private String daoProjectName;

	private String modelProjectName;

	private String mapperProjectName;

	/**
	 * 执行逆向工程
	 * @param generatorConfig
	 * @throws Exception
	 */
	public void execute(GeneratorConfig generatorConfig) throws Exception {
		Connection connection = DataBaseUtil.createConnection();
		this.daoProjectName = generatorConfig.getDaoTargetProject();
		this.modelProjectName = generatorConfig.getModelTargetProject();
		this.mapperProjectName = generatorConfig.getMapperTargetProject();
		List<String> tableNames = generatorConfig.getTableNames();
		Parser parser;
		if (tableNames != null && tableNames.size() > 0) {
			parser = new Parser(connection, tableNames);
		} else {
			parser = new Parser(connection);
		}
		List<JavaModel> javaModelList = parser.getJavaModelList();
		for (JavaModel javaModel : javaModelList) {
			Map<String, Object> map = new HashMap<>(10);
			map.put("config", generatorConfig);
			map.put("model", javaModel);
			// 生成model
			FreeMarkerUtil.write("model.ftl", map, this.modelPath(generatorConfig.getTargetModel(), javaModel.getModelName()));
			// 生成dao
			FreeMarkerUtil.write("dao.ftl", map, this.daoPath(generatorConfig.getTargetDao(), javaModel.getDaoName()));
			// 生成mapper
			FreeMarkerUtil.write("mapper_uppercase_sql.ftl", map, this.mapperPath(generatorConfig.getTargetMapper(), javaModel.getMapperName()));
		}
		DataBaseUtil.close(connection);
	}

	/**
	 * model目录转化
	 * @param path
	 * @param modelName
	 * @return
	 */
	private String modelPath(String path, String modelName) throws IOException {
		String rootProjectPath = new File("").getCanonicalPath();
		rootProjectPath = rootProjectPath.substring(0, rootProjectPath.lastIndexOf(File.separator) + 1);
		return rootProjectPath + modelProjectName + basePath + StringTranslateUtil.packagesToFolders(path) + modelName + ".java";
	}

	/**
	 * dao目录转化
	 * @param path
	 * @param daoName
	 * @return
	 */
	private String daoPath(String path, String daoName) throws IOException {
		String rootProjectPath = new File("").getCanonicalPath();
		rootProjectPath = rootProjectPath.substring(0, rootProjectPath.lastIndexOf(File.separator) + 1);
		return rootProjectPath + daoProjectName + basePath + StringTranslateUtil.packagesToFolders(path) + daoName + ".java";
	}

	/**
	 * mapper目录转化
	 * @param path
	 * @param mapperName
	 * @return
	 */
	private String mapperPath(String path, String mapperName) throws IOException {
		String rootProjectPath = new File("").getCanonicalPath();
		rootProjectPath = rootProjectPath.substring(0, rootProjectPath.lastIndexOf(File.separator) + 1);
		return rootProjectPath + mapperProjectName + basePath + StringTranslateUtil.packagesToFolders(path) + mapperName + ".xml";
	}
}
