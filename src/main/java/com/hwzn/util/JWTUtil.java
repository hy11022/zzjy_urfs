package com.hwzn.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import java.util.HashMap;
import com.auth0.jwt.JWT;
import java.util.Date;
import java.util.Map;

/**
 * @Author： hy
 * @Date： 2025/7/17 11:46
 * @Desc：
 */
public class JWTUtil {
	//token秘钥，后期可以考虑获取动态密钥
	public static final String TOKEN_SECRET = "nuE-20IL";
	
	/**
	 * @param account: 用户账户
	 * @param tokenEffectiveDuration: 有效时长（分）
	 * @description: 创建token
	 * @return: 加密的token
	 **/
	public static String createToken(String account,Integer role,Integer tokenEffectiveDuration){
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		Map<String, Object> header = new HashMap<>();
		return JWT.create().withHeader(header)
				.withClaim("account", account)
				.withClaim("role", role)
				.withClaim("effectiveDuration", tokenEffectiveDuration)
				.withClaim("refreshTime",new Date(System.currentTimeMillis()+tokenEffectiveDuration*30*1000))
				.withExpiresAt(new Date(System.currentTimeMillis()+tokenEffectiveDuration*60*1000))
				.sign(algorithm);
	}
	
	/**
	 * 校验token是否正确
	 * @param token 密钥
	 * @return 是否正确
	 */
	public static boolean verify(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
	
	/**
	 * 获取登陆用户账户    *
	 * @param token 令牌
	 * @return 返回值
	 */
	public static String getAccount(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("account").asString();
		} catch (JWTDecodeException ignored) {
			return null;
		}
	}
	
	/**
	 * 获取令牌有效时长
	 * @param token 令牌
	 * @return 返回值
	 */
	public static Integer getEffectiveDuration(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return Integer.parseInt(jwt.getClaim("effectiveDuration").toString());
		} catch (JWTDecodeException e) {
			return 0;
		}
	}

	/**
	 * 获取登陆用户角色
	 * @param token 令牌
	 * @return 返回值
	 **/
	public static Integer getRole(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return Integer.parseInt(jwt.getClaim("role").toString());
		} catch (JWTDecodeException e) {
			return 3;
		}
	}

	/**
	 * 获取令牌刷新时间
	 * @param token 令牌
	 * @return 返回值
	 */
	public static Long getRefreshTime(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return Long.parseLong(jwt.getClaim("refreshTime").toString());
		} catch (JWTDecodeException e) {
			return 0L;
		}
	}
}