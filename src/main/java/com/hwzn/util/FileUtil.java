package com.hwzn.util;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Objects;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

/**
 * @Author： Du Rongjun
 * @Date： 2023/8/25 16:11
 * @Desc： 文件工具类
 */
@Component
public class FileUtil {

	private static Environment env;

	@Resource
	public void set(Environment env) {
		FileUtil.env = env;
	}
	/**
	 * 存储文件到本地
	 * @param file 文件流
	 * @return 文件地址
	 */
	public static String saveFile(MultipartFile file) throws IOException {
		String type=getFileTypeByUrl(Objects.requireNonNull(file.getOriginalFilename()));
		String filePath = setFilePath(type,null) + CommonUtil.randomUUID() + "." + type;
		file.transferTo(new File(filePath));
		return filePath;
	}

	//存储文件（Base64）
	public static String saveFileByBase64(String base64, String type) {
		String[] splitstr = base64.split(",");
		String s;
		if (base64.contains("base64,")) {
			s = splitstr[1];
		} else {
			s = splitstr[0];
		}
		//定义文件夹路径
		StringBuilder dirPath = new StringBuilder();
		dirPath.append(System.getProperty("catalina.home"), 0, System.getProperty("catalina.home").indexOf("\\"))
				.append("/source/zzjy_urfs/files/").append(type);
		//创建文件夹
		File dir = new File(String.valueOf(dirPath));
		//判断是否存在，不存在创建
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//定义文件路径
		StringBuilder filePath = new StringBuilder();
		filePath.append(dirPath).append("/").append(CommonUtil.randomUUID()).append(".").append(type);
		//将String 转成byte数组
		byte[] file = new Base64().decode(s);
		//写入本地
		File files = new File(filePath.toString());
		try (FileOutputStream writefile = new FileOutputStream(files)) {
			writefile.write(file, 0, file.length);
			writefile.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return filePath.toString();
	}

	/**
	 * 获取文件类型(网络路径）
	 * @param url 网络路径
	 * @return 文件类型
	 */
	public static String getFileTypeByUrl(String url){
		int index = url.lastIndexOf(".");
		return index<0?null:url.substring(index+1);
	}

	/**
	 * 设置文件路径
	 * @param type 文件类型
	 * @param parPath 父路径
	 * @return 文件路径
	 */
	public static String setFilePath(String type,String parPath){
		//定义文件夹路径
		StringBuilder dirPath = new StringBuilder();
		if(type!=null){
			dirPath.append(System.getProperty("catalina.home"), 0,
							Objects.equals(CommonUtil.getOperationSystemType(), "linux")?System.getProperty("catalina.home").lastIndexOf("/"):System.getProperty("catalina.home").indexOf("\\"))
					.append("/source/zzjy_urfs").append("/files/").append(type).append("/");
		}else {
			dirPath.append(System.getProperty("catalina.home"), 0,
							Objects.equals(CommonUtil.getOperationSystemType(), "linux")?System.getProperty("catalina.home").lastIndexOf("/"):System.getProperty("catalina.home").indexOf("\\"))
					.append("/source/zzjy_urfs/").append(parPath);
		}
		//创建文件夹
		File dir = new File(String.valueOf(dirPath));
		//判断是否存在，不存在创建
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dirPath.toString();
	}

	/**
	 * 本地路径转化为网络路径
	 * @param localPath 本地路径
	 * @return 网络路径
	 */
	public static String localPathToUrl(String localPath){
		String path=localPath.replace("\\","/");
		int index =path.indexOf("source")+6;
		return index<0?null:env.getProperty("server.Server")+path.substring(index);
	}

	/**
	 * 递归逐层删除文件夹信息
	 * @param directoryPath 文件夹路径
	 * @return 无
	 */
	public static void deleteDirectory(String directoryPath){
		File directory = new File(directoryPath);
		File[] list = directory.listFiles();
		if (list != null) {
			for (File temp : list) {
				deleteDirectory(temp.getAbsolutePath());
			}
		}
		directory.delete();
	}
}