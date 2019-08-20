package com.dabanjia.vayne.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.util.Map;

/**
 * @author xujiajun
 * @date 2019/1/17
 */
public class FreeMarkerUtil {

	private FreeMarkerUtil() {}

	/**
	 * 获取模板
	 * @param templateName
	 * @return
	 */
	private static Template getTemplate(String templateName) {
		Version version = new Version(2, 3, 28);
		Configuration configuration = new Configuration(version);
		configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/template");
		configuration.setDefaultEncoding("UTF-8");
		Template template = null;
		try {
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	/**
	 * 输出模板
	 * @param templateName
	 * @param root
	 * @param path
	 */
	public static void write(String templateName, Map<String, Object> root, String path) {
		Template template = getTemplate(templateName);
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
			template.process(root, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
