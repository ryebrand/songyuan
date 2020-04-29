package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 故障问题表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Data
@TableName("defect")
public class DefectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 故障问题id
	 */
	@TableId
	private Long id;
	/**
	 * 分类标志： 1 开箱检查 2 不开箱检查 3 危及缺陷
 4 严重缺陷  5 一般缺陷（轻度） 6 一般缺陷（轻微）

	 */
	private Integer splitTag;
	/**
	 * 故障名称
	 */
	private String name;

}
