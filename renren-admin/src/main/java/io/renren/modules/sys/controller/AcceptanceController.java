package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dto.AcceptanceInfoDto;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/acceptance")
public class AcceptanceController {
    @Autowired
    private AcceptanceService acceptanceService;
    @Autowired
    private MalfunctionService malfunctionService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeterBoxService meterBoxService;
    @Autowired
    private EquipmentOwnerService equipmentOwnerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:acceptance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = acceptanceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:acceptance:info")
    public R info(@PathVariable("id") Long id){
        AcceptanceEntity acceptance = acceptanceService.getById(id);

        return R.ok().put("acceptance", acceptance);
    }

    /**
     * 信息
     */
    @RequestMapping("/details/{id}")
    @RequiresPermissions("sys:acceptance:info")
    public R details(@PathVariable("id") Long id) {
        AcceptanceInfoDto acceptanceInfoDto = acceptanceService.selectByAcceptanceId(id);
        return R.ok().put("data", acceptanceInfoDto);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:acceptance:save")
    public R save(@RequestBody AcceptanceEntity acceptance){
        acceptanceService.save(acceptance);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:acceptance:update")
    public R update(@RequestBody AcceptanceEntity acceptance){
        ValidatorUtils.validateEntity(acceptance);
        acceptanceService.updateById(acceptance);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:acceptance:delete")
    public R delete(@RequestBody Long[] ids){
        acceptanceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/acceptanceList")
    @RequiresPermissions("sys:acceptance:list")
    public R getAcceptanceList(int page,int pageSize){
        //用于封装结果
        JSONArray result=new JSONArray();
        //获取验收记录列表
        List<AcceptanceEntity> list=acceptanceService.getAcceptanceInfo(pageSize*(page-1),pageSize);
        for (AcceptanceEntity acceptanceEntity:list) {
            JSONObject jsonObject=new JSONObject();
            if(acceptanceEntity.getType()==0){
                jsonObject.put("equipmentType","电表箱");
            }else if(acceptanceEntity.getType()==1){
                jsonObject.put("equipmentType","电能表");
            }
            jsonObject.put("assetNumber",acceptanceEntity.getAssetNumber());
            if(acceptanceEntity.getAcceptanceType()==0){
                jsonObject.put("acceptanceType","新装");
            }else if(acceptanceEntity.getAcceptanceType()==1){
                jsonObject.put("acceptanceType","改造");
            }else if(acceptanceEntity.getAcceptanceType()==2){
                jsonObject.put("acceptanceType","更换");
            }else if(acceptanceEntity.getAcceptanceType()==3){
                jsonObject.put("acceptanceType","消缺");
            }
            jsonObject.put("defectDescription",acceptanceEntity.getDefectDescription());
            jsonObject.put("buildDate",acceptanceEntity.getBuildDate());
            jsonObject.put("completionDate",acceptanceEntity.getCompletionDate());
            if(acceptanceEntity.getStep()==0){
                jsonObject.put("step","待验收");
            }else if(acceptanceEntity.getStep()==1){
                jsonObject.put("step","存在缺陷");
            }else if(acceptanceEntity.getStep()==2){
                jsonObject.put("step","验收完成");
            }
            //获取故障信息
            if(acceptanceEntity.getMalfunctionId()!=0){
                MalfunctionEntity malfunctionEntity=malfunctionService.getMalfunctionById(acceptanceEntity.getMalfunctionId());
                if(malfunctionEntity.getType()==0){
                    jsonObject.put("malfunctionType","巡检故障");
                }else if(malfunctionEntity.getType()==1){
                    jsonObject.put("malfunctionType","验收故障");
                }
                if(malfunctionEntity.getDefectLevel()==0){
                    jsonObject.put("defectLevel","无缺陷");
                }else if(malfunctionEntity.getDefectLevel()==1){
                    jsonObject.put("defectLevel","危急缺陷");
                }else if(malfunctionEntity.getDefectLevel()==2){
                    jsonObject.put("defectLevel","严重缺陷");
                }else if(malfunctionEntity.getDefectLevel()==3){
                    jsonObject.put("defectLevel","一般缺陷（轻度）");
                }else if(malfunctionEntity.getDefectLevel()==4){
                    jsonObject.put("defectLevel","一般缺陷（轻微）");
                }
                jsonObject.put("faultDescription",malfunctionEntity.getFaultDescription());
                jsonObject.put("otherFaultDescription",malfunctionEntity.getOtherFaultDescription());
                jsonObject.put("images",malfunctionEntity.getImage());
                if(malfunctionEntity.getProcessingStatus()==0){
                    jsonObject.put("processingStatus","待处理");
                }else if(malfunctionEntity.getProcessingStatus()==1){
                    jsonObject.put("processingStatus","处理中");
                }else if(malfunctionEntity.getProcessingStatus()==2){
                    jsonObject.put("processingStatus","处理完成");
                }
                jsonObject.put("malFunctionCreateTime",malfunctionEntity.getCreateTime());
            }
            //获取区域信息
            RegionEntity regionEntity=regionService.getRegionById(acceptanceEntity.getRegionId());
            jsonObject.put("regionName",regionEntity.getName());
            //获取负责人信息
            UserEntity userEntity=userService.getUserById(regionEntity.getUserId());
            jsonObject.put("resposiUserName",userEntity.getName());
            jsonObject.put("resposiUserMobile",userEntity.getMobile());
            //获取电表箱信息
            MeterBoxEntity meterBoxEntity=meterBoxService.getMeterBoxByAssetNumber(acceptanceEntity.getAssetNumber());
            jsonObject.put("taiAreaName",meterBoxEntity.getTaiAreaName());
            jsonObject.put("taiAreaTotalAssetNumber",meterBoxEntity.getTaiAreaTotalAssetNumber());
            jsonObject.put("totalEnergyMeterSeal",meterBoxEntity.getTotalEnergyMeterSeal());
            jsonObject.put("powerSupply",meterBoxEntity.getPowerSupply());
            jsonObject.put("meterBoxModel",meterBoxEntity.getMeterBoxModel());
            jsonObject.put("address",meterBoxEntity.getAddress());
            jsonObject.put("magnification",meterBoxEntity.getMagnification());
            jsonObject.put("meterBoxNumber",meterBoxEntity.getMeterBoxNumber());
            jsonObject.put("boxAddress",meterBoxEntity.getMeterBoxAddress());
            jsonObject.put("acquisitionTerminalAssetNumber",meterBoxEntity.getAcquisitionTerminalAssetNumber());
            jsonObject.put("concentratorSeal",meterBoxEntity.getConcentratorSeal());
            if("0".equals(meterBoxEntity.getMeterBoxStatus())){
                jsonObject.put("meterBoxStatus","运行");
            }else if("1".equals(meterBoxEntity.getMeterBoxStatus())){
                jsonObject.put("meterBoxStatus","在库");
            }
            //获取设备主人信息
            EquipmentOwnerEntity equipmentOwnerEntity=equipmentOwnerService.getOwnerById(meterBoxEntity.getEquipmentOwnerId());
            jsonObject.put("ownerName",equipmentOwnerEntity.getName());
            jsonObject.put("ownerPhone",equipmentOwnerEntity.getPhoneNumber());
            result.add(jsonObject);
        }
        return R.ok().put("data", JSON.toJSONString(result));
    }

    /**
     * 验收状态统计，根据验收状态：0-待验收  1-存在缺陷 2-验收完成
     * 验收通过率=存在缺陷/(存在缺陷+验收完成)
     */
    @GetMapping("/acceptanceCount")
    public R getAcceptanceRate(){
        //待验收数量
        int pendingCount=acceptanceService.getCountByCondition(0,-1,-1,0);
        //存在缺陷数量
        int defectCount=acceptanceService.getCountByCondition(0,-1,-1,1);
        //验收通过数量
        int passCount=acceptanceService.getCountByCondition(0,-1,-1,2);
        //通过率
        double rate=(double) defectCount/(defectCount+passCount);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("pendingCount",pendingCount);
        jsonObject.put("defectCount",defectCount);
        jsonObject.put("passCount",passCount);
        jsonObject.put("acceptanceRate",rate);
        return R.ok().put("data",JSON.toJSONString(jsonObject));
    }
}
