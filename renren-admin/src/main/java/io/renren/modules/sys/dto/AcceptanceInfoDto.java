package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 验收详情Dto
 *
 * @author JunCheng He
 * @title: AcceptanceInfoDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/29  15:44
 */
@Data
public class AcceptanceInfoDto implements Serializable {
    private static final long serialVersionUID = -7506653942442188838L;
    /**
     * 验收列表id
     */
    private Long id;
    /**
     * 生成日期
     */
    private Date buildDate;
    /**
     * 完成日期
     */
    private Date completionDate;
    /**
     * 验收状态：0-待验收  1-存在缺陷 2-验收完成
     */
    private Integer step;


    /**
     * 计量箱资产号
     */
    private String meterBoxAssetNumber;
    /**
     * 台区名称
     */
    private String taiAreaName;
    /**
     * 位置：村庄（小区）名称
     */
    private String address;


    /**
     * 名字
     */
    private String ownerName;

    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 座机
     */
    private String studioCamera;


    /**
     * 区域名称
     */
    private String regionName;


    /**
     * 验收用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobile;


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
     * 缺陷处理状态 默认 0 待处理 1处理中 2 处理完成
     */
    private Integer processingStatus;
}
