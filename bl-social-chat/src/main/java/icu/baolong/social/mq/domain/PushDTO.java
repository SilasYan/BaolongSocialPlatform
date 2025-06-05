package icu.baolong.social.mq.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import icu.baolong.social.service.websocket.domain.base.WSResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * MQ推送DTO
 *
 * @author Silas Yan 2025-06-05 21:10
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushDTO implements Serializable {

	/**
	 * 推送类型（0-用户、1-全员）
	 */
	private Integer pushType;

	/**
	 * 推送用户ID列表
	 */
	private List<Long> userIdList;

	/**
	 * 推送消息响应
	 */
	private WSResp<?> wsResp;

	@Serial
	private static final long serialVersionUID = 1L;
}
