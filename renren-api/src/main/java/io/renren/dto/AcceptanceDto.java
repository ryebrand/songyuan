package io.renren.dto;

import io.renren.entity.AcceptanceEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 扫描资产号查询待验收表箱DTO
 *
 * @author JunCheng He
 * @title: AcceptanceDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/28  9:33
 */
@Data
public class AcceptanceDto extends AcceptanceEntity implements Serializable {
    private static final long serialVersionUID = -6334047782186290666L;
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
     * 集中器封印
     */
    private String concentratorSeal;
    /**
     * 采集终端资产号
     */
    private String acquisitionTerminalAssetNumber;
    /**
     * 计量箱状态 1、运行 2、在库
     */
    private String meterBoxStatus;

    /**
     * 设备责任人
     */
    private String name;

}
