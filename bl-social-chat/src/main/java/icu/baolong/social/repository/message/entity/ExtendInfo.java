package icu.baolong.social.repository.message.entity;

import icu.baolong.social.repository.message.entity.extra.AudioMessageExtra;
import icu.baolong.social.repository.message.entity.extra.EmojiMessageExtra;
import icu.baolong.social.repository.message.entity.extra.FileMessageExtra;
import icu.baolong.social.repository.message.entity.extra.ImageMessageExtra;
import icu.baolong.social.repository.message.entity.extra.RecallMessageExtra;
import icu.baolong.social.repository.message.entity.extra.VideoMessageExtra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息扩展信息（消息表中 extend_info 字段对应的实体类）
 *
 * @author Silas Yan 2025-06-03 22:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendInfo implements Serializable {

	/**
	 * 撤回消息扩展对象
	 */
	private RecallMessageExtra recallMessageExtra;

	/**
	 * 表情消息扩展对象
	 */
	private EmojiMessageExtra emojiMessageExtra;

	/**
	 * 图片消息扩展对象
	 */
	private ImageMessageExtra imageMessageExtra;

	/**
	 * 视频消息扩展对象
	 */
	private VideoMessageExtra videoMessageExtra;

	/**
	 * 语音消息扩展对象
	 */
	private AudioMessageExtra audioMessageExtra;

	/**
	 * 文件消息扩展对象
	 */
	private FileMessageExtra fileMessageExtra;

	@Serial
	private static final long serialVersionUID = 1L;
}
