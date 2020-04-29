package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 电能表表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Data
@TableName("meter")
public class MeterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 电能表id
	 */
	@TableId
	private Long id;
	/**
	 * 电表箱id
	 */
	private Long meterBoxId;
	/**
	 * 电能表资产号
	 */
	private String energyMeterAssetNumber;
	/**
	 * 户名
	 */
	private String accountName;
	/**
	 * 户号
	 */
	private String accountNumber;
	/**
	 * 用户电话
	 */
	private String userPhone;
	/**
	 * 箱表关系
	 */
	private String boxRelationship;
	/**
	 * 隔离开关资产号
	 */
	private String isolationSwitchAssetNumber;
	/**
	 * 断路器资产号
	 */
	private String circuitBreakerAssetNumber;
	/**
	 * 互感器资产号
	 */
	private String transformerAssetNumber;
	/**
	 * 电能表封印
	 */
	private String electricEnergyMeterSeal;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
