package org.springblade.modules.visual.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.modules.visual.entity.Visual;
import org.springblade.modules.visual.entity.VisualConfig;

import java.io.Serializable;

/**
 * 大屏展示DTO
 *
 * @author Chill
 */
@Data
@ApiModel(value = "VisualDTO对象", description = "可视化表")
public class VisualDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 可视化主信息
	 */
	private Visual visual;

	/**
	 * 可视化配置信息
	 */
	private VisualConfig config;

}
