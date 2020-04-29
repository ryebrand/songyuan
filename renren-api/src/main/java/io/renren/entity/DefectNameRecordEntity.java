package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 故障类型表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Data
@TableName("defect_name_record")
public class DefectNameRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 故障名称id
	 */
	@TableId
	private Long id;
	/**
	 * 故障问题id
	 */
	private Long defectId;
	/**
	 * 缺陷名称
	 */
	private String name;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
