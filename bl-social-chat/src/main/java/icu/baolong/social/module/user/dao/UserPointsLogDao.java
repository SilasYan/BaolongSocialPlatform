package icu.baolong.social.module.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.user.entity.UserPointsLog;
import icu.baolong.social.repository.user.mapper.UserPointsLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户积分日志表 (user_points_log) - 持久化服务
 *
 * @author Baolong 2025-05-25 15:01:58
 */
@Repository
public class UserPointsLogDao extends ServiceImpl<UserPointsLogMapper, UserPointsLog> {
}
