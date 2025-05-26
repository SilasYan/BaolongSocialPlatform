package icu.baolong.social.module.sys.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.sys.entity.SysConfig;
import icu.baolong.social.repository.sys.mapper.SysConfigMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统配置表 (sys_config) - 持久化服务
 *
 * @author Baolong 2025-05-25 15:01:58
 */
@Repository
public class SysConfigDao extends ServiceImpl<SysConfigMapper, SysConfig> {

	/**
	 * 根据配置键获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	public SysConfig getSysConfigByKey(String configKey) {
		return this.lambdaQuery().eq(SysConfig::getConfigKey, configKey).one();
	}
}
