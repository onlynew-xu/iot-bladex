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
package org.springblade.modules.visual.log;

import org.springblade.modules.visual.service.IVisualLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 大屏日志配置
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
public class VisualLogConfiguration {

	@Bean
	public VisualLogAspect visualLogAspect(IVisualLogService logService) {
		return new VisualLogAspect(logService);
	}

	@Bean
	public VisualLogListener visualLogListener(IVisualLogService logService) {
		return new VisualLogListener(logService);
	}

}
