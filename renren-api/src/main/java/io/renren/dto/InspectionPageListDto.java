package io.renren.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检分页查询列表DTO
 *
 * @author JunCheng He
 * @title: InspectionPageListDto
 * @projectName project
 * @description: TODO
 * @date 2019/7/27  14:20
 */
@Data
public class InspectionPageListDto implements Serializable {
    private static final long serialVersionUID = 4444011076602214418L;
    /**
     * 巡检id
     */
    private Long id;
    /**
     * 电表箱id
     */
    private Long meterBoxId;
    /**
     * 故障表id
     */
    private Long malfunctionId;

    /**
     * 巡检时间
     */
    private Date createTime;

    /**
     * 巡检时间
     */
    private Long userId;

    /**
     * 类型：0 巡检故障 1 验收故障
     */
    private Integer type;
    /**
     * 缺陷级别 0：无缺陷 1：危急缺陷 2：严重缺陷 3：一般缺陷（轻度） 4：一般缺陷（轻微）
     */
    private Integer defectLevel;

    /**
     * 缺陷处理状态
     */
    private Integer processingStatus;

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
     * 巡检人
     */
    private String name;

    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 计量箱资产号
     */
    private String meterBoxAssetNumber;

    /**
     * 责任人
     */
    private String ownerName;
}
