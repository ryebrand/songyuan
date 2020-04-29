package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.*;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 巡检表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/inspection")
public class InspectionController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeterBoxService meterBoxService;
    @Autowired
    private EquipmentOwnerService equipmentOwnerService;
    @Autowired
    private MalfunctionService malfunctionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:inspection:list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = inspectionService.queryPage(params);

        return R.ok().put("page", inspectionService.pageList(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:inspection:info")
    public R info(@PathVariable("id") Long id){
        InspectionEntity inspection = inspectionService.getById(id);

        return R.ok().put("inspection", inspection);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:inspection:save")
    public R save(@RequestBody InspectionEntity inspection){
        inspectionService.save(inspection);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:inspection:update")
    public R update(@RequestBody InspectionEntity inspection){
        ValidatorUtils.validateEntity(inspection);
        inspectionService.updateById(inspection);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:inspection:delete")
    public R delete(@RequestBody Long[] ids){
        inspectionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/faultList")
    public R getInspectionList( String username, int page,int pageSize) {
        //用于存储转换后的结果
        JSONArray result=new JSONArray();
        //将username转换成userid
        UserEntity userEntity = userService.getUserIdByUserName(username);
        //获取缺陷列表数据
        List<InspectionEntity> list = inspectionService.getInspectionList(userEntity.getUserId(), pageSize * (page - 1), pageSize);
        for (InspectionEntity inspectionEntity : list) {
            JSONObject jsonObject=new JSONObject();
            //根据电表箱id转换成电表箱设备编号
            MeterBoxEntity meterBoxEntity = meterBoxService.getMeterBoxAssetNumberById(inspectionEntity.getHtUserId());
            jsonObject.put("meterBoxAssetNumber", meterBoxEntity.getMeterBoxAssetNumber());
            //根据id获取电表箱主人姓名
            EquipmentOwnerEntity equipmentOwnerEntity = equipmentOwnerService.getOwnerByMeterBoxId(meterBoxEntity.getEquipmentOwnerId());
            jsonObject.put("ownerName", equipmentOwnerEntity.getName());
            //巡检时间
            jsonObject.put("checkTime", inspectionEntity.getCreateTime());
            //巡检人姓名
            jsonObject.put("userName", username);
            //根据故障表id转换成故障等级
            MalfunctionEntity malfunctionEntity = malfunctionService.getDefectLevelById(inspectionEntity.getMalfunctionId());
            if(malfunctionEntity.getType()==0){
                jsonObject.put("type","巡检故障");
            }else  if(malfunctionEntity.getType()==1){
                jsonObject.put("type","验收故障");
            }
            if (malfunctionEntity.getDefectLevel() == 0) {
                jsonObject.put("defectLevel", "无缺陷");
            } else if (malfunctionEntity.getDefectLevel() == 1) {
                jsonObject.put("defectLevel", "危急缺陷");
            } else if (malfunctionEntity.getDefectLevel() == 2) {
                jsonObject.put("defectLevel", "严重缺陷");
            } else if (malfunctionEntity.getDefectLevel() == 3) {
                jsonObject.put("defectLevel", "一般缺陷（轻度）");
            } else if (malfunctionEntity.getDefectLevel() == 4) {
                jsonObject.put("defectLevel", "一般缺陷（轻微）");
            }
            if(malfunctionEntity.getProcessingStatus()==0){
                jsonObject.put("processingStatus","待处理");
            }else if(malfunctionEntity.getProcessingStatus()==1){
                jsonObject.put("processingStatus","处理中");
            }else if(malfunctionEntity.getProcessingStatus()==2){
                jsonObject.put("processingStatus","已处理");
            }
            //异常情况
            jsonObject.put("faultDescription",malfunctionEntity.getFaultDescription());
            //图片
            jsonObject.put("images", malfunctionEntity.getImage());
            result.add(jsonObject);
        }
        //回传数据
        return R.ok().put("data", result.toJSONString());
    }

}
