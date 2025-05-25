package icu.baolong.social.common.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 转换工具类，提供一行调用即可完成对象之间的转换。
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@SuppressWarnings(value = {"unchecked"})
public class ConvertUtils {

	// 缓存每个实体类对应的 BaseConverter
	private static final Map<Class<?>, BaseConverter<?>> CONVERTER_CACHE = new ConcurrentHashMap<>();

	/**
	 * 获取或创建指定实体类的转换器
	 */
	private static <D> BaseConverter<D> getConverter(Class<D> entityClass, String idFieldName) {
		return (BaseConverter<D>) CONVERTER_CACHE.computeIfAbsent(entityClass, cls -> new BaseConverter<>(idFieldName, cls));
	}

	/**
	 * 其他对象转数据库实体对象（自动识别 id 映射）
	 *
	 * @param source      源对象
	 * @param targetClass 目标实体类
	 * @param idFieldName 源对象中表示 ID 的字段名（如 userId / itemId）
	 * @return 数据库实体对象
	 */
	public static <T, D> D to(T source, Class<D> targetClass, String idFieldName) {
		return getConverter(targetClass, idFieldName).to(source);
	}

	/**
	 * 数据库实体对象转目标对象（自动识别 id 映射）
	 *
	 * @param sourceClass 目标类
	 * @param entity      数据库实体对象
	 * @param idFieldName 数据库实体中表示 ID 的字段名（如 userId / itemId）
	 * @return 目标对象
	 */
	public static <T, D> T from(Class<T> sourceClass, D entity, String idFieldName) {
		return getConverter((Class<D>) entity.getClass(), idFieldName).from(sourceClass, entity);
	}

	/**
	 * 列表转换 - 将其他对象列表转换为数据库实体对象列表
	 *
	 * @param sources     源对象列表
	 * @param targetClass 目标实体类
	 * @param idFieldName 源对象中表示 ID 的字段名（如 userId / itemId）
	 * @return 数据库实体对象列表
	 */
	public static <T, D> List<D> toList(List<T> sources, Class<D> targetClass, String idFieldName) {
		if (sources == null || sources.isEmpty()) {
			return new ArrayList<>();
		}
		BaseConverter<D> converter = getConverter(targetClass, idFieldName);
		return sources.stream().map(converter::to).collect(Collectors.toList());
	}

	/**
	 * 列表转换 - 将数据库实体对象列表转换为目标对象列表
	 *
	 * @param sourceClass 目标类
	 * @param entities    数据库实体对象列表
	 * @param idFieldName 数据库实体中表示 ID 的字段名（如 userId / itemId）
	 * @return 目标对象列表
	 */
	public static <T, D> List<T> fromList(Class<T> sourceClass, List<D> entities, String idFieldName) {
		if (entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		BaseConverter<D> converter = getConverter((Class<D>) entities.getFirst().getClass(), idFieldName);
		return entities.stream().map(entity -> converter.from(sourceClass, entity)).collect(Collectors.toList());
	}
}
