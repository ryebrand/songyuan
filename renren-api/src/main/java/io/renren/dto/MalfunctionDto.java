package io.renren.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷跟踪Dto
 *
 * @author JunCheng He
 * @title: MalfunctionDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/30  17:32
 */
@Data
public class MalfunctionDto implements Serializable {
    private static final long serialVersionUID = -8845183479907515736L;
    /**
     * 缺陷id
     */
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

    private String acceptImage;

    /**
     * 缺陷处理状态
     */
    private Integer processingStatus;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 高压用户编号
     */
    private String htUserId;
    /**
     * 终端资产编号
     */
    private String assetsCode;

    /**
     * 位置：村庄（小区）名称
     */
    private String elecAddress;
    /**
     * 计量箱状态 1、运行 2、在库
     */
    //private String meterBoxStatus;
    /**
     * 供电所
     */
    private String powerSupply;
    /**
     * 巡检人名字
     */
    private String checkUserName;
    /**
     * 设备主人
     */
    private String username;
    /**
     * 公司名称
     */
    private String userName;

    /**
     * 电话号码
     */
    private String mobile;
}
