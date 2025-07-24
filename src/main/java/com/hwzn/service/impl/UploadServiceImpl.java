package com.hwzn.service.impl;

import com.hwzn.pojo.dto.upload.UploadByBase64Dto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import com.hwzn.service.UploadService;
import com.hwzn.util.CommonUtil;
import com.hwzn.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 14:56
 * @Desc: 上传服务实现类
 */
@Service
public class UploadServiceImpl implements UploadService {

	@Resource
	private UploadChunkServiceImpl uploadChunkServiceImpl;

	@Override
	public String uploadByFile(MultipartFile file) throws IOException {
		//存储文件到本地，返回获取本地路径
		String localFilePath = FileUtil.saveFile(file);
		//转换本地路径为网络路径返回
		String path = FileUtil.localPathToUrl(localFilePath);
		return path;
	}

	@Override
	public String uploadByBase64(UploadByBase64Dto uploadByBase64Dto) {
		//存储文件（Base64）到本地，返回获取本地路径
		String localFilePath = FileUtil.saveFileByBase64(uploadByBase64Dto.getBase64(), uploadByBase64Dto.getType());
		//转换本地路径为网络路径返回
		return CommonUtil.localPathToUrl(localFilePath);
	}

	@Override
	public String uploadByChunk(String module, HttpServletRequest request) {
		final String[] path = {""};
		String fileName;
		if(Objects.equals(module, "files")){
			String type = FilenameUtils.getExtension(request.getParameter("filename"));//通过文件名获取后缀名
			fileName = CommonUtil.randomUUID() + "." + type;
			path[0] = FileUtil.setFilePath(type,null);
		}else {
			String parPath = request.getParameter("path");
			fileName = request.getParameter("filename");
			String relativePath = "";
			if (request.getParameter("relativePath").contains("/")) {//判断是否有上层文件夹
				String rPath = request.getParameter("relativePath");
				int index =rPath.lastIndexOf("/");
				path[0]  = FileUtil.setFilePath(null,parPath + "/" + rPath.substring(0, index)+"/") ;//后面拼接父路径和文件所属文件夹
			}else {
				path[0]  = FileUtil.setFilePath(null,parPath + "/" + relativePath);//后面拼接父路径和文件所属文件夹
			}
		}
		try {
			uploadChunkServiceImpl.setTemporaryFolder(path[0]);//设置文件生成路径
			uploadChunkServiceImpl.setFinalFileName(fileName);//设置文件名
			uploadChunkServiceImpl.post(request, (status, filename, original_filename, identifier, fileType) -> {});//分片生成文件
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FileUtil.localPathToUrl(path[0] + fileName);
	}
}