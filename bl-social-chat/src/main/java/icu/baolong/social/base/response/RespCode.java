package icu.baolong.social.base.response;

import lombok.Getter;

/**
 * 响应枚举
 *
 * @author Silas Yan 2025-04-24 22:20
 */
@Getter
public enum RespCode {

	/**
	 * 成功
	 */
	SUCCESS(0, "SUCCESS"),
	/**
	 * 失败
	 */
	FAILED(400, "失败啦~"),
	/**
	 * 请求方式错误
	 */
	REQUEST_METHOD_ERROR(405, "请求方式错误~"),
	/**
	 * 系统异常
	 */
	SYSTEM_ERROR(500, "系统开小差中, 请稍后重试~~"),
	/**
	 * 未登录
	 */
	NOT_LOGIN(401, "还未登录呢~"),
	/**
	 * 无对应角色
	 */
	NOT_ROLE(402, "没有对应角色捏~"),
	/**
	 * 无具体权限
	 */
	NOT_AUTH(403, "没有具体权限捏~"),
	/**
	 * 请求限流
	 */
	REQUEST_LIMIT(555, "访问太快啦~"),
	;

	/**
	 * 状态码
	 */
	private final int code;

	/**
	 * 描述信息
	 */
	private final String message;

	RespCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
