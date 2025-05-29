package icu.baolong.social.module.blacklist.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.blacklist.entity.Blacklist;
import icu.baolong.social.repository.blacklist.mapper.BlacklistMapper;
import org.springframework.stereotype.Repository;

/**
 * 黑名单表 (blacklist) - 持久化服务
 *
 * @author Baolong 2025-05-29 20:06:21
 */
@Repository
public class BlacklistDao extends ServiceImpl<BlacklistMapper, Blacklist> {
}
