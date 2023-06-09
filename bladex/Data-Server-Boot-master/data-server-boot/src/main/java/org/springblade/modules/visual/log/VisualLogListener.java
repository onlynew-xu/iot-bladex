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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.log.constant.EventConstant;
import org.springblade.modules.visual.entity.VisualLog;
import org.springblade.modules.visual.service.IVisualLogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;


/**
 * 异步监听日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class VisualLogListener {

	private final IVisualLogService logService;

	@Async
	@Order
	@EventListener(VisualLogEvent.class)
	public void saveVisualLog(VisualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		VisualLog log = (VisualLog) source.get(EventConstant.EVENT_LOG);
		logService.save(log);
	}

}
