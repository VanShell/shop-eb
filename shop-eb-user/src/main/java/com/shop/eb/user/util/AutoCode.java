package com.shop.eb.user.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 自动生成dao,service
 * 
 * @author 杨邦得
 *
 */
public class AutoCode {

	private AutoCode() {
	};

	private volatile static AutoCode autoCode;

	private static AutoCode getAutoCode() {
		if (autoCode == null) {
			synchronized (AutoCode.class) {
				if (autoCode == null) {
					autoCode = new AutoCode();
				}
			}
		}
		return autoCode;
	}

	// 模板路径
	private final static String TEMPLATES_PATH = "/src/main/resources/templates/java";
	// 包路径
	private final static String PACKAGE_PATH = "/com/shop/eb/user";
	// 实体类路径
	private final static String ENTITY_PATH = PACKAGE_PATH + "/domain";
	// dao路径
	private final static String DAO_PATH = PACKAGE_PATH + "/dao";
	// mapper路径
	private final static String MAPPER_PATH = PACKAGE_PATH + "/mapper";
	// service路径
	private final static String SERVICE_PATH = PACKAGE_PATH + "/service";
	// serviceimpl路径
	private final static String SERVICE_IMPL_PATH = PACKAGE_PATH + "/service/impl";
	// controller路径
	private final static String CONTROLLER_PATH = PACKAGE_PATH + "/controller";

	/**
	 * 根据entity生成dao,service<br>
	 * 参数可为空,空默认覆盖全部<br>
	 * bs=>true:覆盖;false:存在文件就不覆盖
	 * 
	 * @throws Exception
	 */
	public static String init(boolean... bs) {
		try {
			// 获取目录
			File webRoot = FileUtil.getWebRoot();
			// 获取实体目录
			String entityPath = webRoot + "/src/main/java" + ENTITY_PATH;
			// 获取所有类文件
			List<String> listFileNames = FileUtil.listFileNames(entityPath);
			/// 获取模板路径
			String templatesPath = webRoot + TEMPLATES_PATH;
			// 设置模板配置
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
			configuration.setDirectoryForTemplateLoading(new File(templatesPath));
			// 包信息
			Map<Object, Object> packageMap = new HashMap<>();
			packageMap.put("package_path", getAutoCode().slashToSpot(PACKAGE_PATH));
			packageMap.put("entity_path", getAutoCode().slashToSpot(ENTITY_PATH));
			packageMap.put("dao_path", getAutoCode().slashToSpot(DAO_PATH));
			packageMap.put("mapper_path", getAutoCode().slashToSpot(MAPPER_PATH));
			packageMap.put("service_path", getAutoCode().slashToSpot(SERVICE_PATH));
			packageMap.put("service_impl_path", getAutoCode().slashToSpot(SERVICE_IMPL_PATH));
			packageMap.put("controller_path", getAutoCode().slashToSpot(CONTROLLER_PATH));
			List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
			for (String string : listFileNames) {
				// 放参数
				Map<Object, Object> map = new HashMap<>();
				map.put("entity", (string.substring(0, 1).toLowerCase() + string.substring(1)).replaceAll(".java", ""));
				map.put("Entity", string.replaceAll(".java", ""));
				map.put("author", "徐帆");
				map.put("datetime", DateUtil.now());
				list.add(map);
				map.put("packageMap", packageMap);
				// 输出文件
				getAutoCode().outFile(configuration, map, bs);
			}
			// baseController
			Map<Object, Object> baseControllerMap = new HashMap<>();
			baseControllerMap.put("list", list);
			baseControllerMap.put("packageMap", packageMap);
			File controller = new File(webRoot.getAbsolutePath() + "/src/main/java" + PACKAGE_PATH + "/controller/BaseController.java");
			Template template = configuration.getTemplate("base_controller.ftl");
			template.process(baseControllerMap, IoUtil.getWriter(new FileOutputStream(controller), CharsetUtil.CHARSET_UTF_8));
		} catch (Exception e) {
			return e.getMessage();
		}
		return "SUCCESS";
	}

	/**
	 * /转.
	 * 
	 * @param slash
	 * @return
	 */
	private String slashToSpot(String slash) {
		return slash.substring(1).replaceAll("/", ".");
	}

	/**
	 * 文件输出
	 * 
	 * @param configuration
	 * @param map
	 * @param bs
	 * @throws Exception
	 */
	private void outFile(Configuration configuration, Map<Object, Object> map, boolean... bs) throws Exception {
		String path = FileUtil.getWebRoot().getAbsolutePath();
		// 判断是否覆盖
		boolean cover = true;
		if (bs != null && bs.length == 1 && !bs[0]) {
			cover = false;
		}
		// Dao
		File daoFile = new File(path + "/src/main/java" + DAO_PATH + "/" + map.get("Entity") + "Dao.java");
		FileUtil.mkParentDirs(daoFile);
		if (cover || !cover && !FileUtil.exist(daoFile)) {
			Template template = configuration.getTemplate("dao.ftl");
			template.process(map, IoUtil.getWriter(new FileOutputStream(daoFile), CharsetUtil.CHARSET_UTF_8));
		}
		// Mapper
		File mapperFile = new File(path + "/src/main/java" + MAPPER_PATH + "/" + map.get("Entity") + "Mapper.xml");
		FileUtil.mkParentDirs(mapperFile);
		if (cover || !cover && !FileUtil.exist(mapperFile)) {
			Template template = configuration.getTemplate("mapper.ftl");
			template.process(map, IoUtil.getWriter(new FileOutputStream(mapperFile), CharsetUtil.CHARSET_UTF_8));
		}
		// service
		File serviceFile = new File(path + "/src/main/java" + SERVICE_PATH + "/" + "I" + map.get("Entity") + "Service.java");
		FileUtil.mkParentDirs(serviceFile);
		if (cover || !cover && !FileUtil.exist(serviceFile)) {
			Template template = configuration.getTemplate("service.ftl");
			template.process(map, IoUtil.getWriter(new FileOutputStream(serviceFile), CharsetUtil.CHARSET_UTF_8));
		}
		// service.impl
		File serviceImplFile = new File(path + "/src/main/java" + SERVICE_IMPL_PATH + "/" + map.get("Entity") + "ServiceImpl.java");
		FileUtil.mkParentDirs(serviceImplFile);
		if (cover || !cover && !FileUtil.exist(serviceImplFile)) {
			Template template = configuration.getTemplate("service_impl.ftl");
			template.process(map, IoUtil.getWriter(new FileOutputStream(serviceImplFile), CharsetUtil.CHARSET_UTF_8));
		}
		// controller
		File controllerFile = new File(path + "/src/main/java" + CONTROLLER_PATH + "/" + map.get("Entity") + "Controller.java");
		FileUtil.mkParentDirs(controllerFile);
		if (cover || !cover && !FileUtil.exist(controllerFile)) {
			Template template = configuration.getTemplate("controller.ftl");
			template.process(map, IoUtil.getWriter(new FileOutputStream(controllerFile), CharsetUtil.CHARSET_UTF_8));
		}
	}

}
