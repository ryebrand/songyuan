package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: MalfunctionStatics
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/31  9:26
 */
@Data
public class MalfunctionStatics implements Serializable {
    private static final long serialVersionUID = -6125377295936909803L;
    /**
     * 月份
     */
    private Integer mont;
    /**
     * 数量
     */
    private Integer count;
}
