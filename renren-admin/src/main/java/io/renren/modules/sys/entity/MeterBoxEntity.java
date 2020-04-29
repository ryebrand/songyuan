package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计量箱表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-08-01 11:08:42
 */
@Data
@TableName("meter_box")
public class MeterBoxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 电表箱id
	 */
	@TableId
	private Long id;
	/**
	 * 计量箱资产号
	 */
	private String meterBoxAssetNumber;
	/**
	 * 台区名称
	 */
	private String taiAreaName;
	/**
	 * 台区经理姓名
	 */
	private String taiAreaManagerName;
	/**
	 * 台区经理工作电话
	 */
	private String taiAreaManagerWorkPhone;
	/**
	 * 台区经理电话
	 */
	private String taiAreaManagerPhone;
	/**
	 * 台区总表资产号
	 */
	private String taiAreaTotalAssetNumber;
	/**
	 * 总电能表封印
	 */
	private String totalEnergyMeterSeal;
	/**
	 * 供电所
	 */
	private String powerSupply;
	/**
	 * 计量箱规格型号
	 */
	private String meterBoxModel;
	/**
	 * 位置：村庄（小区）名称
	 */
	private String address;
	/**
	 * 倍率
	 */
	private Integer magnification;
	/**
	 * 计量箱编号
	 */
	private String meterBoxNumber;
	/**
	 * 集中器资产号
	 */
	private String concentratorAssetNumber;
	/**
	 * 计量箱地址（经纬度）
	 */
	private String meterBoxAddress;
	/**
	 * 采集终端资产号
	 */
	private String acquisitionTerminalAssetNumber;
	/**
	 * 集中器封印
	 */
	private String concentratorSeal;
	/**
	 * 计量箱状态 1、运行 2、在库
	 */
	private String meterBoxStatus;
	/**
	 * 设备责任人id
	 */
	private Long equipmentOwnerId;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
