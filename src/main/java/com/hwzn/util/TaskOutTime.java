package com.hwzn.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.hwzn.pojo.entity.UploadRecordEntity;
import com.hwzn.service.UploadRecordService;
import cn.hutool.core.io.FileUtil;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2023/9/14  15:06
 * @Desc:
 */
@Component
@Async
public class TaskOutTime {

    @Resource
    UploadRecordService uploadRecordService;

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
    public void syncUploadRecordSchedule() {
        List<UploadRecordEntity> resultList = uploadRecordService.getUploadRecordByStatus(0);
        for(UploadRecordEntity entity:resultList){
            String filePath = CommonUtil.urlToLocalPath(entity.getPath());
            boolean b = FileUtil.del(filePath);
            if (b){
                uploadRecordService.deleteUploadRecord(entity);
            }
        }
    }
}
