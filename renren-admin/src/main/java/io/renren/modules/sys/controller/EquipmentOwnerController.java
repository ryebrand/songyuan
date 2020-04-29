package io.renren.modules.sys.controller;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.EquipmentOwnerEntity;
import io.renren.modules.sys.service.EquipmentOwnerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/equipmentowner")
public class EquipmentOwnerController {
    @Autowired
    private EquipmentOwnerService equipmentOwnerService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:equipmentowner:list")
    public R list(@RequestParam Map<String, Object> params) {
        //PageUtils page = equipmentOwnerService.queryPage(params);
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:equipmentowner:info")
    public R info(@PathVariable("id") Long id) {
        EquipmentOwnerEntity equipmentOwner = equipmentOwnerService.getById(id);

        return R.ok().put("equipmentOwner", equipmentOwner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:equipmentowner:save")
    public R save(@RequestBody EquipmentOwnerEntity equipmentOwner) {
        equipmentOwner.setCreateDate(new Date());
        equipmentOwner.setDeleteStatus(0);
        equipmentOwnerService.save(equipmentOwner);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:equipmentowner:update")
    public R update(@RequestBody EquipmentOwnerEntity equipmentOwner) {
        ValidatorUtils.validateEntity(equipmentOwner);
        equipmentOwnerService.updateById(equipmentOwner);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:equipmentowner:delete")
    public R delete(@RequestBody Long[] ids) {
        equipmentOwnerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取所有名字
     * @return
     */
    @RequestMapping("/ownerNames")
    public R ownerNames() {
        List<EquipmentOwnerEntity> list = equipmentOwnerService.list();
        return R.ok().put("data",list);
    }

}
