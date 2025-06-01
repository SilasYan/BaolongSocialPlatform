package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.GroupMember;
import icu.baolong.social.repository.room.mapper.GroupMemberMapper;
import org.springframework.stereotype.Repository;

/**
 * 群成员表 (group_member) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {
}
