package io.renren.controller;

import io.renren.service.DefectNameRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 故障类型表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("defectnamerecord")
@Api(tags = "故障类型接口")
public class ApiDefectNameRecordController {
    @Autowired
    private DefectNameRecordService defectNameRecordService;


}
