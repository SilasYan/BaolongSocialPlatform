package icu.baolong.social.common.page;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 分页响应对象
 *
 * @author Silas Yan 2025-04-25 20:57
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {

	/**
	 * 当前页
	 */
	@Schema(description = "当前页", defaultValue = "1", example = "1")
	private long current = 1;

	/**
	 * 每页显示条数，默认 10
	 */
	@Schema(description = "每页显示条数", defaultValue = "10", example = "10")
	private long pageSize = 10;

	/**
	 * 总数
	 */
	@Schema(description = "总数", defaultValue = "0", example = "0")
	private long total = 0;

	/**
	 * 总页数
	 */
	@Schema(description = "总页数", defaultValue = "0", example = "0")
	private long pages = 0;

	/**
	 * 数据列表
	 */
	@Schema(description = "数据列表", defaultValue = "[]")
	private List<T> records = Collections.emptyList();

	/**
	 * 将 MyBatis-Plus 的分页对象转换为分页响应对象
	 *
	 * @param page MyBatis-Plus 的分页对象
	 * @param <T>  泛型
	 * @return 分页响应对象
	 */
	public static <T> PageResponse<T> to(IPage<T> page) {
		return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), page.getRecords());
	}

	/**
	 * 将 MyBatis-Plus 的分页对象转换为分页响应对象
	 *
	 * @param page    MyBatis-Plus 的分页对象
	 * @param records 实际对象数据集合
	 * @param <T>     泛型
	 * @return 分页响应对象
	 */
	public static <T> PageResponse<T> to(IPage page, List<T> records) {
		return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), records);
	}

	/**
	 * 将 MyBatis-Plus 的分页对象转换为分页响应对象
	 *
	 * @param page  MyBatis-Plus 的分页对象
	 * @param clazz 实际分页对象类型
	 * @param <T>   泛型
	 * @return 分页响应对象
	 */
	public static <T> PageResponse<T> to(IPage page, Class<T> clazz) {
		return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(),
				Optional.ofNullable(page.getRecords())
						.orElse(Collections.emptyList())
						.stream()
						.map(user -> {
							T vo = BeanUtils.instantiateClass(clazz);
							BeanUtil.copyProperties(user, vo);
							return vo;
						}).toList()
		);
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
