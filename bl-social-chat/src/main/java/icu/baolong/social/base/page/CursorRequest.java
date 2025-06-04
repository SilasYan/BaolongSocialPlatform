package icu.baolong.social.base.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 游标分页请求
 *
 * @author Silas Yan 2025-05-31 01:14
 */
@Schema(name = "游标分页请求", description = "游标分页请求")
@Data
public class CursorRequest implements Serializable {

	@Schema(description = "游标（初始为null, 后续请求需要附带上一次翻页的游标）")
	private String cursor;

	@Schema(description = "每页条数", defaultValue = "10", example = "10")
	private int pageSize = 10;

	/**
	 * 游标分页对象
	 *
	 * @param <T> 泛型
	 * @return 游标分页对象
	 */
	public <T> Page<T> page() {
		// 第三个参数表示是否进行 count 查询
		return new Page<>(1, this.pageSize, false);
	}

	/**
	 * 游标分页对象
	 *
	 * @param clazz 类
	 * @param <T>   泛型
	 * @return 游标分页对象
	 */
	public <T> Page<T> page(Class<T> clazz) {
		// 第三个参数表示是否进行 count 查询
		return new Page<>(1, this.pageSize, false);
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
