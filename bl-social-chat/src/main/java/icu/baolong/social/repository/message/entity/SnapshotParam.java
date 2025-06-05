package icu.baolong.social.repository.message.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 快照参数（消息表中 snapshot_param 字段对应的实体类）
 *
 * @author Silas Yan 2025-06-04 21:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotParam implements Serializable {

	/**
	 * 类名
	 */
	private String className;

	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 参数
	 */
	private String params;

	/**
	 * 参数类型
	 */
	private String paramTypes;

	@Serial
	private static final long serialVersionUID = 1L;
}
