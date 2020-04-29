package io.renren.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import io.renren.entity.*;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 巡检表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("inspection")
@Api(tags = "巡检接口")
public class ApiInspectionController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeterBoxService meterBoxService;
    @Autowired
    private MalfunctionService malfunctionService;
    @Autowired
    private EquipmentOwnerService equipmentOwnerService;

    @GetMapping("/pageList")
    @ApiOperation(value = "分页获取巡检记录")
    public R queryPageList(@ApiParam(name = "pageNum", value = "页码", required = true, type = "int") @RequestParam Integer pageNum,
                           @ApiParam(name = "pageSize", value = "每页大小", required = true, type = "int") @RequestParam Integer pageSize,
                           @ApiParam(name = "userId", value = "用户id", required = true, type = "long") @RequestParam Long userId,
                           @ApiParam(name = "meterBoxId", value = "电表箱id", required = true, type = "long") @RequestParam Long meterBoxId) {
        return R.ok().put("data", inspectionService.queryPageList(pageNum, pageSize, userId, meterBoxId));
    }

    @PostMapping("/save")
    @ApiOperation(value = "添加巡检记录")
    public R save(RequestEntity requestEntity) {
        MalfunctionEntity malfunctionEntity = JSON.parseObject(requestEntity.getParams(), MalfunctionEntity.class);
        inspectionService.save(malfunctionEntity, requestEntity.getFile());
        return R.ok();
    }


    @ApiOperation(value = "缺陷列表")
    @GetMapping("/faultList")
    public R getInspectionList(
            @ApiParam(name = "page", value = "页码", required = true, type = "int") @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页大小", required = true, type = "int") @RequestParam Integer pageSize,
            @ApiParam(name = "processingStatus", value = "缺陷处理状态", type = "string") @RequestParam(required = false) String processingStatus,
            @ApiParam(name = "defectLevel", value = "缺陷级别", type = "string") @RequestParam(required = false) String defectLevel,
            @ApiParam(name = "userId", value = "用户id", required = true, type = "long") @RequestParam Long userId) {


        return R.ok().put("data", malfunctionService.pageList(page, pageSize, processingStatus, defectLevel, userId));
//        //用于存储转换后的结果
//        JSONArray result = new JSONArray();
//        //将username转换成userid
//        UserEntity userEntity = userService.getUserIdByUserName(username);
//        //获取缺陷列表数据
//        List<InspectionEntity> list = inspectionService.getInspectionList(userEntity.getUserId(), pageSize * (page - 1), pageSize);
//        for (InspectionEntity inspectionEntity : list) {
//            JSONObject jsonObject = new JSONObject();
//            //根据电表箱id转换成电表箱设备编号
//            MeterBoxEntity meterBoxEntity = meterBoxService.getMeterBoxAssetNumberById(inspectionEntity.getMeterBoxId());
//            jsonObject.put("meterBoxAssetNumber", meterBoxEntity.getMeterBoxAssetNumber());
//            //根据id获取电表箱主人姓名
//            EquipmentOwnerEntity equipmentOwnerEntity = equipmentOwnerService.getOwnerByMeterBoxId(meterBoxEntity.getEquipmentOwnerId());
//            jsonObject.put("ownerName", equipmentOwnerEntity.getName());
//            //巡检时间
//            jsonObject.put("checkTime", inspectionEntity.getCreateTime());
//            //巡检人姓名
//            jsonObject.put("userName", username);
//            //根据故障表id转换成故障等级
//            MalfunctionEntity malfunctionEntity = malfunctionService.getDefectLevelById(inspectionEntity.getMalfunctionId());
//            if (malfunctionEntity.getType() == 0) {
//                jsonObject.put("type", "巡检故障");
//            } else if (malfunctionEntity.getType() == 1) {
//                jsonObject.put("type", "验收故障");
//            }
//            if (malfunctionEntity.getDefectLevel() == 0) {
//                jsonObject.put("defectLevel", "无缺陷");
//            } else if (malfunctionEntity.getDefectLevel() == 1) {
//                jsonObject.put("defectLevel", "危急缺陷");
//            } else if (malfunctionEntity.getDefectLevel() == 2) {
//                jsonObject.put("defectLevel", "严重缺陷");
//            } else if (malfunctionEntity.getDefectLevel() == 3) {
//                jsonObject.put("defectLevel", "一般缺陷（轻度）");
//            } else if (malfunctionEntity.getDefectLevel() == 4) {
//                jsonObject.put("defectLevel", "一般缺陷（轻微）");
//            }
//            if (malfunctionEntity.getProcessingStatus() == 0) {
//                jsonObject.put("processingStatus", "待处理");
//            } else if (malfunctionEntity.getProcessingStatus() == 1) {
//                jsonObject.put("processingStatus", "处理中");
//            } else if (malfunctionEntity.getProcessingStatus() == 2) {
//                jsonObject.put("processingStatus", "已处理");
//            }
//            jsonObject.put("level", malfunctionEntity.getDefectLevel());
//            //异常情况
//            jsonObject.put("faultDescription", malfunctionEntity.getFaultDescription());
//            //图片
//            jsonObject.put("images", malfunctionEntity.getImage());
//            result.add(jsonObject);
//        }
//        //回传数据


    }

}
