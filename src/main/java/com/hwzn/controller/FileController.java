package com.hwzn.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.common.*;
import com.hwzn.pojo.vo.FileVo;
import com.hwzn.service.LogService;
import com.hwzn.util.CommonUtil;
import com.hwzn.util.FileUtil;
import com.hwzn.util.JWTUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @Author：Rongjun Du
 * @Date：2024/3/14 16:46
 * @Desc：文件
 */
@Component
@RestController
@RequestMapping("/file")
public class FileController {
	
	private static Environment env;
	
	@Resource
	public void set(Environment env) {
		FileController.env = env;
	}
	
	@Resource
	LogService logService;
	
	//筛选文件列表
	@PostMapping("/filterFileList")
	public Result filterFileList(@Validated @RequestBody PathDto pathDto)  {
		JSONObject data = new JSONObject();
		List<FileVo> resultList = new ArrayList<>();
		//获取指定的文件目录
		File directory = new File((Objects.equals(CommonUtil.getOperationSystemType(), "linux")?"/usr/local":"D:")+"/source/zzjy_urfs/"+pathDto.getPath());
		File[] files = directory.listFiles();
		if (files != null) {
			for(File file : files){
				FileVo fileVo = new FileVo();
				fileVo.setName(file.getName());
				fileVo.setUrl(FileUtil.localPathToUrl(file.getPath()));
				fileVo.setSize(CommonUtil.convertBytes(file.length()));
				fileVo.setLastModifiedTime(file.lastModified());
				if(file.isDirectory()){
					fileVo.setType("文件夹");
					resultList.add(0,fileVo);
				}else {
					fileVo.setType(file.getPath().substring(file.getPath().lastIndexOf(".")+1));
					resultList.add(fileVo);
				}
			}
		}
		Collections.reverse(resultList);
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}
	
	//创建文件夹
	@PostMapping("/createFolder")
	public Result createFolder(@Validated @RequestBody CreateFolderDto createFolderDto, HttpServletRequest request)  {
		File directory = new File((Objects.equals(CommonUtil.getOperationSystemType(), "linux")?"/usr/local":"D:")+"/source/zzjy_urfs/"+createFolderDto.getPath()+"/"+createFolderDto.getName());
		if(directory.exists()){
			return Result.showInfo(1,"目录已存在", null);
		}
		if(!directory.mkdir()){
			return Result.showInfo(1,"创建文件夹失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"创建文件夹，路径为："+env.getProperty("server.Server")+"/"+createFolderDto.getPath()+"/"+createFolderDto.getName());
		return Result.showInfo(0,"Success", null);
	}
	
	//更新文件
	@PostMapping("/updateFile")
	public Result updateFile(@Validated @RequestBody UpdateFileDto updateFileDto, HttpServletRequest request)  {
		Path oldFile = Paths.get(Objects.requireNonNull(CommonUtil.urlToLocalPath(updateFileDto.getUrl())));
		int index =  updateFileDto.getUrl().lastIndexOf("/");
		Path newFile = Paths.get(Objects.requireNonNull(CommonUtil.urlToLocalPath(index < 0 ? null : updateFileDto.getUrl().substring(0, index + 1) + updateFileDto.getName())));
		File file = new File(String.valueOf(newFile));
		if(file.exists()){
			return Result.showInfo(1,"文件或目录已存在", null);
		}
		try {
			Files.move(oldFile, newFile, StandardCopyOption.REPLACE_EXISTING);
			//记录日志
			logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),updateFileDto.getUrl()+"重命名为"+(index<0?null:updateFileDto.getUrl().substring(0,index+1)+updateFileDto.getName()));
			return Result.showInfo(0,"Success", null);
		} catch (IOException e) {
			return Result.showInfo(0,"文件重命名失败", null);
		}
	}
	
	//删除文件
	@PostMapping("/deleteFile")
	public Result deleteFile(@Validated @RequestBody UrlDto urlDto, HttpServletRequest request)  {
		String path = CommonUtil.urlToLocalPath(urlDto.getUrl());
		if (path==null) {
			return Result.showInfo(1,"文件地址有误", null);
		}
		File file = new File(Objects.requireNonNull(CommonUtil.urlToLocalPath(urlDto.getUrl())));
		if(!file.exists()){
			return Result.showInfo(2,"文件不存在或已删除", null);
		}
		//如果是文件夹，则递归删除
		if(file.isDirectory()){
			FileUtil.deleteDirectory(CommonUtil.urlToLocalPath(urlDto.getUrl()));
			logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"删除文件夹，文件夹路径为："+urlDto.getUrl());
			return Result.showInfo(0,"Success", null);
		}
		if(!file.delete()){
			return Result.showInfo(2,"删除失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"删除文件，文件路径为："+urlDto.getUrl());
		return Result.showInfo(0,"Success", null);
	}
	
	//批量删除文件
	@PostMapping("/batchDeleteFile")
	public Result batchDeleteFile(@Validated @RequestBody UrlsDto urlsDto, HttpServletRequest request)  {
		JSONArray urlList = JSONUtil.parseArray(urlsDto.getUrls());
		for (Object o : urlList) {
			String url = CommonUtil.urlToLocalPath(o.toString());
			if (url == null) {
				break;
			}
			File file = new File(url);
			if (!file.exists()) {
				break;
			}
			//如果是文件夹，则递归删除
			if (file.isDirectory()) {
				FileUtil.deleteDirectory(url);
			}
			file.delete();
		}
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"删除文件（批量），文件路径为："+urlsDto.getUrls());
		return Result.showInfo(0,"Success", null);
	}
}
