package io.renren.controller;

import io.renren.service.EquipmentOwnerChangeRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 设备责任人变更记录表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("equipmentownerchangerecord")
@Api(tags = "设备责任人变更接口")
public class ApiEquipmentOwnerChangeRecordController {
    @Autowired
    private EquipmentOwnerChangeRecordService equipmentOwnerChangeRecordService;



}
