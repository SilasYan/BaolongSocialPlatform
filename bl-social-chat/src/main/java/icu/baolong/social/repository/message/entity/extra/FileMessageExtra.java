package icu.baolong.social.repository.message.entity.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件消息扩展对象
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FileMessageExtra implements Serializable {

	@Schema(description = "文件地址")
	private String fileUrl;

	@Schema(description = "文件大小（字节）")
	private Long fileSize;

	@Schema(description = "文件类型")
	private String fileType;

	@Schema(description = "文件名称（包含后缀）")
	private String fileName;

	@Schema(description = "文件后缀")
	private String fileSuffix;

	@Serial
	private static final long serialVersionUID = 1L;
}
