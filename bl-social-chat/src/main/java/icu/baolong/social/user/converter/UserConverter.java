package icu.baolong.social.user.converter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import icu.baolong.social.user.domain.entity.User;

import java.lang.reflect.InvocationTargetException;

/**
 * 转换类
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
public class UserConverter {

	private final static CopyOptions TO_OPTION = CopyOptions.create().setFieldMapping(MapUtil.of("userId", "id"));
	private final static CopyOptions FROM_OPTION = CopyOptions.create().setFieldMapping(MapUtil.of("id", "userId"));

	/**
	 * 其他对象 转为 数据库对象
	 *
	 * @param t   原对象
	 * @param <T> 泛型
	 * @return DO 对象
	 */
	public static <T> User to(T t) {
		User d = new User();
		BeanUtil.copyProperties(t, d, TO_OPTION);
		return d;
	}

	/**
	 * 数据库对象 转为 其他对象
	 *
	 * @param clazz 目标对象类型
	 * @param d     源对象
	 * @return 目标对象
	 */
	public static <T> T from(Class<T> clazz, User d) {
		try {
			T t = clazz.getDeclaredConstructor().newInstance();
			BeanUtil.copyProperties(d, t, FROM_OPTION);
			return t;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			throw new RuntimeException("无法创建类型 [" + clazz.getName() + "] 的实例，请确认是否包含无参构造函数", e);
		}
	}
}
