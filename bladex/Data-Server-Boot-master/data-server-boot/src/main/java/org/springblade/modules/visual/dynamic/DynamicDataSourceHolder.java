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
package org.springblade.modules.visual.dynamic;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import lombok.Setter;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Set;

import static org.springblade.modules.visual.dynamic.DynamicDataSourceConstant.*;

/**
 * 动态数据源核心处理类
 *
 * @author Chill
 */
@Setter
public class DynamicDataSourceHolder {

	private DataSource dataSource;
	private DataSourceCreator dataSourceCreator;
	private JdbcTemplate jdbcTemplate;


	/**
	 * 数据源缓存处理
	 *
	 * @param id 数据源ID
	 */
	public void handleDataSource(String id) {
		// 获取储存的数据源集合
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		Set<String> keys = ds.getCurrentDataSources().keySet();
		// 配置不存在则动态添加数据源，以懒加载的模式解决分布式场景的配置同步
		// 为了保证数据完整性，配置后生成数据源缓存，后台便无法修改更换数据源，若一定要修改请迁移数据后重启服务或自行修改底层逻辑
		if (!keys.contains(id)) {
			DynamicDataSource dynamicDataSource = getDataSource(id);
			if (dynamicDataSource != null) {
				// 创建数据源配置
				DataSourceProperty dataSourceProperty = new DataSourceProperty();
				// 拷贝数据源配置
				BeanUtils.copyProperties(dynamicDataSource, dataSourceProperty);
				// 设置SQL校验
				DruidConfig druid = dataSourceProperty.getDruid();
				if (StringUtil.equals(dataSourceProperty.getDriverClassName(), ORACLE_DRIVER_CLASS)) {
					druid.setValidationQuery(ORACLE_VALIDATE_STATEMENT);
				} else {
					druid.setValidationQuery(COMMON_VALIDATE_STATEMENT);
				}
				// 创建动态数据源
				DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
				// 添加最新数据源
				ds.addDataSource(id, dataSource);
			} else {
				throw new ServiceException(DYNAMIC_DATASOURCE_NOT_FOUND);
			}
		}
	}

	/**
	 * 获取对应的数据源配置
	 *
	 * @param id 数据源ID
	 */
	private DynamicDataSource getDataSource(String id) {
		// 获取数据源信息
		try {
			DynamicDataSource dynamicDataSource = CacheUtil.get(DYNAMIC_DATASOURCE_CACHE, DYNAMIC_DATASOURCE_KEY, id, DynamicDataSource.class);
			if (dynamicDataSource == null) {
				dynamicDataSource = jdbcTemplate.queryForObject(DYNAMIC_DATASOURCE_SINGLE_STATEMENT, new String[]{id}, new BeanPropertyRowMapper<>(DynamicDataSource.class));
				if (dynamicDataSource != null && StringUtil.isNoneBlank(dynamicDataSource.getId(), dynamicDataSource.getDriverClass(), dynamicDataSource.getUrl(), dynamicDataSource.getUsername(), dynamicDataSource.getPassword())) {
					CacheUtil.put(DYNAMIC_DATASOURCE_CACHE, DYNAMIC_DATASOURCE_KEY, id, dynamicDataSource);
				}
			}
			return dynamicDataSource;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
