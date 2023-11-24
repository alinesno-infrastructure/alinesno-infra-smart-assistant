package com.alinesno.infra.smart.assistant.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.smart.assistant.entity.LoaderDataEntity;
import com.alinesno.infra.smart.assistant.mapper.LoaderDataMapper;
import com.alinesno.infra.smart.assistant.service.ILoadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 应用构建Service业务层处理
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
@Slf4j
@Service
public class LoaderDataServiceImpl extends IBaseServiceImpl<LoaderDataEntity, LoaderDataMapper> implements ILoadDataService {

}