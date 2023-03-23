/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.visual.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 可视化数据集表 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_visual_record")
@ApiModel(value = "VisualRecord对象", description = "可视化数据集表")
@EqualsAndHashCode(callSuper = true)
public class VisualRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
	/**
	 * 数据地址
	 */
	@ApiModelProperty(value = "数据地址")
	private String url;
	/**
	 * 数据类型
	 */
	@ApiModelProperty(value = "数据类型")
	private Integer dataType;
	/**
	 * 数据方法
	 */
	@ApiModelProperty(value = "数据方法")
	private String dataMethod;
	/**
	 * 数据请求头
	 */
	@ApiModelProperty(value = "数据请求头")
	private String dataHeader;
	/**
	 * 数据集
	 */
	@ApiModelProperty(value = "数据集")
	private String data;
	/**
	 * 数据查询
	 */
	@ApiModelProperty(value = "数据查询")
	private String dataQuery;
	/**
	 * 数据查询类型
	 */
	@ApiModelProperty(value = "数据查询类型")
	private String dataQueryType;
	/**
	 * 数据格式化
	 */
	@ApiModelProperty(value = "数据格式化")
	private String dataFormatter;
	/**
	 * ws地址
	 */
	@ApiModelProperty(value = "ws地址")
	private String wsUrl;
	/**
	 * 创建部门
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建部门")
	private Long createDept;

}
