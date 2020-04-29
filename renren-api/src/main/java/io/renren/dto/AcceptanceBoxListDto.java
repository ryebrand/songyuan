package io.renren.dto;

import io.renren.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JunCheng He
 * @title: AcceptanceBoxListDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/28  11:59
 */
@Data
public class AcceptanceBoxListDto extends MeterBoxEntity implements Serializable {
    private static final long serialVersionUID = 5309810389486226812L;

    /**
     * 以下都是验收表字段
     */
    /**
     * 类型：0 ：电表箱   ## 1：电能表
     */
    private Integer type;
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
     * 验收状态：0-待验收  1-存在缺陷 2-验收完成
     */
    private Integer step;
    /**
     * 设备责任人
     */
    private String name;

}
