package icu.baolong.social.module.sys.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

/**
 * 系统配置表 (sys_config) - 服务接口
 *
 * @author Silas Yan 2025-05-25 15:15
 */
public interface SysConfigService {

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	String getConfigAsValue(String configKey);

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	JSONObject getConfigAsJsonObj(String configKey);

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	JSONArray getConfigAsJsonArr(String configKey);

}
