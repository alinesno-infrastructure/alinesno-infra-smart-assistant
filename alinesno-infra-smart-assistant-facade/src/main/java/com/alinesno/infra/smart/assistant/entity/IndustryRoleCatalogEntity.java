package com.alinesno.infra.smart.assistant.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 行业角色分类类型
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("industry_role_catalog") // MyBatis-Plus 表名注解
@TableComment("行业角色分类类型")
public class IndustryRoleCatalogEntity extends InfraBaseEntity {

    @Excel(name="类型图标")
    @TableField("icon")
    @ColumnType(length=64)
    @ColumnComment("类型图标")
    private String icon;

    @Excel(name="类型名称")
    @TableField("name")
    @ColumnType(length=50)
    @ColumnComment("类型名称")
    private String name ;

    @Excel(name="显示排序")
    @TableField("order_num")
    @ColumnType(length=5 , value = MySqlTypeConstant.INT)
    @ColumnComment("显示排序")
    private int orderNum ;

    @Excel(name="类型描述")
    @TableField("description")
    @ColumnType(length=128)
    @ColumnComment("类型描述")
    private String description;

    @Excel(name="生成式数量")
    @TableField("prompt_count")
    @ColumnType(length=5)
    @ColumnComment("生成式数量")
    private int promptCount;

    @Excel(name="祖级列表")
    @TableField("ancestors")
    @ColumnType(length=256)
    @ColumnComment("祖级列表")
    private String ancestors;

    @Excel(name="父类ID")
    @TableField("parent_id")
    @ColumnType(length=32)
    @ColumnComment("父类ID")
    private Long parentId;

    /** 子类型 */
    @TableField(exist = false)
    private List<IndustryRoleCatalogEntity> children = new ArrayList<>();

    /** 包含的子Agent角色 **/
    @TableField(exist = false)
    private List<IndustryRoleEntity> agents = new ArrayList<>();
}