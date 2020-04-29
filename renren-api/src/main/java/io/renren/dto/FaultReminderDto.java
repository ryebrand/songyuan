package io.renren.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JunCheng He
 * @title: FaultReminderDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/31  10:54
 */
@Data
public class FaultReminderDto implements Serializable {
    private static final long serialVersionUID = 8352628853127189724L;
    /**
     * 缺陷id
     */
    private Long id;
    /**
     * 缺陷级别 0：无缺陷 1：危急缺陷 2：严重缺陷 3：一般缺陷（轻度） 4：一般缺陷（轻微）
     */
    private Integer defectLevel;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 台区名称
     */
    private String taiAreaName;
    /**
     * 位置：村庄（小区）名称
     */
    private String address;
}

