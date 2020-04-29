package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-29 11:08:06
 */
@Data
@TableName("acceptance")
public class AcceptanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验收列表id
     */
    @TableId
    private Long id;
    /**
     * 类型：0 ：电表箱   ## 1：电能表
     */
    private Integer type;
    /**
     * 验收人
     */
    private Long userId;
    /**
     * 资产号
     */
    private String assetNumber;
    /**
     * 验收类型 0-新装 1-改造 2-更换 3-消缺
     */
    private Integer acceptanceType;
    /**
     * 缺陷描述：默认为空
     */
    private String defectDescription;
    /**
     * 生成日期
     */
    private Date buildDate;
    /**
     * 完成日期
     */
    private Date completionDate;
    /**
     * 故障表id 默认为空 验收状态为存在缺陷时 写入故障id
     */
    private Long malfunctionId;
    /**
     * 区域id
     */
    private Long regionId;
    /**
     * 验收状态：0-待验收  1-存在缺陷 2-验收完成
     */
    private Integer step;
    /**
     * 删除状态 ：0：使用 1：删除
     */
    private Integer deleteStatus;

}
