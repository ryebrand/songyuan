package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷记录
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-29 11:08:06
 */
@Data
@TableName("meter_defect_record")
public class MeterDefectRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Integer id;
	/**
	 * 电表箱id
	 */
	private Long meterBoxId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 故障描述id
	 */
	private Long defectId;
	/**
	 * 创建时间
	 */
	private Date createtime;

}
