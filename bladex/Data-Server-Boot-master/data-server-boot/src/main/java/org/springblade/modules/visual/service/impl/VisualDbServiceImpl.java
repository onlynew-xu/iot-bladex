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
package org.springblade.modules.visual.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.PlaceholderUtil;
import org.springblade.modules.visual.dto.VisualDbDTO;
import org.springblade.modules.visual.dynamic.DynamicDataSourceHelper;
import org.springblade.modules.visual.dynamic.DynamicDataSourceHolder;
import org.springblade.modules.visual.entity.VisualDb;
import org.springblade.modules.visual.mapper.VisualDbMapper;
import org.springblade.modules.visual.service.IVisualDbService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 可视化数据源配置表 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class VisualDbServiceImpl extends BaseServiceImpl<VisualDbMapper, VisualDb> implements IVisualDbService {

	private final VisualDbMapper mapper;
	private final DynamicDataSourceHolder holder;
	private final DynamicDataSourceHelper helper;

	@Override
	public Boolean dbTest(VisualDb db) {
		return helper.dbTest(db.getDriverClass(), db.getUrl(), db.getUsername(), db.getPassword());
	}

	@Override
	public List<VisualDbDTO> dbList() {
		return mapper.dbList();
	}

	@Override
	public List<LinkedHashMap<String, Object>> dynamicQuery(String id, String sql) {
		try {
			//处理自定义数据源
			holder.handleDataSource(id);
			//切换数据源
			DynamicDataSourceContextHolder.push(id);
			//获取user信息
			BladeUser user = AuthUtil.getUser();
			//获取user参数
			Map<String, Object> map = (Func.isEmpty(user)) ? Kv.newMap() : BeanUtil.toMap(user);
			//替换user占位符
			String dynamicSql = PlaceholderUtil.getDefaultResolver().resolveByMap(sql, map);
			//执行自定义sql
			return mapper.dynamicQuery(dynamicSql);
		} catch (Exception exception) {
			throw new ServiceException(exception.getMessage());
		} finally {
			//切回主数据源
			DynamicDataSourceContextHolder.poll();
		}

	}

}
