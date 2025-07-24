package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * @author hy
 * @since 2023-5-9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("login_exception_records")
public class LoginExceptionRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String createTime;

    private String releaseTime;

    private String createAccount;
}