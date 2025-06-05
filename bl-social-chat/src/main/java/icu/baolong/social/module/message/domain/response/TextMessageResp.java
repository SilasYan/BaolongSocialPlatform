package icu.baolong.social.module.message.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文本消息响应
 *
 * @author Silas Yan 2025-06-05 20:43
 */
@Schema(name = "文本消息响应", description = "文本消息响应")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageResp implements Serializable {

	@Schema(description = "消息内容")
	private String content;

	@Serial
	private static final long serialVersionUID = 1L;
}
