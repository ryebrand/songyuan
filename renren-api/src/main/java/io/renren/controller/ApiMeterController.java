package io.renren.controller;


import io.renren.common.utils.R;
import io.renren.service.MeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 电能表表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("meter")
@Api(tags = "电能表接口")
public class ApiMeterController {
    @Autowired
    private MeterService meterService;

    @GetMapping("/info")
    @ApiOperation("根据表箱id查询电表列表")
    public R info(@ApiParam(name = "meterBoxId", value = "表箱ID", required = true, type = "string") @RequestParam String meterBoxId) {
        return R.ok().put("data", meterService.selectByMeterBoxId(meterBoxId));
    }

}
