package icu.baolong.social.module.items.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 物品查询请求
 *
 * @author Silas Yan 2025-05-25 16:18
 */
@Data
public class ItemsQueryReq implements Serializable {

	@Schema(description = "物品类型", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer itemType;

	@Serial
	private static final long serialVersionUID = 1L;
}
