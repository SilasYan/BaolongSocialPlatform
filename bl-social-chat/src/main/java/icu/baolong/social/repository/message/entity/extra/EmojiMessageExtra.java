package icu.baolong.social.repository.message.entity.extra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 表情消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@Data
public class EmojiMessageExtra implements Serializable {

	@Schema(description = "表情地址")
	private String emojiUrl;

	@Serial
	private static final long serialVersionUID = 1L;
}
