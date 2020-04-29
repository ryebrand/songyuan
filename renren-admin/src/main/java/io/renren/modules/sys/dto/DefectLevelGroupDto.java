package io.renren.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 缺陷等级统计Dto
 *
 * @author JunCheng He
 * @title: DefectLevelGroupDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/8/1  14:30
 */
@Data
public class DefectLevelGroupDto implements Serializable {
    private static final long serialVersionUID = 2770110134951712952L;
    /**
     * 缺陷级别 0：无缺陷 1：危急缺陷 2：严重缺陷 3：一般缺陷（轻度） 4：一般缺陷（轻微）
     */
    private Integer defectLevel;
    /**
     * 分类个数
     */
    private Integer count;
}
