package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 区域表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-29 11:08:06
 */
@Data
@TableName("region")
public class RegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@TableId
	private Long id;
	/**
	 * 区域名称
	 */
	private String name;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
