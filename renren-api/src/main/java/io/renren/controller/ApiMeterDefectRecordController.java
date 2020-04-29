package io.renren.controller;


import io.renren.service.MeterDefectRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 缺陷记录
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 13:48:14
 */
@RestController
@RequestMapping("meterdefectrecord")
@Api(tags = "缺陷记录接口")
public class ApiMeterDefectRecordController {
    @Autowired
    private MeterDefectRecordService meterDefectRecordService;


}
