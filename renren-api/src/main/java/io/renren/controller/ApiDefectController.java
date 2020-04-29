package io.renren.controller;

import io.renren.service.DefectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 故障问题表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@RestController
@RequestMapping("defect")
@Api(tags = "故障问题接口")
public class ApiDefectController {
    @Autowired
    private DefectService defectService;


}
