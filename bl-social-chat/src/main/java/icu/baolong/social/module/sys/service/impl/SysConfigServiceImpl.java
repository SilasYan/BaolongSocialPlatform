package icu.baolong.social.module.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import icu.baolong.social.cache.SysConfigCache;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.module.sys.domain.enums.ConfigTypeEnum;
import icu.baolong.social.module.sys.service.SysConfigService;
import icu.baolong.social.repository.sys.entity.SysConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统配置表（sys_config） - 服务实现
 *
 * @author Silas Yan 2025-05-25 15:16
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

	private final SysConfigCache sysConfigCache;

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	@Override
	public String getConfigAsValue(String configKey) {
		SysConfig sysConfig = sysConfigCache.getConfig(configKey);
		if (ObjectUtil.isNotNull(sysConfig)) {
			if (ConfigTypeEnum.VALUE.getKey().equals(sysConfig.getConfigType())) {
				return sysConfig.getConfigValue();
			}
		}
		throw new BusinessException("配置类型错误");
	}

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	@Override
	public JSONObject getConfigAsJsonObj(String configKey) {
		SysConfig sysConfig = sysConfigCache.getConfig(configKey);
		if (ObjectUtil.isNotNull(sysConfig)) {
			if (ConfigTypeEnum.JSON_OBJECT.getKey().equals(sysConfig.getConfigType())) {
				return JSONUtil.parseObj(sysConfig.getConfigValue());
			}
		}
		throw new BusinessException("配置类型错误");
	}

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	@Override
	public JSONArray getConfigAsJsonArr(String configKey) {
		SysConfig sysConfig = sysConfigCache.getConfig(configKey);
		if (ObjectUtil.isNotNull(sysConfig)) {
			if (ConfigTypeEnum.JSON_ARRAY.getKey().equals(sysConfig.getConfigType())) {
				return JSONUtil.parseArray(sysConfig.getConfigValue());
			}
		}
		throw new BusinessException("配置类型错误");
	}
}
