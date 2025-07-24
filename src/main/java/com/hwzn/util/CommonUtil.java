package com.hwzn.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import cn.hutool.http.useragent.UserAgentUtil;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.entity.LoginLogEntity;
import cn.hutool.http.useragent.UserAgent;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.pojo.entity.DbTbLogEntity;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import cn.hutool.json.JSONArray;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import java.util.*;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 16:21
 * @Desc: 通用工具类
 */
@Component
public class CommonUtil {

	private static final String APP_KEY="MKFBZ-3RP6J-XXSF5-X4BKC-C4ZQV-TSBA7";
	
	private static Environment env;
	
	@Resource
	public void set(Environment env) {
		CommonUtil.env = env;
	}
	
	//获取服务端操作系统类别
	public static String getOperationSystemType() {
		String ostype = "linux";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			ostype = "windows";
		}
		return ostype;
	}
	
	//获取随机UUID,去除“-”
	public static String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "").toUpperCase();
	}
	
	//本地路径转化为网络路径
	public static String localPathToUrl(String localPath){
		return env.getProperty("server.Server")+"/"+localPath.substring(localPath.indexOf(Objects.requireNonNull(env.getProperty("server.Project"))));
	}

	//通过request获取客户端IP
	public static String getClientIpByRequest(HttpServletRequest request){

		String ip = request.getHeader("x-forwarded-for");
		if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	// 通过IP获取现实地址信息
	public static JSONObject getAddressInfoByIp(String ip){
		try {
			String res = HttpUtil.get("https://apis.map.qq.com/ws/location/v1/ip?ip="+ip+"&key="+APP_KEY);
			return JSONUtil.parseObj(res).getJSONObject("result");
		}catch (Exception e){
			System.out.println("通过ID获取地理地址错误，错误为："+e.getMessage());
			return null;
		}
	}

	/**
	 * 获取登录日志
	 * @param request 请求体
	 * @param createrAccount 创建账户
	 */
	public static LoginLogEntity getLoginLog(HttpServletRequest request,String createrAccount) {
		LoginLogEntity loginLogEntity=new LoginLogEntity();
		String ip = getClientIpByRequest(request);
		UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
		loginLogEntity.setIp(ip);
//		loginLogEntity.setDevice(ua.isMobile()?"mobile":"PC");
		loginLogEntity.setOs(ua.getPlatform().toString());
		loginLogEntity.setOsVersion(ua.getOs().toString());
		loginLogEntity.setBrowser(ua.getBrowser().toString());
		loginLogEntity.setBrowserVersion(ua.getVersion());
		loginLogEntity.setCreaterAccount(createrAccount);
		return loginLogEntity;
	}

	/**
	 * 获取用户日志
	 * @param des 描述
	 * @param createrAccount 创建账户
	 */
	public static UserLogEntity getUserLog(String des, String createrAccount) {
		UserLogEntity userLogEntity=new UserLogEntity();
		userLogEntity.setDes(des);
		userLogEntity.setCreaterAccount(createrAccount);
		return userLogEntity;
	}

	/**
	 * 获取数据库数据表日志
	 * @param tbName 数据表名称
	 * @param rId 记录ID
	 * @param des 描述
	 * @param createrAccount 创建账户
	 */
	public static DbTbLogEntity getDbTbLog(String tbName, Integer rId, String des,String createrAccount) {
		DbTbLogEntity dbTbLogEntity=new DbTbLogEntity();
		dbTbLogEntity.setDbName("zzjy_urfs");
		dbTbLogEntity.setTbName(tbName);
		dbTbLogEntity.setRId(rId);
		dbTbLogEntity.setDes(des);
		dbTbLogEntity.setCreaterAccount(createrAccount);
		return dbTbLogEntity;
	}

	/**
	 * 获取项目名后面的路径
	 */
	public static String getPathAfterProjectName(String url) {
		int index = url.lastIndexOf("zzjy_urfs");
		return url.substring(index);
	}

	// type=pre获取倒数第一个.之前字符；type=after获取倒数第一个.之后字符,常用于截取后缀。
	public static String getLastPointStr(String path, String type) {
		// index为最后的“.”字符所在的位置
		int index = path.lastIndexOf(".");
		String result = "";
		if (type.equals("pre")) {
			result = path.substring(0, index);
		} else if (type.equals("after")) {
			result = path.substring(index + 1);
		}
		return result;
	}

	// type=pre获取倒数第一个/之前字符.type=after获取倒数第一个/之后字符
	public static String getLastIndexStr(String url, String type) {
		// index为最后的"/"字符所在的位置
		int index = url.lastIndexOf("/");
		String result = "";
		if (type.equals("pre")) {
			result = url.substring(0, index);
		} else if (type.equals("after")) {
			result = url.substring(index + 1);
		}
		return result;
	}

	public static String getFirstIndexStr(String url) {// 获取第一个\之前字符
		// index为第一个“\”字符所在的位置
		int index = url.indexOf("\\");
		return url.substring(0, index);
	}
	/**
	 * url文件路径转本地路径
	 */
	public static String urlToLocalPath(String url) {
		String basePath = System.getProperty("catalina.home");
		String ostype = getOperationSystemType();
		StringBuilder path = new StringBuilder();
		if (StrUtil.isEmpty(basePath)) {
			if (ostype.equals("linux")) {
				path.append("usr/local/source/").append(getPathAfterProjectName(url));
			} else {
				path.append("D:/source/").append(getPathAfterProjectName(url));
			}
		} else {
			if (ostype.equals("1")) {
				path.append(getLastIndexStr(basePath, "pre")).append("/source/").append(getPathAfterProjectName(url));
			} else {
				path.append(getFirstIndexStr(basePath)).append("/source/").append(getPathAfterProjectName(url));//win经过这里
			}
		}
		return path.toString();
	}

	/**
	 * 数组之间差集
	 * @param priArray 原始数组
	 * @param conArray 对比数组
	 * @return JSONArray
	 */
	public  static List<Object> removeMixedList(JSONArray priArray,JSONArray conArray){
		return CollectionUtil.subtractToList(priArray,conArray);
	}

	/**
	 * 从富文本中获取文件路径数组
	 * @param richText 富文本内容
	 * @return JSONArray
	 */
	public static JSONArray getFilePathListByRichText(String richText){
		List<String> strArr = StrUtil.split(richText,"src=\"");
		JSONArray jsonArray = new JSONArray();
		for (String s:strArr){
			String reStr = StrUtil.split(s,"\"").get(0);
			if((reStr.contains("http:")||reStr.contains("https:")) && reStr.contains("zzjy_urfs")){
				jsonArray.add(reStr);
			}
		}
		return jsonArray;
	}

	/**
	 * 处理排序查询
	 * @param queryWrapper 查询条件
	 * @param sortArray 排序数组
	 * @param tableName 表名
	 * @return 查询条件
	 **/
	public static QueryWrapper<Object> handleSortQuery(QueryWrapper queryWrapper, JSONArray sortArray, String tableName){
		if(!CollUtil.isEmpty(sortArray)){//按前端传来的参数排序
			for (Object o : sortArray) {
				JSONObject sortObj = JSONUtil.parseObj(o);
				if (sortObj.get("sort").equals("ASC")) {
					queryWrapper.orderByAsc(tableName+"."+ StrUtil.toUnderlineCase(sortObj.get("field").toString()));//把参数转为驼峰
				} else {
					queryWrapper.orderByDesc(tableName+"."+StrUtil.toUnderlineCase(sortObj.get("field").toString()));
				}
			}
		}else{
			queryWrapper.orderByAsc(tableName+".id");
		}
		return queryWrapper;
	}

	/**
	 * 将字节数转换为文件大小
	 * @param bytes 字节数
	 * @return 文件大小
	 */
	public static String convertBytes(long bytes) {
		if(bytes==0){
			return "0 KB";
		}else if(bytes<1024){
			return bytes+" B";
		}else if(bytes<1024*1024){
			return String.format("%.2f", bytes/1024.00) + " KB";
		}else if(bytes<1024*1024*1024){
			return String.format("%.2f", bytes/1024.00/1024.00) + " MB";
		}else{
			return String.format("%.2f", bytes/1024.00/1024.00/1024.00) + " GB";
		}
	}
}
