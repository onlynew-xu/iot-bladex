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

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.visual.entity.VisualLog;
import org.springblade.modules.visual.service.IVisualLogService;

/**
 * 大屏日志切面
 *
 * @author Chill
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class VisualLogAspect {

	/**
	 * 日志服务类
	 */
	private final IVisualLogService logService;
	/**
	 * 每天限流次数
	 */
	private final static long MAX_COUNT = 500L;
	/**
	 * 限流提醒文本
	 */
	private final static String LIMIT_MESSAGE = "访问过于频繁,请稍后再试";

	@Around("@annotation(dataLog)")
	public Object around(ProceedingJoinPoint point, DataLog dataLog) throws Throwable {
		//获取类名
		String className = point.getTarget().getClass().getName();
		//获取方法
		String methodName = point.getSignature().getName();
		//设置限流次数
		String remoteIp = WebUtil.getIP();
		long count = logService.count(
			Wrappers.<VisualLog>lambdaQuery()
				.eq(VisualLog::getRemoteIp, remoteIp)
				.gt(VisualLog::getCreateTime, DateUtil.minusDays(DateUtil.now(), 1L))
		);
		if (count > MAX_COUNT) {
			VisualLogPublisher.publishEvent(methodName, className, LIMIT_MESSAGE, 0L);
			throw new ServiceException(LIMIT_MESSAGE);
		}
		//发送异步日志事件
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//记录日志
		VisualLogPublisher.publishEvent(methodName, className, dataLog.value(), time);
		return result;
	}

}
