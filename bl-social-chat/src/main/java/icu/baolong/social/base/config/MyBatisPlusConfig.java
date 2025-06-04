package icu.baolong.social.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * MyBatis-Plus 配置
 *
 * @author Silas Yan 2025-04-24 23:09
 */
@Configuration
@MapperScan(value = {"icu.baolong.social.repository.**.mapper"})
public class MyBatisPlusConfig implements MetaObjectHandler {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 配置分页插件
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	/**
	 * 新增时的字段填充
	 *
	 * @param metaObject 元对象
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
	}

	/**
	 * 更新时的字段填充
	 *
	 * @param metaObject 元对象
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("editTime", new Date(), metaObject);
	}
}
