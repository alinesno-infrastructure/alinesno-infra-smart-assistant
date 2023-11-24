package com.alinesno.infra.smart.assistant.entity;

import com.alinesno.infra.common.facade.mapper.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行业角色实体类，用于表示不同行业中的角色信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("industry_role") // MyBatis-Plus 表名注解
public class IndustryRoleEntity extends BaseEntity {

    @TableField("role_name") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 100)
    @ColumnComment("角色名称")
    private String roleName; // 角色名称

    @TableField("industry") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 100)
    @ColumnComment("所属行业")
    private String industry; // 所属行业

    @TableField("responsibilities") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("角色职责描述")
    private String responsibilities; // 角色职责描述

    @TableField("skills") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("所需技能描述")
    private String skills; // 所需技能描述

    @TableField("experience") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("经验要求描述")
    private String experience; // 经验要求描述

    @TableField("role_level") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 50)
    @ColumnComment("角色等级")
    private String roleLevel; // 角色等级

    @TableField("salary_range") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 100)
    @ColumnComment("薪资范围")
    private String salaryRange; // 薪资范围

    @TableField("education") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.VARCHAR, length = 100)
    @ColumnComment("教育背景要求")
    private String education; // 教育背景要求

    @TableField("other_attributes") // MyBatis-Plus 字段注解
    @ColumnType(value = MySqlTypeConstant.TEXT)
    @ColumnComment("其他角色相关字段")
    private String otherAttributes; // 其他角色相关字段
}