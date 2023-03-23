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

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 动态数据源配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class, DynamicDataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, DynamicDataSourceProperties.class})
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class})
public class DynamicDataSourceConfiguration {

	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider(DataSourceProperties dataSourceProperties, DynamicDataSourceProperties dynamicDataSourceProperties, DynamicDataSourceHelper dynamicDataSourceHelper) {
		String driverClassName = dataSourceProperties.getDriverClassName();
		String url = dataSourceProperties.getUrl();
		String username = dataSourceProperties.getUsername();
		String password = dataSourceProperties.getPassword();
		DataSourceProperty master = dynamicDataSourceProperties.getDatasource().get(dynamicDataSourceProperties.getPrimary());
		if (master != null) {
			driverClassName = master.getDriverClassName();
			url = master.getUrl();
			username = master.getUsername();
			password = master.getPassword();
		}
		return new DynamicDataSourceJdbcProvider(dynamicDataSourceHelper, dynamicDataSourceProperties, driverClassName, url, username, password);
	}

	@Bean
	public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
		dataSource.setPrimary(dynamicDataSourceProperties.getPrimary());
		dataSource.setStrict(dynamicDataSourceProperties.getStrict());
		dataSource.setStrategy(dynamicDataSourceProperties.getStrategy());
		dataSource.setProvider(dynamicDataSourceProvider);
		dataSource.setP6spy(dynamicDataSourceProperties.getP6spy());
		dataSource.setSeata(dynamicDataSourceProperties.getSeata());
		return dataSource;
	}

	@Bean
	public DynamicDataSourceHolder dataSourceHolder() {
		return new DynamicDataSourceHolder();
	}

	@Bean
	public DynamicDataSourceHelper dataSourceHelper() {
		return new DynamicDataSourceHelper();
	}

	@Order
	@AllArgsConstructor
	@Configuration(proxyBeanMethods = false)
	public static class DataSourceHolderConfiguration implements SmartInitializingSingleton {

		private final DataSource dataSource;
		private final DruidDataSourceCreator dataSourceCreator;
		private final JdbcTemplate jdbcTemplate;
		private final DynamicDataSourceHolder dynamicDataSourceHolder;

		@Override
		public void afterSingletonsInstantiated() {
			dynamicDataSourceHolder.setDataSource(dataSource);
			dynamicDataSourceHolder.setDataSourceCreator(dataSourceCreator);
			dynamicDataSourceHolder.setJdbcTemplate(jdbcTemplate);
		}

	}


}
