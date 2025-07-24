package com.hwzn.global.interceptor;

import com.hwzn.util.JWTUtil;
import org.springframework.http.HttpMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: hy
 * @Date: 2025/7/17 10:11
 * @Desc: 令牌过滤器
 */
public class TokenInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){//防止请求被过滤
			return true;
		}
		//验证token
		boolean verify = JWTUtil.verify(request.getHeader("token"));
		if (!verify) {
			response.setStatus(401);
		}

		//验证当前时间是否在续签时间之后，一般续签时间为有效时间的一半，token续签
		if( System.currentTimeMillis() > JWTUtil.getRefreshTime(request.getHeader("token"))){
			String token = request.getHeader("token");
			//续签token
			String newToken = JWTUtil.createToken(JWTUtil.getAccount(token),JWTUtil.getRole(token),JWTUtil.getEffectiveDuration(token));
			response.setHeader("newtoken",newToken);
		}
		return  verify;
	}
}