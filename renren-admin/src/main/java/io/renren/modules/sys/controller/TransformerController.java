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

import io.renren.modules.sys.entity.TransformerEntity;
import io.renren.modules.sys.service.TransformerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 13:44:48
 */
@RestController
@RequestMapping("sys/transformer")
public class TransformerController {
    @Autowired
    private TransformerService transformerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:transformer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = transformerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{transformCode}")
    @RequiresPermissions("sys:transformer:info")
    public R info(@PathVariable("transformCode") String transformCode){
        TransformerEntity transformer = transformerService.getById(transformCode);

        return R.ok().put("transformer", transformer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:transformer:save")
    public R save(@RequestBody TransformerEntity transformer){
        transformerService.save(transformer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:transformer:update")
    public R update(@RequestBody TransformerEntity transformer){
        ValidatorUtils.validateEntity(transformer);
        transformerService.updateById(transformer);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:transformer:delete")
    public R delete(@RequestBody String[] transformCodes){
        transformerService.removeByIds(Arrays.asList(transformCodes));

        return R.ok();
    }


}
