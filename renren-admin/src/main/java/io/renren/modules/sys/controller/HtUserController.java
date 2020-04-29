package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.entity.EquipmentOwnerChangeRecordEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.UserEntity;
import io.renren.modules.sys.service.EquipmentOwnerChangeRecordService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.HtUserEntity;
import io.renren.modules.sys.service.HtUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 14:11:13
 */
@RestController
@RequestMapping("sys/htuser")
public class HtUserController extends AbstractController {
    @Autowired
    private HtUserService htUserService;
    @Autowired
    private EquipmentOwnerChangeRecordService equipmentOwnerChangeRecordService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:htuser:list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = htUserService.queryPage(params);

        return R.ok().put("page", htUserService.pageList(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:htuser:info")
    public R info(@PathVariable("id") Long id){
        HtUserEntity htUser = htUserService.getById(id);

        UserEntity userByName = userService.getUserById(htUser.getCheckUser());
        HtUserVO htUserVO = new HtUserVO();
        BeanUtils.copyProperties(htUser,htUserVO);
        htUserVO.setCheckUserName(userByName.getUsername());

        return R.ok().put("htUser", htUserVO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:htuser:save")
    public R save(@RequestBody HtUserEntity htUser){
        htUserService.save(htUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:htuser:update")
    public R update(@RequestBody HtUserEntity htUser){
        ValidatorUtils.validateEntity(htUser);
        htUserService.updateById(htUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:htuser:delete")
    public R delete(@RequestBody String[] ids){
        htUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 更改责任人
     */
    @RequestMapping("/changeOwner/{id}/{ownerId}")
    @RequiresPermissions("sys:htuser:info")
    public R info(@PathVariable("id") String id, @PathVariable("ownerId") Long ownerId) {
        htUserService.updateByIdAndOwnerId(id, ownerId);

        EquipmentOwnerChangeRecordEntity equipmentOwnerChangeRecordEntity = new EquipmentOwnerChangeRecordEntity();
        equipmentOwnerChangeRecordEntity.setChangeTime(new Date());
        equipmentOwnerChangeRecordEntity.setDeleteStatus(0);
        equipmentOwnerChangeRecordEntity.setHtUserId(id);
        equipmentOwnerChangeRecordEntity.setEquipmentOwnerId(ownerId);
        equipmentOwnerChangeRecordEntity.setChanger(this.getUserId());
        equipmentOwnerChangeRecordService.save(equipmentOwnerChangeRecordEntity);
        return R.ok();
    }

    @RequestMapping("/upload")
    @RequiresPermissions("sys:watchbox:upload")
    public R update(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        htUserService.batchImport(fileName, file);
        return R.ok();
    }

}
