package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.MeterEntity;
import io.renren.modules.sys.service.MeterService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 电能表表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/meter")
public class MeterController {
    @Autowired
    private MeterService meterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("sys:meter:list")
    @RequiresPermissions("sys:meterbox:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:meter:info")
    public R info(@PathVariable("id") Long id){
        MeterEntity meter = meterService.getById(id);

        return R.ok().put("meter", meter);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:meter:save")
    public R save(@RequestBody MeterEntity meter){
        meterService.save(meter);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:meter:update")
    public R update(@RequestBody MeterEntity meter){
        ValidatorUtils.validateEntity(meter);
        meterService.updateById(meter);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meter:delete")
    public R delete(@RequestBody Long[] ids){
        meterService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
