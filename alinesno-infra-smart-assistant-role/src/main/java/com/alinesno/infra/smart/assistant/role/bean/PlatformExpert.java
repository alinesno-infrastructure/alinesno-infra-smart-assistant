package com.alinesno.infra.smart.assistant.role.bean;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 创建父类 ITExpert 并声明为抽象类
@Data
public abstract class PlatformExpert implements BaseExpert {

    private String platformUrl ;

    private int defaultThreadPool = 200 ;

    private static ExecutorService executor = null ;

    protected ExecutorService getExecutor(){
        if(executor == null){
            // 生成一个固定线程池
            executor = Executors.newFixedThreadPool(defaultThreadPool);
        }
        return executor ;
    }

    // 添加一个公共的方法
    public void displayExpertInformation() {
        System.out.println("This is an IT expert.");
    }

}
