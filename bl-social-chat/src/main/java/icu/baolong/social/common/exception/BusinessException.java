package icu.baolong.social.common.exception;

import icu.baolong.social.common.response.RespCode;
import lombok.Getter;

/**
 * 业务异常类
 *
 * @author Silas Yan 2025-05-21 21:14
 */
@Getter
public class BusinessException extends RuntimeException {

	private final int code;

	public BusinessException(String message) {
		super(message);
		this.code = RespCode.FAILED.getCode();
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(RespCode respCode) {
		super(respCode.getMessage());
		this.code = respCode.getCode();
	}

	public BusinessException(RespCode respCode, String message) {
		super(message);
		this.code = respCode.getCode();
	}
}
