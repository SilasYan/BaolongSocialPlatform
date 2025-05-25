package icu.baolong.social.module.items.domain.response;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 物品响应
 *
 * @author Silas Yan 2025-05-25 16:18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsResp implements Serializable {

	/**
	 * 物品ID
	 */
	private Long itemId;

	/**
	 * 物品类型（0-改名卡, 1-徽章, 2-头像框）
	 */
	private Integer itemType;

	/**
	 * 物品名称
	 */
	private String itemName;

	/**
	 * 物品描述
	 */
	private String itemDesc;

	/**
	 * 物品图片
	 */
	private String itemImage;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}
