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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.visual.dto.VisualRecordDTO;
import org.springblade.modules.visual.entity.VisualRecord;
import org.springblade.modules.visual.mapper.VisualRecordMapper;
import org.springblade.modules.visual.service.IVisualRecordService;
import org.springframework.stereotype.Service;

/**
 * 可视化数据集表 服务实现类
 *
 * @author BladeX
 */
@Service
public class VisualRecordServiceImpl extends BaseServiceImpl<VisualRecordMapper, VisualRecord> implements IVisualRecordService {

	@Override
	public IPage<VisualRecordDTO> selectVisualRecordPage(IPage<VisualRecordDTO> page, VisualRecordDTO visualRecord) {
		return page.setRecords(baseMapper.selectVisualRecordPage(page, visualRecord));
	}

}
