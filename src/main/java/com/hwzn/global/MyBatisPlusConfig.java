package com.hwzn.global;

import com.baomidou.mybatisplus.annotation.DbType;
import com.hwzn.global.interceptor.CorsInterceptor;
import com.hwzn.global.interceptor.EasySqlInjector;
import org.springframework.context.annotation.Bean;
import com.hwzn.global.interceptor.TokenInterceptor;
import com.hwzn.global.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * @Author: hy
 * @Date: 2025/7/17 10:12
 * @Desc: 拦截器
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//注册拦截器，跨域处理在最前面
		InterceptorRegistration cor = registry.addInterceptor(new CorsInterceptor());
		InterceptorRegistration token = registry.addInterceptor(new TokenInterceptor());
		InterceptorRegistration admin = registry.addInterceptor(new AdminInterceptor());
		//配置拦截路径
		cor.addPathPatterns("/**");//加拦截路径才会走配置的拦截器（进行相关业务判断）
		token.addPathPatterns("/**");//加拦截路径才会走配置的拦截器（进行相关业务判断）
		admin.addPathPatterns("/*/admin/*");
		//配置不拦截路径
		token.excludePathPatterns("/error/**");//错误页放行
		token.excludePathPatterns("/*/*get*");
		token.excludePathPatterns("/*/*/*filter*");
		token.excludePathPatterns("/activity/readActivityInfoByID");
		token.excludePathPatterns("/*/*Login");
		token.excludePathPatterns("/user/verifyToken");
		token.excludePathPatterns("/*/*");
	}
	
	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,
	 * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		//自带分页拦截器
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
	//配置文件上传解析器
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		return new CommonsMultipartResolver();
	}
	//自定义批量插入
	@Bean
	public EasySqlInjector easySqlInjector() {
		return new EasySqlInjector();
	}
}