package io.renren.controller;

import io.renren.service.EquipmentOwnerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("equipmentowner")
@Api(tags = "设备责任人接口")
public class ApiEquipmentOwnerController {
    @Autowired
    private EquipmentOwnerService equipmentOwnerService;


}
