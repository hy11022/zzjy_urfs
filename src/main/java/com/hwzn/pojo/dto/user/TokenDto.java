package com.hwzn.pojo.dto.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/30 9:11
 * @Desc: 令牌
 */
@Data
public class TokenDto {
	
	private String token;
}
