package icu.baolong.social.function.limit;

/**
 * 限流类型枚举
 *
 * @author Silas Yan 2025-05-22 22:58
 */
public enum LimitType {
	/**
	 * 默认策略: 就是 IP 限流
	 */
	DEFAULT,
	/**
	 * 根据请求者 IP 进行限流
	 */
	IP,
	/**
	 * 根据请求者 IP 进行限流，加上类名、方法名作为前缀
	 */
	CLASS_METHOD_IP,
	/**
	 * 根据请求者 UID 进行限流
	 */
	UID,
	/**
	 * 根据请求者 UID 进行限流，加上类名、方法名作为前缀
	 */
	CLASS_METHOD_UID,
	/**
	 * 全局限流，以类名、方法名
	 */
	GLOBAL;
}
