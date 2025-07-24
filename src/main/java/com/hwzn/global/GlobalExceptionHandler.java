package com.hwzn.global;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.validation.FieldError;
import com.hwzn.pojo.Result;
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2025/7/17 10:12
 * @Desc: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * 参数校验异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
		FieldError error = e.getBindingResult().getFieldError();
		return Result.showInfo(400, error==null?"参数校验异常":error.getDefaultMessage(),null);
	}
	
	/**
	 * 数据重复异常
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Result sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
		return Result.showInfo(1, e.getMessage().substring(e.getMessage().indexOf("key")+5,e.getMessage().length()-1)+"重复",null);
	}

	/**
	 * http读取异常
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
		if(Objects.requireNonNull(e.getMessage()).contains("InvalidFormatException")){
			return Result.showInfo(400, "入参格式有误",null);
		}
		if(Objects.requireNonNull(e.getMessage()).contains("JsonParseException")){
			return Result.showInfo(400, "JSON解析有误",null);
		}
		return Result.showInfo(400, e.getMessage(),null);
	}
}