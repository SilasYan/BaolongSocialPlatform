package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.Room;
import icu.baolong.social.repository.room.mapper.RoomMapper;
import org.springframework.stereotype.Repository;

/**
 * 房间表 (room) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomDao extends ServiceImpl<RoomMapper, Room> {
}
