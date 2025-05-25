package icu.baolong.social.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户徽章响应
 *
 * @author Silas Yan 2025-05-25 18:23
 */
@Builder
@Data
public class UserBadgeResp implements Serializable {

	@Schema(description = "徽章ID")
	private Long badgeId;

	@Schema(description = "徽章名称")
	private String badgeName;

	@Schema(description = "徽章描述")
	private String badgeDesc;

	@Schema(description = "徽章图片")
	private String badgeImage;

	@Schema(description = "获取时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date obtainTime;

	@Schema(description = "拥有状态（0-未拥有、1-已拥有）")
	private Integer ownStatus;

	@Schema(description = "佩戴状态（0-未佩戴、1-已佩戴）")
	private Integer wearStatus;

	@Serial
	private static final long serialVersionUID = 1L;
}
