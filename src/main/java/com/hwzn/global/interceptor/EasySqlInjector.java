package com.hwzn.global.interceptor;

import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import java.util.List;

//批量插入
public class EasySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        //防止父类方法不可用
        List<AbstractMethod> methodList=super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn());//添加批量插入方法
        return methodList;
    }
}