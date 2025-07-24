package com.hwzn.global.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: hy
 * @Date: 2025/7/17 10:12
 * @Desc: 跨域
 */
public class CorsInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Expose-Headers", "newtoken");
		
		// 如果是OPTIONS则结束请求
		if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return false;
		}
		return true;
	}
}