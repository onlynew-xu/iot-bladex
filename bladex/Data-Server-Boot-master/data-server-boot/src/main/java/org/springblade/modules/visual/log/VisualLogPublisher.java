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

import org.springblade.core.log.constant.EventConstant;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.UrlUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.visual.entity.VisualLog;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 大屏日志信息事件发送
 *
 * @author Chill
 */
public class VisualLogPublisher {

	public static void publishEvent(String methodName, String methodClass, String title, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		VisualLog log = new VisualLog();
		log.setTitle(title);
		log.setTime(String.valueOf(time));
		log.setMethodClass(methodClass);
		log.setMethodName(methodName);
		log.setRemoteIp(WebUtil.getIP(request));
		log.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		log.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		log.setMethod(request.getMethod());
		log.setParams(WebUtil.getRequestContent(request));
		log.setCreateTime(DateUtil.now());
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, log);
		SpringUtil.publishEvent(new VisualLogEvent(event));
	}

}
