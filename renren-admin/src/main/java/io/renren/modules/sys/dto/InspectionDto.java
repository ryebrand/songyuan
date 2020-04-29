package io.renren.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检记录Dto
 *
 * @author JunCheng He
 * @title: InspectionDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/29  9:09
 */
@Data
public class InspectionDto implements Serializable {
    private static final long serialVersionUID = 7765137760798077260L;
    /**
     * 巡检id
     */
    @TableId
    private Long id;
    /**
     * 巡检时间
     */
    private Date createTime;
    /**
     * 故障表id
     */
    private Long malfunctionId;

    /**
     * 高压用户编号
     */
    private String htUserId;
    /**
     * 高压用户名称
     */
    private String htUserName;
    /**
     * 台区名称
     */
    //private String taiAreaName;

    /**
     * 巡检周期
     */
    private int checkCycle;

    /**
     * 高压用户用电地址
     */
    private String elecAddress;
    /**
     * 计量箱状态 1、运行 2、在库
     */
    private String meterBoxStatus;
    /**
     * 责任人名字
     */
    private String username;
    /**
     * 责任人电话号码
     */
    private String mobile;

    /**
     * 故障表照片
     */
    private String image;
}
