package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.RoomGroup;
import icu.baolong.social.repository.room.mapper.RoomGroupMapper;
import org.springframework.stereotype.Repository;

/**
 * 群聊房间表 (room_group) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {
}
