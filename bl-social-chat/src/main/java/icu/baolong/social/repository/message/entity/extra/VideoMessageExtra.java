package icu.baolong.social.repository.message.entity.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视频消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class VideoMessageExtra implements Serializable {

	@Schema(description = "视频地址")
	private String videoUrl;

	@Schema(description = "视频大小（字节）")
	private Long videoSize;

	@Schema(description = "视频时长（秒）")
	private Long videoDuration;

	@Schema(description = "视频名称（包含后缀）")
	private String videoName;

	@Schema(description = "封面地址")
	private String coverUrl;

	@Schema(description = "封面大小（字节）")
	private String coverSize;

	@Schema(description = "封面宽度（像素）")
	private Integer coverWidth;

	@Schema(description = "封面高度（像素）")
	private Integer coverHeight;

	@Serial
	private static final long serialVersionUID = 1L;
}
