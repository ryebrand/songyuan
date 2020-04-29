package io.renren.dto;

import io.renren.entity.MeterBoxEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 电表箱详情信息 DTO
 *
 * @author JunCheng He
 * @title: MeterBoxInfoDto
 * @projectName project
 * @description: TODO
 * @date 2019/7/27  16:07
 */
@Data
public class MeterBoxInfoDto  extends MeterBoxEntity implements Serializable  {
    private static final long serialVersionUID = -1926307981992889308L;

    /**
     * 名字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;
}
