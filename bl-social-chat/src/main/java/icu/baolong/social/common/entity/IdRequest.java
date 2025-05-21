package icu.baolong.social.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * ID（主键）请求对象
 *
 * @author Silas Yan 2025-04-25 21:05
 */
@Data
public class IdRequest implements Serializable {

	private Long id;

	@Serial
	private static final long serialVersionUID = 1L;
}
