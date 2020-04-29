package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据最近巡检时间统计次数Dto
 *
 * @author JunCheng He
 * @title: CreateTimeGroupDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/8/1  14:55
 */
@Data
public class CreateTimeGroupDto implements Serializable {
    private static final long serialVersionUID = 4543034703679391862L;

    /**
     * 日期
     */
    private String days;
    /**
     * 次数
     */
    private Integer num;

}
