package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import lombok.Data;

/**
 * @author hy
 * @since 2023-5-9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("configs")
public class ConfigsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private String content;

    private String des;

    private Integer type;
}