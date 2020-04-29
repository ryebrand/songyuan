package io.renren.modules.sys.dto;


import io.renren.modules.sys.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: MeterBoxDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/28  17:09
 */
@Data
public class MeterBoxDto extends MeterBoxEntity implements Serializable {
    private static final long serialVersionUID = 2902049206909152222L;
    //责任人
    /**
     * 名字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;
}
