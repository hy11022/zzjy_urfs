package com.hwzn.global.interceptor;

import com.hwzn.util.JWTUtil;
import org.springframework.http.HttpMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author：hy
 * @Date：2025/7/17 10:11
 * @Desc：管理员权限过滤器
 */
public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){//防止请求被过滤
			return true;
		}
		
		boolean verify = JWTUtil.getRole(request.getHeader("token"))==1;
		if (!verify) {
			response.setStatus(402);
		}
		return verify;
	}
}