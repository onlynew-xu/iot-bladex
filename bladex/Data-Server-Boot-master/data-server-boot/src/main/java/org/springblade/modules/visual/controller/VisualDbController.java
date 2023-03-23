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
import io.protostuff.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.api.crypto.annotation.decrypt.ApiDecryptAes;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.dto.VisualDbDTO;
import org.springblade.modules.visual.dynamic.DynamicModel;
import org.springblade.modules.visual.entity.VisualDb;
import org.springblade.modules.visual.service.IVisualDbService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据源表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/db")
@Api(value = "可视化数据源配置表", tags = "可视化数据源配置接口")
public class VisualDbController {

	private final IVisualDbService visualDbService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入datasource")
	public R<VisualDb> detail(VisualDb db) {
		VisualDb detail = visualDbService.getOne(Condition.getQueryWrapper(db));
		return R.data(detail);
	}

	/**
	 * 分页 数据源配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入datasource")
	public R<IPage<VisualDb>> list(VisualDb db, Query query) {
		IPage<VisualDb> pages = visualDbService.page(Condition.getPage(query), Condition.getQueryWrapper(db));
		return R.data(pages);
	}

	/**
	 * 新增 数据源配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入datasource")
	public R save(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.save(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}

	/**
	 * 修改 数据源配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入datasource")
	public R update(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.updateById(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}

	/**
	 * 新增或修改 数据源配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入datasource")
	public R submit(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.saveOrUpdate(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}


	/**
	 * 删除 数据源配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualDbService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 数据源测试连接
	 */
	@ApiDecryptAes
	@PostMapping("/db-test")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "数据源测试连接", notes = "数据源测试连接")
	public R<Boolean> dbTest(@RequestBody VisualDb db) {
		return R.status(visualDbService.dbTest(db));
	}

	/**
	 * 数据源列表
	 */
	@GetMapping("/db-list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉数据源", notes = "数据源列表")
	public R<List<VisualDbDTO>> dbList() {
		return R.data(visualDbService.dbList());
	}

	/**
	 * 动态执行SQL
	 */
	@ApiDecryptAes
	@PostMapping("dynamic-query")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "动态执行SQL", notes = "动态执行SQL")
	public R dynamicQuery(@RequestBody DynamicModel model) {
		List<LinkedHashMap<String, Object>> linkedHashMaps = visualDbService.dynamicQuery(model.getId(), model.getSql());
		return R.data(linkedHashMaps);
	}

}
