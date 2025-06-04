package icu.baolong.social.repository.message.entity.extra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@Data
public class ImageMessageExtra implements Serializable {

	@Schema(description = "图片地址")
	private String imageUrl;

	@Schema(description = "图片大小（字节）")
	private Long imageSize;

	@Schema(description = "宽度（像素）")
	private Integer width;

	@Schema(description = "高度（像素）")
	private Integer height;

	@Serial
	private static final long serialVersionUID = 1L;
}
