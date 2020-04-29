package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: SeriesEntity
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/31  9:54
 */
@Data
public class SeriesEntity implements Serializable {
    private static final long serialVersionUID = -2858791964196193223L;

    private String name;
    private String type = "line";
    private String stack = "总量";
    private Integer[] data;
}
