package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.RegionEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.service.RegionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 区域表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 15:38:51
 */
@RestController
@RequestMapping("sys/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:region:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = regionService.queryPage(params);

        return R.ok().put("page", regionService.pageList(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:region:info")
    public R info(@PathVariable("id") Long id) {
        RegionEntity region = regionService.getById(id);

        return R.ok().put("region", region);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:region:save")
    public R save(@RequestBody RegionEntity region) {
        regionService.save(region);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:region:update")
    public R update(@RequestBody RegionEntity region) {
        ValidatorUtils.validateEntity(region);
        regionService.updateById(region);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:region:delete")
    public R delete(@RequestBody Long[] ids) {
        regionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 分配管理员
     */
    @RequestMapping("/distribution/{id}/{userId}")
    @RequiresPermissions("sys:region:info")
    public R distribution(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        return R.ok().put("region", regionService.updateUserIdById(id, userId));
    }


}
