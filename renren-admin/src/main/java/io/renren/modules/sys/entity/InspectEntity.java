package io.renren.modules.sys.entity;

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
 * @date 2020-04-20 10:35:49
 */
@Data
@TableName("inspect")
public class InspectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 巡检任务id
	 */
	@TableId
	private Long id;
	/**
	 * 高压用户id
	 */
	private String htUserId;
	/**
	 * 巡检区域
	 */
	private String inspectArea;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除状态
	 */
	private Integer delStatus;

}
