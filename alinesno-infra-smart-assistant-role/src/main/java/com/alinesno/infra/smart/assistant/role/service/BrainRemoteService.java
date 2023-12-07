package com.alinesno.infra.smart.assistant.role.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 调用远程服务
 */
@Slf4j
@Service
public class BrainRemoteService {

    @Retryable(retryFor = {Exception.class} , maxAttempts = 5 , backoff = @Backoff(delay = 5000L , multiplier = 2))
    public void commitTask(Map<String , Object> params , long businessId , String promptId){

        System.out.println(LocalDateTime.now() + ": do something ... " + new Gson().toJson(params));
        throw new RuntimeException(LocalDateTime.now() + ":运行调用异常.") ;
    }

    @Recover
    public void recover(Exception e){
        System.out.println(e.getMessage());
    }

    @Retryable(retryFor = {Exception.class} , maxAttempts = 5 , backoff = @Backoff(delay = 5000L , multiplier = 2))
    public String chatContent(String businessId) {

        return "this is a test of content" ;
    }
}
