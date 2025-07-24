package com.hwzn.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.hwzn.service.LoginExceptionRecordService;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.service.ConfigService;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.util.JWTUtil;
import com.hwzn.pojo.Result;

@RestController
@RequestMapping("/loginExceptionRecord")
public class LoginExceptionRecordController {

    @Resource
    private LoginExceptionRecordService loginExceptionRecordService;

    @Resource
    private ConfigService configService;

    //创建登录异常记录
    @PostMapping("/createLoginExceptionRecord")
    public Result createLoginExceptionRecord(HttpServletRequest request) {
        String loginFailAccountLockDurationStr =configService.getConfigContentByCode("login-fail-account-lock-duration");
        if(loginFailAccountLockDurationStr==null){
            return Result.showInfo(2,"获取login-fail-account-lock-duration配置失败",null);
        }
        Integer loginFailAccountLockDuration = Integer.valueOf(loginFailAccountLockDurationStr);

        String account = JWTUtil.getAccount(request.getHeader("token"));

        Integer id =loginExceptionRecordService.createLoginExceptionRecord(account, loginFailAccountLockDuration);
        JSONObject data =new JSONObject();
        data.set("id",id);
        return Result.showInfo(0,"Success",data);
    }
}