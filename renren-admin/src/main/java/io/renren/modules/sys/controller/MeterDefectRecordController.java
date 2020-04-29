package io.renren.modules.sys.controller;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.MeterDefectRecordEntity;
import io.renren.modules.sys.service.MeterDefectRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 缺陷记录
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/meterdefectrecord")
public class MeterDefectRecordController {
    @Autowired
    private MeterDefectRecordService meterDefectRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:meterdefectrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meterDefectRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:meterdefectrecord:info")
    public R info(@PathVariable("id") Integer id){
        MeterDefectRecordEntity meterDefectRecord = meterDefectRecordService.getById(id);

        return R.ok().put("meterDefectRecord", meterDefectRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:meterdefectrecord:save")
    public R save(@RequestBody MeterDefectRecordEntity meterDefectRecord){
        meterDefectRecordService.save(meterDefectRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:meterdefectrecord:update")
    public R update(@RequestBody MeterDefectRecordEntity meterDefectRecord){
        ValidatorUtils.validateEntity(meterDefectRecord);
        meterDefectRecordService.updateById(meterDefectRecord);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meterdefectrecord:delete")
    public R delete(@RequestBody Integer[] ids){
        meterDefectRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 统计每一种缺陷对应的数量
     * @return
     */
    public R getMeterDefectRecordById() {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Integer>> list = meterDefectRecordService.getMeterDefectRecordById();
        Set<Integer> set=new HashSet<Integer>();
        for (Map<String,Integer> map:list) {
            set.add(map.get("defect_id"));
        }
        for(int i=1;i<29;i++){
            if(!set.contains(i)){
                Map<String,Integer> map=new HashMap<String,Integer>();
                map.put("defect_id",i);
                map.put("count",0);
                list.add(map);
            }
        }
        return R.ok().put("data",list);
    }
}
