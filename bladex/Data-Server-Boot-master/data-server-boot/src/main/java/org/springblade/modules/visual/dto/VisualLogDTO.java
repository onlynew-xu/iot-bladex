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
package org.springblade.modules.visual.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 大屏日志DTO
 *
 * @author BladeX
 */
@Data
@ApiModel(value = "VisualLogDTO对象", description = "可视化日志表")
public class VisualLogDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 日志标题
	 */
	@ApiModelProperty(value = "日志标题")
	private String title;
	/**
	 * 操作方式
	 */
	@ApiModelProperty(value = "操作方式")
	private String method;
	/**
	 * 请求URI
	 */
	@ApiModelProperty(value = "请求URI")
	private String requestUri;
	/**
	 * 方法类
	 */
	@ApiModelProperty(value = "方法类")
	private String methodClass;
	/**
	 * 方法名
	 */
	@ApiModelProperty(value = "方法名")
	private String methodName;
	/**
	 * 执行时间
	 */
	@ApiModelProperty(value = "执行时间")
	private String time;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;


}
