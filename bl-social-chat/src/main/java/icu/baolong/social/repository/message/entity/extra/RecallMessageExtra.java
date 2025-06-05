package icu.baolong.social.repository.message.entity.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 撤回消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RecallMessageExtra implements Serializable {

	@Schema(description = "消息ID")
	private Long messageId;

	@Serial
	private static final long serialVersionUID = 1L;
}
