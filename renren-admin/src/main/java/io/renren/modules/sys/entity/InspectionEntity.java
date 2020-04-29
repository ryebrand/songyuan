package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-29 11:08:06
 */
@Data
@TableName("inspection")
public class InspectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 巡检id
	 */
	@TableId
	private Long id;
	/**
	 * 高压用户id
	 */
	private String htUserId;
	/**
	 * 故障表id
	 */
	private Long malfunctionId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 巡检时间
	 */
	private Date createTime;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
