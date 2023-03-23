package org.springblade.modules.visual.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 大屏数据源DTO
 *
 * @author Chill
 */
@Data
@ApiModel(value = "VisualDbDTO对象", description = "可视化数据源表")
public class VisualDbDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 驱动类
	 */
	@ApiModelProperty(value = "驱动类")
	private String driverClass;

}
