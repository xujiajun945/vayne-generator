package com.dabanjia.vayne.connect;

import com.dabanjia.vayne.util.DataBaseUtil;
import lombok.Getter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author xujiajun
 * @date 2019/1/17
 */
@Getter
public class GeneratorConfig {

	/**
	 * model生成的项目名
	 */
	private String modelTargetProject;

	/**
	 * model生成的目标包名
	 */
	private String targetModel;

	/**
	 * dao生成的项目名
	 */
	private String daoTargetProject;

	/**
	 * dao生成的目标包名
	 */
	private String targetDao;

	/**
	 * mapper生成的项目名
	 */
	private String mapperTargetProject;

	/**
	 * mapper生成的目标包名
	 */
	private String targetMapper;

	/**
	 * java作者信息
	 */
	private String author;

	/**
	 * 生成时间
	 */
	private String date;

	/**
	 * 要使用的表名集合
	 */
	private List<String> tableNames;

	public GeneratorConfig() {
		Properties properties = new Properties();
		try {
			properties.load(DataBaseUtil.class.getClassLoader().getResourceAsStream("generator.properties"));
			this.modelTargetProject = properties.getProperty("model-target-project");
			this.daoTargetProject = properties.getProperty("dao-target-project");
			this.mapperTargetProject = properties.getProperty("mapper-target-project");
			this.targetModel = properties.getProperty("target.model");
			this.targetDao = properties.getProperty("target.dao");
			this.targetMapper = properties.getProperty("target.mapper");
			this.author = properties.getProperty("user");
			Date now = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String format = dateFormat.format(now);
			this.date = format;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}
}
