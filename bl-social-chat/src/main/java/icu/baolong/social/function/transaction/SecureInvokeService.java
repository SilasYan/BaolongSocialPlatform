package icu.baolong.social.function.transaction;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.baolong.social.module.message.dao.MessageInvokeRecordDao;
import icu.baolong.social.module.message.enums.InvokeStatusEnum;
import icu.baolong.social.repository.message.entity.MessageInvokeRecord;
import icu.baolong.social.repository.message.entity.SnapshotParam;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * 安全执行服务
 *
 * @author Silas Yan 2025-06-04 21:29
 */
@Slf4j
@EnableScheduling
@Service
public class SecureInvokeService {

	@Resource
	private MessageInvokeRecordDao messageInvokeRecordDao;
	@Resource
	private ThreadPoolTaskExecutor messageExecutor;

	/**
	 * 查询并执行
	 */
	@Scheduled(cron = "*/5 * * * * ?")
	public void queryAndExecute() {
		List<MessageInvokeRecord> waitRetryInvokeRecord = messageInvokeRecordDao.getWaitRetryInvokeRecord();
		for (MessageInvokeRecord messageInvokeRecord : waitRetryInvokeRecord) {
			asyncInvoke(messageInvokeRecord);
		}
	}

	/**
	 * 执行
	 *
	 * @param messageInvokeRecord 消息调用记录对象
	 * @param isAsync             是否异步
	 */
	public void execute(MessageInvokeRecord messageInvokeRecord, boolean isAsync) {
		// 判断是否是在同一个事务中
		boolean transaction = TransactionSynchronizationManager.isActualTransactionActive();
		if (!transaction) {
			return;
		}
		messageInvokeRecordDao.save(messageInvokeRecord);
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@SneakyThrows
			@Override
			public void afterCommit() {
				// 事务后执行
				if (isAsync) {
					asyncInvoke(messageInvokeRecord);
				} else {
					invoke(messageInvokeRecord);
				}
			}
		});
	}

	/**
	 * 调用
	 *
	 * @param messageInvokeRecord 消息调用记录对象
	 */
	public void invoke(MessageInvokeRecord messageInvokeRecord) {
		try {
			SecureInvokeHolder.set();
			SnapshotParam snapshotParam = messageInvokeRecord.getSnapshotParam();
			// 获取到类, 并创建对象
			Class<?> clazz = Class.forName(snapshotParam.getClassName());
			Object bean = SpringUtil.getBean(clazz);
			// 获取到参数类型, 并获取到对应的类
			List<String> paramTypes = JSONUtil.toList(snapshotParam.getParamTypes(), String.class);
			List<? extends Class<?>> paramClassList = paramTypes.stream().map(pt -> {
				try {
					return Class.forName(pt);
				} catch (ClassNotFoundException e) {
					log.error("获取参数类型失败", e);
				}
				return null;
			}).toList();
			// 获取执行的方法
			Method method = ReflectUtil.getMethod(clazz, snapshotParam.getMethodName(), paramClassList.toArray(new Class[]{}));
			// 获取到参数
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(snapshotParam.getParams());
			Object[] args = new Object[jsonNode.size()];
			for (int i = 0; i < jsonNode.size(); i++) {
				Class<?> aClass = paramClassList.get(i);
				args[i] = objectMapper.treeToValue(jsonNode.get(i), aClass);
			}
			// 执行方法
			method.invoke(bean, args);
			// 到这里说明执行成功, 直接删掉这一条记录即可
			messageInvokeRecordDao.removeById(messageInvokeRecord.getId());
		} catch (Exception e) {
			log.error("[安全执行服务]执行失败", e);
			retry(messageInvokeRecord, e.getMessage());
		} finally {
			SecureInvokeHolder.del();
		}
	}

	/**
	 * 异步调用
	 *
	 * @param messageInvokeRecord 消息调用记录对象
	 */
	public void asyncInvoke(MessageInvokeRecord messageInvokeRecord) {
		messageExecutor.execute(() -> invoke(messageInvokeRecord));
	}

	/**
	 * 重试
	 *
	 * @param messageInvokeRecord 消息调用记录对象
	 * @param errorMsg            错误信息
	 */
	private void retry(MessageInvokeRecord messageInvokeRecord, String errorMsg) {
		int retryCount = messageInvokeRecord.getRetryCount() + 1;
		MessageInvokeRecord newRecord = new MessageInvokeRecord();
		newRecord.setId(messageInvokeRecord.getId());
		newRecord.setFailReason(errorMsg);
		newRecord.setNextRetryTime(getNextRetryTime(retryCount));
		if (retryCount > messageInvokeRecord.getMaxRetryCount()) {
			newRecord.setInvokeStatus(InvokeStatusEnum.FAIL.getKey());
		} else {
			newRecord.setRetryCount(retryCount);
		}
		messageInvokeRecordDao.updateById(newRecord);
	}

	/**
	 * 采用退避算法
	 * <p>
	 * 重试时间指数上升 2m 4m 8m 16m
	 *
	 * @param retryCount 重试次数
	 * @return 重试时间
	 */
	private Date getNextRetryTime(Integer retryCount) {
		double waitMinutes = Math.pow(2, retryCount);
		return DateUtil.offsetMinute(new Date(), (int) waitMinutes);
	}
}
