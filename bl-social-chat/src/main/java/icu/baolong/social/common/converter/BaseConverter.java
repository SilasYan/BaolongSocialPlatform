package icu.baolong.social.common.converter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 基础转换器，用于在不同类型的对象之间进行属性拷贝。
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
public class BaseConverter<D> {

	private final Class<D> doClass;
	private final CopyOptions toOption;
	private final CopyOptions fromOption;

	/**
	 * 构造函数
	 *
	 * @param idFieldName 业务对象中的 ID 字段名（如 "userId", "itemId"）
	 * @param doClass     数据库实体类的 Class 对象
	 */
	public BaseConverter(String idFieldName, Class<D> doClass) {
		this.doClass = doClass;
		this.toOption = CopyOptions.create().setFieldMapping(MapUtil.of(idFieldName, "id"));
		this.fromOption = CopyOptions.create().setFieldMapping(MapUtil.of("id", idFieldName));
	}

	/**
	 * 将其他对象转换为数据库对象 D
	 *
	 * @param t   源对象
	 * @param <T> 源对象类型
	 * @return 数据库对象
	 */
	public <T> D to(T t) {
		try {
			D d = doClass.getDeclaredConstructor().newInstance();
			BeanUtil.copyProperties(t, d, toOption);
			return d;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			throw new RuntimeException("无法创建类型 [" + doClass.getName() + "] 的实例，请确认是否包含无参构造函数", e);
		}
	}

	/**
	 * 将数据库对象 D 转换为目标类型 T
	 *
	 * @param clazz 目标类型
	 * @param d     数据库对象
	 * @param <T>   目标类型
	 * @return 转换后的目标对象
	 */
	public <T> T from(Class<T> clazz, D d) {
		try {
			T t = clazz.getDeclaredConstructor().newInstance();
			BeanUtil.copyProperties(d, t, fromOption);
			return t;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			throw new RuntimeException("无法创建类型 [" + clazz.getName() + "] 的实例，请确认是否包含无参构造函数", e);
		}
	}
}
