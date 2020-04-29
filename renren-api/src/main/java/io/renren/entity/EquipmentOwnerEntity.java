package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备责任人表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Data
@TableName("equipment_owner")
public class EquipmentOwnerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备主人id（台区经理或抄表人等）
	 */
	@TableId
	private Long id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 座机
	 */
	private String studioCamera;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
