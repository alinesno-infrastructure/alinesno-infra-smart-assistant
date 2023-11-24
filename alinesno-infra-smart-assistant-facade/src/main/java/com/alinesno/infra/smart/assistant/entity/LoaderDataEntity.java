package com.alinesno.infra.smart.assistant.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * LoaderData 实体类，用于存储 Loader 模块相关的数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("loader_data") // MyBatis-Plus 表名注解
public class LoaderDataEntity extends InfraBaseEntity {
    
    @TableField("source_type")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("数据来源类型")
    private String sourceType; // 数据来源类型

    @TableField("file_name")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("文件名")
    private String fileName; // 文件名

    @TableField("file_path")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("文件路径")
    private String filePath; // 文件路径

    @TableField("file_size")
    @ColumnComment("文件大小")
    private int fileSize; // 文件大小

    @TableField("uploaded_by")
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("上传者")
    private String uploadedBy; // 上传者

    @TableField("uploaded_at")
    @ColumnType(value = MySqlTypeConstant.DATETIME, length = 50)
    @ColumnComment("上传时间")
    private Date uploadedAt; // 上传时间

}
