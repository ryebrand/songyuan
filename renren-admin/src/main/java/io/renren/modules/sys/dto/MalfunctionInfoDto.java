package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷详情Dto
 *
 * @author JunCheng He
 * @title: MalfunctionDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/29  19:51
 */
@Data
public class MalfunctionInfoDto implements Serializable {
    private static final long serialVersionUID = -4596634607331222350L;
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
     * 高压用户编号
     */
    private String htUserId;
    /**
     * 高压用户名
     */
    private String userName;
    /**
     * 台区名称
     */
    //private String taiAreaName;
    /**
     * 位置：村庄（小区）名称
     */
    private String elecAddress;
    /**
     * 计量箱状态 1、运行 2、在库
     */
    //private String meterBoxStatus;



    /**
     * 名字
     */
    private String checkUserName;
    /**
     * 电话号码
     */
    private String mobile;
}
