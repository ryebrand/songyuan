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

import io.renren.modules.sys.entity.EquipmentOwnerChangeRecordEntity;
import io.renren.modules.sys.service.EquipmentOwnerChangeRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 设备责任人变更记录表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/equipmentownerchangerecord")
public class EquipmentOwnerChangeRecordController {
    @Autowired
    private EquipmentOwnerChangeRecordService equipmentOwnerChangeRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:equipmentownerchangerecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equipmentOwnerChangeRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:equipmentownerchangerecord:info")
    public R info(@PathVariable("id") Long id){
        EquipmentOwnerChangeRecordEntity equipmentOwnerChangeRecord = equipmentOwnerChangeRecordService.getById(id);

        return R.ok().put("equipmentOwnerChangeRecord", equipmentOwnerChangeRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:equipmentownerchangerecord:save")
    public R save(@RequestBody EquipmentOwnerChangeRecordEntity equipmentOwnerChangeRecord){
        equipmentOwnerChangeRecordService.save(equipmentOwnerChangeRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:equipmentownerchangerecord:update")
    public R update(@RequestBody EquipmentOwnerChangeRecordEntity equipmentOwnerChangeRecord){
        ValidatorUtils.validateEntity(equipmentOwnerChangeRecord);
        equipmentOwnerChangeRecordService.updateById(equipmentOwnerChangeRecord);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:equipmentownerchangerecord:delete")
    public R delete(@RequestBody Long[] ids){
        equipmentOwnerChangeRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
