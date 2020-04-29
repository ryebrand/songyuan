package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: RegionDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/30  10:28
 */
@Data
public class RegionDto implements Serializable {
    private static final long serialVersionUID = -8490467053079951866L;

    /**
     * 区域id
     */
    private Long id;
    /**
     * 区域名称
     */
    private String name;


    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String tbUserName;
}
