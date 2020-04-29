package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-29 11:08:06
 */
@Data
@TableName("malfunction")
public class MalfunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 缺陷id
	 */
	@TableId
	private Long id;
	/**
	 * 类型：0 巡检故障 1 验收故障
	 */
	private Integer type;
	/**
	 * 缺陷级别 0：无缺陷 1：危急缺陷 2：严重缺陷 3：一般缺陷（轻度） 4：一般缺陷（轻微）
	 */
	private Integer defectLevel;
	/**
	 * 故障描述集合
	 */
	private String faultDescription;
	/**
	 * 其它故障描述
	 */
	private String otherFaultDescription;
	/**
	 * 图片上传地址 多个，分割
	 */
	private String image;
	/**
	 * 验收图片
	 */
	private String acceptImage;
	/**
	 * 缺陷处理状态 默认 0 待处理 1处理中 2 处理完成
	 */
	private Integer processingStatus;
	/**
	 * 生成时间
	 */
	private Date createTime;
	/**
	 * 删除状态 ：0：使用 1：删除 
	 */
	private Integer deleteStatus;

}
