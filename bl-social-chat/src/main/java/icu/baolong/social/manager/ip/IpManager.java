package icu.baolong.social.manager.ip;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.repository.user.entity.IpInfo;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * IP处理服务
 *
 * @author Silas Yan 2025-05-26 22:20
 */
@Slf4j
@Component
public class IpManager implements DisposableBean {

	private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
			1,
			1,
			0L,
			TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>(500),
			new NamedThreadFactory("IpManager", null, false)
	);

	@Resource
	private UserDao userDao;

	/**
	 * 刷新IP信息
	 *
	 * @param userId     用户ID
	 * @param retryCount 重试次数
	 */
	public void refreshIpInfo(Long userId, Integer retryCount) {
		if (retryCount == null || retryCount < 0) {
			retryCount = 3;
		}
		if (retryCount > 5) {
			retryCount = 5;
		}
		Integer finalRetryCount = retryCount;
		// 执行
		EXECUTOR.execute(() -> {
			User user = userDao.getById(userId);
			IpInfo ipInfo = user.getIpInfo();
			String lastLoginIp = ipInfo.getLastLoginIp();
			if (StrUtil.isBlank(lastLoginIp)) {
				log.error("[IP]暂未存储IP信息, userId: {}", userId);
				return;
			}
			// 判断是否需要刷新IP, 如果数据库中最后登录的IP和当前最后登录的IP相同, 则不需要刷新
			boolean present = Optional.ofNullable(ipInfo.getLastLoginIpDetail())
					.map(IpInfo.IpDetail::getIp)
					.filter(ip -> ip.equals(lastLoginIp))
					.isPresent();
			if (present) {
				log.info("[IP]IP未发生变化, IP: {}, userId: {}", lastLoginIp, userId);
				return;
			}
			IpInfo.IpDetail ipDetail = getIpDetail(lastLoginIp, finalRetryCount);
			if (ObjectUtil.isNull(ipDetail)) {
				log.error("[IP]IP详情获取失败, IP: {}, userId: {}", lastLoginIp, userId);
				return;
			}
			// 更新IP信息
			if (StrUtil.isBlank(ipInfo.getRegisterIp()) || ipInfo.getRegisterIp().equals(ipDetail.getIp())) {
				ipInfo.setRegisterIp(ipDetail.getIp());
				ipInfo.setRegisterIpDetail(ipDetail);
			}
			if (ipInfo.getLastLoginIp().equals(ipDetail.getIp())) {
				ipInfo.setLastLoginIpDetail(ipDetail);
			}
			User updateUser = new User();
			updateUser.setUserId(userId);
			updateUser.setIpInfo(ipInfo);
			boolean result = userDao.updateById(updateUser);
			if (result) {
				log.info("[IP]IP信息刷新成功, IP: {}, userId: {}", lastLoginIp, userId);
			} else {
				log.error("[IP]IP信息刷新失败, IP: {}, userId: {}", lastLoginIp, userId);
			}
		});
	}

	/**
	 * 获取IP详情
	 *
	 * @param ip         IP地址
	 * @param retryCount 重试次数
	 * @return IP详情
	 */
	public static IpInfo.IpDetail getIpDetail(String ip, int retryCount) {
		for (int i = 0; i < retryCount; i++) {
			IpInfo.IpDetail ipDetail = getIpDetail(ip);
			if (ObjectUtil.isNotNull(ipDetail)) {
				return ipDetail;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error("[IP]解析IP详情失败, IP: {}, 次数: {}", ip, retryCount + 1, e);
			}
		}
		return null;
	}

	/**
	 * 获取IP详情
	 *
	 * @param ip IP地址
	 * @return IP详情
	 */
	public static IpInfo.IpDetail getIpDetail(String ip) {
		String body = HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
		JSONObject jsonObject = JSONUtil.parseObj(body);
		if (jsonObject.getInt("code").equals(0)) {
			IpInfo.IpDetail ipDetail = jsonObject.getJSONObject("data").toBean(IpInfo.IpDetail.class);
			System.out.println(JSONUtil.parse(ipDetail));
			return ipDetail;
		}
		return null;
	}

	/**
	 * 优雅停机
	 */
	@Override
	public void destroy() throws Exception {
		EXECUTOR.shutdown();
		// 最多等30秒，处理不完就强制关闭
		if (!EXECUTOR.awaitTermination(30, TimeUnit.SECONDS)) {
			if (log.isErrorEnabled()) {
				log.error("Timed out while waiting for executor [{}] to terminate", EXECUTOR);
			}
		}
	}
}
