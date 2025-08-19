package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

/**
 * @Author: hy
 * @CreateTime: 2025-8-6 10:35
 * @Description: 终端管理
 */
@Data
@TableName("client")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientEntity implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String ip;

    private String os;

    private String processor;

    private String codeVersion;

    private String lastOntime;

    private Integer fireWallStatus;

    private Integer outbreakPreventionStatus;

    private Integer status;
}