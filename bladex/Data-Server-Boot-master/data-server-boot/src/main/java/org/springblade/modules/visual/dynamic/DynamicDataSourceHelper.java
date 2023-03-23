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

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 *
 * @author Chill
 */
public class DynamicDataSourceHelper {

	/**
	 * 测试数据库链接
	 */
	public Boolean dbTest(String driverClass, String url, String username, String password) {
		Connection conn = null;
		try {
			//测试驱动类
			Class.forName(driverClass);
			//创建连接
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(Boolean.FALSE);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			//关闭连接
			dbClose(conn);
		}
	}

	/**
	 * 关闭数据库链接
	 */
	private void dbClose(Connection conn) {
		try {
			//关闭数据源连接
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
