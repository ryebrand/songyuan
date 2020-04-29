package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 14:11:13
 */
@Data
@TableName("ht_user")
public class HtUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用电类别
	 */
	private String elecCategory;
	/**
	 * 用户分类
	 */
	private String userCategory;
	/**
	 * 负荷性质
	 */
	private String loadProperty;
	/**
	 * 用电地址信息
	 */
	private String elecAddress;
	/**
	 * 检察人员
	 */
	private Integer checkUser;
	/**
	 * 检查周期
	 */
	private Integer checkCycle;
	/**
	 * 上次检查日期
	 */
	private Date checkDateBefore;
	/**
	 * 下次检查日期
	 */
	private Date checkDateAfter;
	/**
	 * 电能表条码
	 */
	private String elecCode;
	/**
	 * 采集点编号
	 */
	private String collectCode;
	/**
	 * 终端资产编号
	 */
	private String assetsCode;
	/**
	 * 经度
	 */
	private double lat;
	/**
	 * 维度
	 */
	private double lng;

}
