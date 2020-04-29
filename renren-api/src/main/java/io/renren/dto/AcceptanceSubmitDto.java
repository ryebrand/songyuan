package io.renren.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 待验收提交DTO
 *
 * @author JunCheng He
 * @title: AcceptanceSubmitDto
 * @projectName SdEPPI_WEB
 * @description: TODO
 * @date 2019/7/28  9:42
 */
@Data
public class AcceptanceSubmitDto implements Serializable {
    private static final long serialVersionUID = 5464098228223128832L;


    /**
     * 缺陷对象
     */
    private String params;

    /**
     * 提交验收状态 1 存在缺陷 2  验收完成
     */
    private Integer type;

    /**
     * 上传文件数组
     */
    private MultipartFile[] file;
}
