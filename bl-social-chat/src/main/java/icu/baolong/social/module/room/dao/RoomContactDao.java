package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.RoomContact;
import icu.baolong.social.repository.room.mapper.RoomContactMapper;
import org.springframework.stereotype.Repository;

/**
 * 房间会话表 (room_contact) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomContactDao extends ServiceImpl<RoomContactMapper, RoomContact> {
}
