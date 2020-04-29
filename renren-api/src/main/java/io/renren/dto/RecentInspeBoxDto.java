package io.renren.dto;

import io.renren.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 最近巡检过的表箱DTO
 *
 * @author JunCheng He
 * @title: RecentInspeBoxDto
 * @projectName project
 * @description: TODO
 * @date 2019/7/27  16:42
 */
@Data
public class RecentInspeBoxDto extends MeterBoxEntity implements Serializable {
    private static final long serialVersionUID = 1963976615195633648L;
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
     * 删除状态 ：0：使用 1：删除
     */
    private Integer deleteStatus;


    /**
     * 计量箱资产号
     */
    private String meterBoxAssetNumber;
    /**
     * 台区名称
     */
    private String taiAreaName;
    /**
     * 台区总表资产号
     */
    private String taiAreaTotalAssetNumber;
    /**
     * 总电能表封印
     */
    private String totalEnergyMeterSeal;
    /**
     * 供电所
     */
    private String powerSupply;
    /**
     * 计量箱规格型号
     */
    private String meterBoxModel;
    /**
     * 位置：村庄（小区）名称
     */
    private String address;
    /**
     * 倍率
     */
    private Integer magnification;
    /**
     * 计量箱编号
     */
    private String meterBoxNumber;
    /**
     * 计量箱地址（经纬度）
     */
    private String meterBoxAddress;
    /**
     * 采集终端资产号
     */
    private String acquisitionTerminalAssetNumber;
    /**
     * 计量箱状态 1、运行 2、在库
     */
    private String meterBoxStatus;

    /**
     * 名字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;

}
