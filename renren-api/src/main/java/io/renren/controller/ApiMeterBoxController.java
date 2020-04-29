package io.renren.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.controller.VO.HtUserVO;
import io.renren.entity.HtUserEntity;
import io.renren.entity.UserEntity;
import io.renren.interceptor.AuthorizationInterceptor;
import io.renren.service.EquipmentOwnerChangeRecordService;
import io.renren.service.MeterBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("meterbox")
@Api(tags = "计量箱接口")
public class ApiMeterBoxController {
    @Autowired
    private MeterBoxService meterBoxService;
    @Autowired
    private HttpServletRequest request;


    @Autowired
    private EquipmentOwnerChangeRecordService equipmentOwnerChangeRecordService;

    @GetMapping("/info")
    @ApiOperation("查看电表箱详情")
    public R info(@ApiParam(name = "meterBoxAssetNumber", value = "电表箱编号") @RequestParam String meterBoxAssetNumber) {
        return R.ok().put("data", meterBoxService.selectByMeterBoxAssetNumber(meterBoxAssetNumber));
    }

    @GetMapping("/recent")
    @ApiOperation("最近巡检过的表箱列表")
    public R recent(@ApiParam(name = "pageNum", value = "页码", required = true, type = "int") @RequestParam Integer pageNum,
                    @ApiParam(name = "pageSize", value = "每页大小", required = true, type = "int") @RequestParam Integer pageSize,
                    @ApiParam(name = "userId", value = "用户id", required = true, type = "long") @RequestParam Long userId) {

        return R.ok().put("data", meterBoxService.selectByUserId(pageNum, pageSize, userId));
    }

    @GetMapping("/personChange")
    @ApiOperation("设备主人变更记录")
    public R personChange(@ApiParam(name = "pageNum", value = "页码", required = true, type = "int") @RequestParam Integer pageNum,
                          @ApiParam(name = "pageSize", value = "每页大小", required = true, type = "int") @RequestParam Integer pageSize,
                          @ApiParam(name = "meterBoxId", value = "表箱id", required = true, type = "string") @RequestParam String meterBoxId) {
        return R.ok().put("data", equipmentOwnerChangeRecordService.selectByMeterBoxId(pageNum, pageSize, meterBoxId));
    }



    @GetMapping("/search")
    @ApiOperation("表箱搜索")
    public R search(@ApiParam(name = "pageNum", value = "页码", required = true, type = "int") @RequestParam Integer pageNum,
                    @ApiParam(name = "pageSize", value = "每页大小", required = true, type = "int") @RequestParam Integer pageSize,
                    @ApiParam(name = "userId", value = "每页大小", required = true, type = "int") @RequestParam long userId,
                    @ApiParam(name = "assetNumOrAddress", value = "表箱资产号或地址", required = true, type = "string") @RequestParam String assetNumOrAddress) {
        return R.ok().put("data", meterBoxService.selectByAssetNumOrAddress(pageNum, pageSize, assetNumOrAddress,userId));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ApiOperation("高压用户更新")
    public R update(@RequestBody HtUserVO htUser){
        ValidatorUtils.validateEntity(htUser);
        meterBoxService.updateByHtUserId(htUser);
        return R.ok();
    }


}
