package icu.baolong.social.common.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础响应类
 *
 * @author Silas Yan 2025-04-24 22:23
 */
@Data
public class BaseResponse<T> implements Serializable {

	/**
	 * 状态码
	 */
	private int code;

	/**
	 * 描述信息
	 */
	private String message;

	/**
	 * 响应数据
	 */
	private T data;

	public BaseResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public BaseResponse(int code, String message) {
		this(code, message, null);
	}

	public BaseResponse(RespCode respCode) {
		this(respCode.getCode(), respCode.getMessage(), null);
	}

	public BaseResponse(RespCode respCode, T data) {
		this(respCode.getCode(), respCode.getMessage(), data);
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
