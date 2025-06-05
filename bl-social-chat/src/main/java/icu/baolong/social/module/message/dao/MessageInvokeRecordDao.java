package icu.baolong.social.module.message.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.module.message.enums.InvokeStatusEnum;
import icu.baolong.social.repository.message.entity.MessageInvokeRecord;
import icu.baolong.social.repository.message.mapper.MessageInvokeRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 消息调用记录（存放所有未执行的消息） (message_invoke_record) - 持久化服务
 *
 * @author Baolong 2025-06-04 21:07:15
 */
@Repository
public class MessageInvokeRecordDao extends ServiceImpl<MessageInvokeRecordMapper, MessageInvokeRecord> {

	/**
	 * 获取所有待重试的记录
	 *
	 * @return 消息调用记录列表
	 */
	public List<MessageInvokeRecord> getWaitRetryInvokeRecord() {
		return this.lambdaQuery()
				.eq(MessageInvokeRecord::getInvokeStatus, InvokeStatusEnum.WITH.getKey())
				// 查询小于当前时间的数据
				.lt(MessageInvokeRecord::getNextRetryTime, new Date())
				// 避免刚入库的数据被查出来, 查出2分钟前的数据
				.lt(MessageInvokeRecord::getCreateTime, DateUtil.offsetMinute(new Date(), -2))
				.list();
	}
}
