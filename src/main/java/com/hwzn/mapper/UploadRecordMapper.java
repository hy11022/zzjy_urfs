package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.hwzn.pojo.entity.UploadRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 13:55
 * @Desc: 上传记录
 */
@Mapper
@Repository
public interface UploadRecordMapper extends BaseMapper<UploadRecordEntity> {

    @Update("UPDATE upload_records SET status=${status} WHERE path=#{filePath}")
    void updateStatusByPath(String filePath,Integer status);

}
