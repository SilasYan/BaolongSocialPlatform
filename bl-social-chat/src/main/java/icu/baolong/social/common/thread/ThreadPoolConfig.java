package icu.baolong.social.common.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 *
 * @author Silas Yan 2025-04-05:20:54
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

	public static final String COMMON_EXECUTOR = "commonExecutor";

	public static final String EMAIL_EXECUTOR = "emailExecutor";

	public static final String PUSH_EXECUTOR = "pushExecutor";

	/**
	 * 默认/主线程池
	 */
	@Override
	public Executor getAsyncExecutor() {
		return this.commonThreadPool();
	}

	/**
	 * 日志记录线程池
	 *
	 * @return 线程池任务执行器
	 */
	@Bean(name = COMMON_EXECUTOR)
	public ThreadPoolTaskExecutor commonThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数,它是可以同时被执行的线程数量
		executor.setCorePoolSize(10);
		// 设置最大线程数,缓冲队列满了之后会申请超过核心线程数的线程
		executor.setMaxPoolSize(10);
		// 设置缓冲队列容量,在执行任务之前用于保存任务
		executor.setQueueCapacity(200);
		// 设置线程生存时间（秒）,当超过了核心线程出之外的线程在生存时间到达之后会被销毁
		executor.setKeepAliveSeconds(60);
		// 设置线程名称前缀
		executor.setThreadNamePrefix("Common-Executor-");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 设置未捕获的异常处理类
		executor.setThreadFactory(new CustomThreadFactory(executor));
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 初始化
		executor.initialize();
		return executor;
	}

	/**
	 * 邮件发送线程池
	 *
	 * @return 线程池任务执行器
	 */
	@Bean(name = EMAIL_EXECUTOR)
	public ThreadPoolTaskExecutor emailThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数,它是可以同时被执行的线程数量
		executor.setCorePoolSize(10);
		// 设置最大线程数,缓冲队列满了之后会申请超过核心线程数的线程
		executor.setMaxPoolSize(10);
		// 设置缓冲队列容量,在执行任务之前用于保存任务
		executor.setQueueCapacity(50);
		// 设置线程生存时间（秒）,当超过了核心线程出之外的线程在生存时间到达之后会被销毁
		executor.setKeepAliveSeconds(60);
		// 设置线程名称前缀
		executor.setThreadNamePrefix("Email-Executor-");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 设置未捕获的异常处理类
		executor.setThreadFactory(new CustomThreadFactory(executor));
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 初始化
		executor.initialize();
		return executor;
	}

	/**
	 * 推送线程池
	 *
	 * @return 线程池任务执行器
	 */
	@Bean(name = PUSH_EXECUTOR)
	public ThreadPoolTaskExecutor pushThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(16);
		executor.setMaxPoolSize(16);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("Push-Executor-");
		// 拒绝策略: 满了直接丢弃
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		executor.setThreadFactory(new CustomThreadFactory(executor));
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}
}
