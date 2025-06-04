package icu.baolong.social.base.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页请求对象
 *
 * @author Silas Yan 2025-04-25 20:55
 */
@Schema(name = "分页请求对象", description = "分页请求对象")
@Data
public class PageRequest implements Serializable {

	/**
	 * 当前页, 默认 1
	 */
	@Schema(description = "当前页", defaultValue = "1", example = "1")
	private int current = 1;

	/**
	 * 每页显示条数, 默认 10
	 */
	@Schema(description = "每页显示条数", defaultValue = "10", example = "10")
	private int pageSize = 10;

	/**
	 * 是否多个排序; true-多个排序, false-单个排序
	 */
	@Schema(description = "是否多个排序; true-多个排序, false-单个排序", defaultValue = "false", example = "false")
	private boolean multipleSort = false;

	/**
	 * 排序对象
	 */
	@Schema(description = "排序对象, multipleSort=false 时传值")
	private Sort sort;

	/**
	 * 排序对象列表
	 */
	@Schema(description = "排序对象列表, multipleSort=true 时传值")
	private List<Sort> sorts;

	/**
	 * 排序类
	 */
	@Data
	public static class Sort {
		/**
		 * 是否升序; true-升序, false-降序
		 */
		@Schema(description = "是否升序; true-升序, false-降序", defaultValue = "false", example = "false")
		private boolean asc = false;

		/**
		 * 排序字段
		 */
		@Schema(description = "排序字段", example = "createTime")
		private String field;
	}

	/**
	 * 分页对象
	 *
	 * @param clazz 类
	 * @param <T>   泛型
	 * @return 分页对象, MyBatisPlus 的分页
	 */
	public <T> Page<T> page(Class<T> clazz) {
		return new Page<>(this.current, this.pageSize);
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
