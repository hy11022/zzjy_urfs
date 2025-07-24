package com.hwzn.pojo.dto.banner;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建横幅
 */
@Data
public class UpdateBannerDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;

	@NotBlank(message = "图片路径不能为空")
	private String imgPath;

	private String url;

	@NotNull(message = "排序不能为空")
	private Integer seq;

	private String des;
}