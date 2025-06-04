package icu.baolong.social.base.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 游标分页响应
 *
 * @author Silas Yan 2025-05-31 01:24
 */
@Schema(name = "游标分页响应", description = "游标分页响应")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursorResponse<T> implements Serializable {

	@Schema(description = "游标（下次请求需要附带这个游标）")
	private String cursor;

	@Schema(description = "是否最后一页")
	private Boolean isLastPage = false;

	/**
	 * 数据列表
	 */
	@Schema(description = "数据列表", defaultValue = "[]")
	private List<T> records = Collections.emptyList();

	/**
	 * 构建响应
	 *
	 * @param cursor     游标
	 * @param isLastPage 是否最后一页
	 * @param records    数据列表
	 * @param <T>        数据类型
	 * @return 游标分页响应
	 */
	public static <T> CursorResponse<T> build(String cursor, Boolean isLastPage, List<T> records) {
		return new CursorResponse<>(cursor, isLastPage, Optional.ofNullable(records).orElse(Collections.emptyList()));
	}

	/**
	 * 构建响应
	 *
	 * @param cursorResponse 游标分页响应
	 * @param records        数据列表
	 * @param <T>            数据类型
	 * @return 游标分页响应
	 */
	public static <T> CursorResponse<T> build(CursorResponse<?> cursorResponse, List<T> records) {
		return build(cursorResponse.getCursor(), cursorResponse.getIsLastPage(), records);
	}

	/**
	 * 空响应
	 *
	 * @param <T> 数据类型
	 * @return 游标分页响应
	 */
	public static <T> CursorResponse<T> empty() {
		return new CursorResponse<>(null, true, Collections.emptyList());
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
