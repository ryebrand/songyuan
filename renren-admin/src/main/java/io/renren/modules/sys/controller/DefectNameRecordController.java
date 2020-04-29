package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.DefectNameRecordEntity;
import io.renren.modules.sys.service.DefectNameRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 故障类型表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/defectnamerecord")
public class DefectNameRecordController {
    @Autowired
    private DefectNameRecordService defectNameRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:defectnamerecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = defectNameRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:defectnamerecord:info")
    public R info(@PathVariable("id") Long id){
        DefectNameRecordEntity defectNameRecord = defectNameRecordService.getById(id);

        return R.ok().put("defectNameRecord", defectNameRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:defectnamerecord:save")
    public R save(@RequestBody DefectNameRecordEntity defectNameRecord){
        defectNameRecordService.save(defectNameRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:defectnamerecord:update")
    public R update(@RequestBody DefectNameRecordEntity defectNameRecord){
        ValidatorUtils.validateEntity(defectNameRecord);
        defectNameRecordService.updateById(defectNameRecord);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:defectnamerecord:delete")
    public R delete(@RequestBody Long[] ids){
        defectNameRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取故障描述
     * @return
     */
    @GetMapping("/getRecords")
    public R getRecords(){
        List<Map<String,Object>> list=defectNameRecordService.getRecords();
        return R.ok().put("data",list);
    }

}
