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

/**
 * 数据源常量.
 *
 * @author Chill
 */
public interface DynamicDataSourceConstant {

	/**
	 * 数据源缓存名
	 */
	String DYNAMIC_DATASOURCE_CACHE = "blade:visual";

	/**
	 * 数据源缓存键
	 */
	String DYNAMIC_DATASOURCE_KEY = "datasource:id:";

	/**
	 * 数据源查询基础
	 */
	String DYNAMIC_DATASOURCE_BASE_STATEMENT = "SELECT id, driver_class as driverClass, url, username, password FROM blade_visual_db";

	/**
	 * 数据源查询SQL
	 */
	String DYNAMIC_DATASOURCE_SINGLE_STATEMENT = DYNAMIC_DATASOURCE_BASE_STATEMENT + " WHERE is_deleted = 0 AND id = ?";

	/**
	 * 数据源查询SQL
	 */
	String DYNAMIC_DATASOURCE_GROUP_STATEMENT = DYNAMIC_DATASOURCE_BASE_STATEMENT + " WHERE is_deleted = 0";

	/**
	 * 数据源错误提示
	 */
	String DYNAMIC_DATASOURCE_NOT_FOUND = "数据源信息有误，数据加载失败";

	/**
	 * oracle驱动类
	 */
	String ORACLE_DRIVER_CLASS = "oracle.jdbc.OracleDriver";

	/**
	 * oracle校验
	 */
	String ORACLE_VALIDATE_STATEMENT = "select 1 from dual";

	/**
	 * 通用校验
	 */
	String COMMON_VALIDATE_STATEMENT = "select 1";

}
