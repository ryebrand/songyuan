package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 13:44:48
 */
@Data
@TableName("transformer")
public class TransformerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 互感器条码
	 */
	@TableId
	private long id;

	private String transformCode;
	/**
	 * 互感器类别
	 */
	private String transformCategory;
	/**
	 * 电压互感器变比
	 */
	@TableField(value = "voltage_var", strategy = FieldStrategy.IGNORED)
	private String voltageVar;
	/**
	 * 电流互感器变比
	 */
	@TableField(value = "current_val", strategy = FieldStrategy.IGNORED)
	private String currentVal;
	/**
	 * 高压用户id
	 */
	private String htUserId;

}
