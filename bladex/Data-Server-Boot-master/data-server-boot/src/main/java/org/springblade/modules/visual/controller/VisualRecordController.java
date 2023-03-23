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
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.dto.VisualRecordDTO;
import org.springblade.modules.visual.entity.VisualRecord;
import org.springblade.modules.visual.service.IVisualRecordService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 可视化数据集表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/record")
@Api(value = "可视化数据集表", tags = "可视化数据集接口")
public class VisualRecordController extends BladeController {

	private final IVisualRecordService visualRecordService;

	/**
	 * 可视化数据集表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入visualRecord")
	public R<VisualRecord> detail(VisualRecord visualRecord) {
		VisualRecord detail = visualRecordService.getOne(Condition.getQueryWrapper(visualRecord));
		return R.data(detail);
	}

	/**
	 * 可视化数据集表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入visualRecord")
	public R<IPage<VisualRecordDTO>> list(VisualRecordDTO record, Query query) {
		IPage<VisualRecordDTO> page = Condition.getPage(query);
		IPage<VisualRecordDTO> pages = visualRecordService.selectVisualRecordPage(page, record);
		return R.data(pages);
	}


	/**
	 * 可视化数据集表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入visualRecord")
	public R save(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.save(visualRecord));
	}

	/**
	 * 可视化数据集表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入visualRecord")
	public R update(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.updateById(visualRecord));
	}

	/**
	 * 可视化数据集表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入visualRecord")
	public R submit(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.saveOrUpdate(visualRecord));
	}

	/**
	 * 可视化数据集表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualRecordService.deleteLogic(Func.toLongList(ids)));
	}


}
