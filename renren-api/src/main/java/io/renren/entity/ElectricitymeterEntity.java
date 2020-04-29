package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 电能表表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-12 14:18:39
 */
@Data
@TableName("electricitymeter")
public class ElectricitymeterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long electricitymeterid;
	/**
	 * 电表资产号
	 */
	private String id;
	/**
	 * 电表箱资产号
	 */
	private String watchboxid;
	/**
	 * 户名
	 */
	private String username;
	/**
	 * 户号
	 */
	private String usernumber;
	/**
	 * 抄表代码段
	 */
	private String meterreadingcodesegment;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 资产号
	 */
	private String watchpropertynumber;
	/**
	 * 计量单位编号
	 */
	private String unitnumber;
	/**
	 * 用户电话
	 */
	private String userTel;
	/**
	 * 表箱关系
	 */
	private String watchBoxRelationship;
	/**
	 * 电能表封印
	 */
	private String wattHourMeterSeal;

}
