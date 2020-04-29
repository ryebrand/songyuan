package io.renren.controller;

import io.renren.common.utils.R;
import io.renren.dto.AcceptanceBoxListDto;
import io.renren.dto.AcceptanceDto;
import io.renren.dto.AcceptanceSubmitDto;
import io.renren.service.AcceptanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@RestController
@RequestMapping("acceptance")
@Api(tags = "验收接口")
public class ApiAcceptanceController {
    @Autowired
    private AcceptanceService acceptanceService;

    //@PostMapping({"/save"})
    //@ApiOperation("验收提交")
    //public R save(AcceptanceSubmitDto acceptanceSubmitDto) {
    //    this.acceptanceService.updateByAssetNumber(acceptanceSubmitDto);
    //    return R.ok();
    //}

    @PostMapping({"/save"})
    @ApiOperation("验收提交")
    public R save(@RequestParam("malfunctionId") Long malfunctionId, @RequestParam(value = "file",required = false) MultipartFile[] file) {
        this.acceptanceService.updateByAssetNumber(malfunctionId,file);
        return R.ok();
    }

    @GetMapping({"/pendingAcceptance"})
    @ApiOperation(
            value = "扫描资产号查询待验收的电表箱",
            response = AcceptanceDto.class
    )
    public R pendingAcceptance(@ApiParam(name = "meterBoxAssetNumber", value = "电表箱资产号", type = "string") @RequestParam String meterBoxAssetNumber,
                               @ApiParam(name = "userId", value = "用户id", type = "long") @RequestParam Long userId) {
        return R.ok().put("data", acceptanceService.selectByAssetNumber(meterBoxAssetNumber, userId));
    }


    @GetMapping({"/list"})
    @ApiOperation(
            value = "当前用户验收列表",
            response = AcceptanceBoxListDto.class,
            responseContainer = "List"
    )
    public R list(@ApiParam(name = "userId", value = "用户id", type = "long") @RequestParam Long userId,
                  @ApiParam(name = "acceptanceType", value = "验收类型0-新装 ,1-改造, 2-更换 ,3-消缺", type = "string") @RequestParam(required = false) String acceptanceType,
                  @ApiParam(name = "step", value = "验收状态--0-待验收 , 1-存在缺陷 ,2-验收完成", type = "string") @RequestParam(required = false) String step) {
        return R.ok().put("data", acceptanceService.selectByUserId(userId, acceptanceType, step));
    }


}
