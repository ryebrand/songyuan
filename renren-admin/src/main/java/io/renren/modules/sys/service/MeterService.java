package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.MeterEntity;

import java.util.Map;

/**
 * 电能表表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface MeterService extends IService<MeterEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

