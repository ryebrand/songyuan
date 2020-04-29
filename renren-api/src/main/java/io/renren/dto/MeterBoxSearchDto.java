package io.renren.dto;

import io.renren.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: MeterBoxSearchDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/31  16:32
 */
@Data
public class MeterBoxSearchDto extends MeterBoxEntity implements Serializable {
    private static final long serialVersionUID = 1881330386450016424L;
    /**
     * 名字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 座机
     */
    private String studioCamera;
}
