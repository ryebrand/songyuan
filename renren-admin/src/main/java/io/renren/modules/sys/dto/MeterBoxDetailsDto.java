package io.renren.modules.sys.dto;

import io.renren.modules.sys.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 电表箱详情Dto
 *
 * @author JunCheng He
 * @title: MeterBoxDetailsDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/8/8  9:54
 */
@Data
public class MeterBoxDetailsDto extends MeterBoxEntity implements Serializable {
    private static final long serialVersionUID = 556479168246653179L;
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
