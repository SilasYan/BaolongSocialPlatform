package icu.baolong.social.module.message.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.message.entity.Message;
import icu.baolong.social.repository.message.mapper.MessageMapper;
import org.springframework.stereotype.Repository;

/**
 * 消息表 (message) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class MessageDao extends ServiceImpl<MessageMapper, Message> {
}
