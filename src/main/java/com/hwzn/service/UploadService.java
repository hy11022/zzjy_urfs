package com.hwzn.service;

import com.hwzn.pojo.dto.upload.UploadByBase64Dto;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 14:48
 * @Desc: 上传服务接口
 */
public interface UploadService {

	String uploadByFile(MultipartFile file) throws IOException;

	String uploadByBase64(UploadByBase64Dto uploadByBase64Dto);

	String uploadByChunk(String module, HttpServletRequest request);
}