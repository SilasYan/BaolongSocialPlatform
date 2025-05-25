package icu.baolong.social.module.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Data
public class UserUpdateReq implements Serializable {

	@Schema(description = "用户头像")
	private String userAvatar;

	@Schema(description = "用户简介")
	private String userProfile;

	@Schema(description = "用户性别（0-男, 1-女, 2-无）")
	private Integer userSex;

	@Schema(description = "徽章ID")
	private Long badgeId;

	@Schema(description = "头像框ID")
	private Long avatarFrameId;

	@Serial
	private static final long serialVersionUID = 1L;
}
