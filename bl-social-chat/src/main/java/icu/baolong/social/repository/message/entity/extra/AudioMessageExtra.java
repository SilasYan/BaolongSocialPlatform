package icu.baolong.social.repository.message.entity.extra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 语音消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@Data
public class AudioMessageExtra implements Serializable {

	@Schema(description = "语音地址")
	private String audioUrl;

	@Schema(description = "语音大小（字节）")
	private Long audioSize;

	@Schema(description = "语音时长（秒）")
	private Long audioDuration;

	@Serial
	private static final long serialVersionUID = 1L;
}
