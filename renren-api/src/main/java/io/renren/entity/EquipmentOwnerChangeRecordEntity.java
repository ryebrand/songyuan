package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备责任人变更记录表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Data
@TableName("equipment_owner_change_record")
public class EquipmentOwnerChangeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备责任人变更记录id
	 */
	@TableId
	private Long id;
	/**
	 * 责任人id
	 */
	private Long equipmentOwnerId;
	/**
	 * 高压用户编号
	 */
	private String htUserId;
	/**
	 * 变更人 系统用户
	 */
	private Long changer;
	/**
	 * 变更时间
	 */
	private Date changeTime;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
