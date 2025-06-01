package icu.baolong.social.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import icu.baolong.social.common.base.page.CursorRequest;
import icu.baolong.social.common.base.page.CursorResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 游标分页工具类
 *
 * @author Silas Yan 2025-05-31 01:32
 */
public class CursorUtil {

	/**
	 * 构建并执行游标分页
	 *
	 * @param dao           执行查询的dao对象
	 * @param cursorRequest 游标分页请求对象
	 * @param initWrapper   额外条件
	 * @param cursorColumn  游标字段
	 * @param <T>           泛型
	 * @return 游标分页响应对象
	 */
	public static <T> CursorResponse<T> buildAndExecute(IService<T> dao,
														CursorRequest cursorRequest,
														Consumer<LambdaQueryWrapper<T>> initWrapper,
														SFunction<T, ?> cursorColumn) {
		// 获取游标字段的类型
		Class<?> cursorType = LambdaUtil.getType(cursorColumn);
		// 创建查询器
		LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
		// 设置额外条件
		initWrapper.accept(wrapper);
		// 设置游标条件
		if (StrUtil.isNotBlank(cursorRequest.getCursor())) {
			wrapper.lt(cursorColumn, parseCursorType(cursorRequest.getCursor(), cursorType));
		}
		// 设置游标的排序
		wrapper.orderByDesc(cursorColumn);
		// 执行查询
		Page<T> page = dao.page(cursorRequest.page(), wrapper);
		List<T> records = page.getRecords();
		// 获取到游标
		String cursor = Optional.ofNullable(CollectionUtil.getLast(records))
				.map(cursorColumn)
				.map(CursorUtil::toCursor)
				.orElse(null);
		// 判断是否有最后一页
		boolean isLastPage = records.size() != cursorRequest.getPageSize();
		return new CursorResponse<>(cursor, isLastPage, records);
	}

	/**
	 * 解析游标类型
	 *
	 * @param cursor      游标
	 * @param cursorClass 游标字段实际类型
	 * @return 游标对象
	 */
	private static Object parseCursorType(String cursor, Class<?> cursorClass) {
		if (Date.class.isAssignableFrom(cursorClass)) {
			return new Date(Long.parseLong(cursor));
		} else {
			return cursor;
		}
	}

	/**
	 * 将游标对象转换为游标字符串
	 *
	 * @param o 游标对象
	 * @return 游标字符串
	 */
	private static String toCursor(Object o) {
		if (o instanceof Date) {
			return String.valueOf(((Date) o).getTime());
		} else {
			return o.toString();
		}
	}
}
