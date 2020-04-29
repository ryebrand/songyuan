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

import io.renren.modules.sys.entity.DefectEntity;
import io.renren.modules.sys.service.DefectService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 故障问题表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/defect")
public class DefectController {
    @Autowired
    private DefectService defectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:defect:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = defectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:defect:info")
    public R info(@PathVariable("id") Long id){
        DefectEntity defect = defectService.getById(id);

        return R.ok().put("defect", defect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:defect:save")
    public R save(@RequestBody DefectEntity defect){
        defectService.save(defect);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:defect:update")
    public R update(@RequestBody DefectEntity defect){
        ValidatorUtils.validateEntity(defect);
        defectService.updateById(defect);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:defect:delete")
    public R delete(@RequestBody Long[] ids){
        defectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
