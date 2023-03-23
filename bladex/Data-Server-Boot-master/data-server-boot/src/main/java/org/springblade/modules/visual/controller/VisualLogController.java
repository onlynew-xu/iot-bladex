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
package org.springblade.modules.visual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.modules.visual.dto.VisualLogDTO;
import org.springblade.modules.visual.entity.VisualLog;
import org.springblade.modules.visual.service.IVisualLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/log")
@Api(value = "可视化日志表", tags = "可视化日志接口")
public class VisualLogController {

	private final IVisualLogService visualLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入id")
	public R<VisualLogDTO> detail(Long id) {
		VisualLogDTO detail = visualLogService.detail(id);
		return R.data(detail);
	}

	/**
	 * 分页 日志表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入log")
	public R<IPage<VisualLogDTO>> list(VisualLogDTO log, Query query) {
		IPage<VisualLogDTO> pages = visualLogService.selectVisualLogPage(Condition.getPage(query), log);
		return R.data(pages);
	}

	/**
	 * 详情-全字段
	 */
	@GetMapping("/detail-all")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情-全字段", notes = "传入id")
	public R<VisualLog> detailAll(Long id) {
		VisualLog detail = visualLogService.getById(id);
		return R.data(detail);
	}

	/**
	 * 分页-全字段 日志表
	 */
	@GetMapping("/list-all")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页-全字段", notes = "传入log")
	public R<IPage<VisualLog>> listAll(VisualLog log, Query query) {
		IPage<VisualLog> pages = visualLogService.page(Condition.getPage(query), Condition.getQueryWrapper(log));
		return R.data(pages);
	}


}
