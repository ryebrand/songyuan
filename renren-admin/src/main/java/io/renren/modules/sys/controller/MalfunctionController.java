package io.renren.modules.sys.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dto.MalfunctionInfoDto;
import io.renren.modules.sys.service.InspectionService;
import io.renren.modules.sys.service.MeterBoxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.MalfunctionEntity;
import io.renren.modules.sys.service.MalfunctionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:09
 */
@RestController
@RequestMapping("sys/malfunction")
public class MalfunctionController {
    @Autowired
    private MalfunctionService malfunctionService;
    @Autowired
    private MeterBoxService meterBoxService;
    @Autowired
    private InspectionService inspectionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:malfunction:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = malfunctionService.queryPage(params);

        return R.ok().put("page", malfunctionService.pageList(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("sys:malfunction:info")
    public R info(@PathVariable("id") Long id) {
        MalfunctionEntity malfunction = malfunctionService.getById(id);

        return R.ok().put("malfunction", malfunction);
    }

    /**
     * 详情
     */
    @RequestMapping("/details/{id}")
    @RequiresPermissions("sys:malfunction:info")
    public R details(@PathVariable("id") Long id) {
        MalfunctionInfoDto malfunctionInfoDto = malfunctionService.selectByMalfunctionId(id);
        return R.ok().put("data", malfunctionInfoDto);
    }

    /**
     * 缺陷处理
     */
    @RequestMapping("/handler/{id}/{type}")
    @RequiresPermissions("sys:malfunction:info")
    public R details(@PathVariable("id") Long id, @PathVariable("type") Integer type) {
        malfunctionService.updateByMalfuctionId(id, type);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:malfunction:save")
    public R save(@RequestBody MalfunctionEntity malfunction) {
        malfunctionService.save(malfunction);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("sys:malfunction:update")
    public R update(@RequestBody MalfunctionEntity malfunction) {
        ValidatorUtils.validateEntity(malfunction);
        malfunctionService.updateById(malfunction);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:malfunction:delete")
    public R delete(@RequestBody Long[] ids) {
        malfunctionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取基本的缺陷统计信息
     *
     * @return
     */
    @GetMapping("/faultCount")
    public R getInspectionCount() {
        JSONObject jsonObject = new JSONObject();
        //故障类型数量统计
        int acceptanceTypeCount = malfunctionService.getCountByCondition(0, -1, -1);
        int inspectionTypeCount = malfunctionService.getCountByCondition(1, -1, -1);
        jsonObject.put("acceptanceTypeCount", acceptanceTypeCount);
        jsonObject.put("inspectionTypeCount", inspectionTypeCount);
        //故障级别数量统计
        int levelOne = malfunctionService.getCountByCondition(-1, 0, -1);
        int levelTwo = malfunctionService.getCountByCondition(-1, 1, -1);
        int levelThree = malfunctionService.getCountByCondition(-1, 2, -1);
        int levelFour = malfunctionService.getCountByCondition(-1, 3, -1);
        int levelFive = malfunctionService.getCountByCondition(-1, 4, -1);
        jsonObject.put("levelOne", levelOne);
        jsonObject.put("levelTwo", levelTwo);
        jsonObject.put("levelThree", levelThree);
        jsonObject.put("levelFour", levelFour);
        jsonObject.put("levelFive", levelFive);
        //故障状态数量统计
        int pending = malfunctionService.getCountByCondition(-1, -1, 0);
        int processing = malfunctionService.getCountByCondition(-1, -1, 1);
        int processed = malfunctionService.getCountByCondition(-1, -1, 2);
        jsonObject.put("pending", pending);
        jsonObject.put("processing", processing);
        jsonObject.put("processed", processed);
        return R.ok().put("data", JSON.toJSONString(jsonObject));
    }

    /**
     * 根据多条件，自定义获取缺陷数量
     *
     * @param type
     * @param defectLevel
     * @param processingStatus
     * @return
     */
    @GetMapping("/customizeFalutCount")
    public R getInspectionCountByCustomize(int type, int defectLevel, int processingStatus) {
        int count = malfunctionService.getCountByCondition(type, defectLevel, processingStatus);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customizeCount", count);
        return R.ok().put("data", JSON.toJSONString(jsonObject));
    }

    @GetMapping("/getDefectLevel")
    public R getDefectLevelByTime(String startTime, String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (startTime == null || "".equals(startTime)) {
            //默认查30天的数据
            startTime = dateFormat.format(new Date(date.getTime() - (long) 30 * 24 * 60 * 60 * 1000)) + " 0:00:00";
        }
        if (endTime == null || "".equals(endTime)) {
            //默认截止时间为当前时间
            endTime = dateFormat.format(date) + " 23:59:59";
        }
        //按供电所和台区统计电表箱数量
        List<Map<String, Object>> list = meterBoxService.getTotalByDiv();
        for (Map<String, Object> map : list) {
            map.put("levelOne", malfunctionService.getDefectLevelByTime(0, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("levelTwo", malfunctionService.getDefectLevelByTime(1, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("levelTree", malfunctionService.getDefectLevelByTime(2, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("levelFour", malfunctionService.getDefectLevelByTime(3, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("levelFive", malfunctionService.getDefectLevelByTime(4, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("inspectedMeter",inspectionService.getInspectedMeterCountByCondition(startTime,endTime,map.get("tai_area_name").toString(), map.get("power_supply").toString()));
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("startTime",startTime);
        jsonObject.put("endTime",endTime);
        JSONObject result=new JSONObject();
        result.put("date",jsonObject);
        result.put("data",list);
        return R.ok().put("data", result);
    }

    @GetMapping("/getProcessingStatus")
    public R getProcessingStatus(String startTime, String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (startTime == null || "".equals(startTime)) {
            //默认查30天的数据
            startTime = dateFormat.format(new Date(date.getTime() - (long) 30 * 24 * 60 * 60 * 1000)) + " 0:00:00";
        }
        if (endTime == null || "".equals(endTime)) {
            //默认截止时间为当前时间
            endTime = dateFormat.format(date) + " 23:59:59";
        }
        //各个供电所下的台区的分类统计
        List<Map<String, Object>> list = meterBoxService.getProcessingStatusByDiv();
        for (Map<String, Object> map : list) {
            map.put("pending", malfunctionService.getProcessingStatus(0, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("processing", malfunctionService.getProcessingStatus(1, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
            map.put("processed", malfunctionService.getProcessingStatus(2, map.get("tai_area_name").toString(), map.get("power_supply").toString(), startTime, endTime));
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("startTime",startTime);
        jsonObject.put("endTime",endTime);
        JSONObject result=new JSONObject();
        result.put("date",jsonObject);
        result.put("data",list);
        return R.ok().put("data", result);
    }
}
