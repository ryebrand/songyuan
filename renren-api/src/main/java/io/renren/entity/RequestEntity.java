package io.renren.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author JunCheng He
 * @title: RequertEntity
 * @projectName project
 * @description: TODO
 * @date 2019/7/27  15:43
 */
@Data
public class RequestEntity implements Serializable {
    private static final long serialVersionUID = 3403803930092660802L;
    /**
     * 参数集合
     */
    private String params;
    /**
     * 文件
     */
    private MultipartFile[] file;
}
