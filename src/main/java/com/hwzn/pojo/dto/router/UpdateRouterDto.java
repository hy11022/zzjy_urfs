package com.hwzn.pojo.dto.router;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class UpdateRouterDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotNull(message = "层级不能为空")
	@Min(value=1,message = "层级不能小于1")
	private Integer level;

	@NotNull(message = "父级ID不能为空")
	@Min(value=0,message = "父级ID不能小于0")
	private Integer parId;

	@NotNull(message = "路由类型不能为空")
	@Range(min = 1, max = 2, message = "路由类型只能是1和2")
	private Integer type;

	@NotBlank(message = "标题不能为空")
	@Length(max = 10,message = "标题长度不能超过10位")
	private String title;

	@NotBlank(message = "名称不能为空")
	@Length(max = 255,message = "名称长度不能超过255位")
	private String name;

	@NotBlank(message = "路径不能为空")
	@Length(max = 255,message = "路径长度不能超过255位")
	private String path;

	@NotNull(message = "排序不能为空")
	@Min(value=1,message = "排序不能小于1")
	private Integer seq;
}
