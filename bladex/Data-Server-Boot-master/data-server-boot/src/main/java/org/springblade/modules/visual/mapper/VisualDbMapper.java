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
package org.springblade.modules.visual.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.modules.visual.dto.VisualDbDTO;
import org.springblade.modules.visual.entity.VisualDb;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 可视化数据源配置表 Mapper 接口
 *
 * @author Chill
 */
public interface VisualDbMapper extends BaseMapper<VisualDb> {

	/**
	 * 数据源列表
	 *
	 * @return List
	 */
	List<VisualDbDTO> dbList();

	/**
	 * 自定义调用动态sql
	 *
	 * @param sql 可执行sql语句
	 * @return List
	 */
	List<LinkedHashMap<String, Object>> dynamicQuery(String sql);

}
