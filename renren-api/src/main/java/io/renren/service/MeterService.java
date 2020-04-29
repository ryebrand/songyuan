package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MeterEntity;
import io.renren.entity.TransformerEntity;

import java.util.List;


/**
 * 电能表表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface MeterService extends IService<MeterEntity> {

    /**
     * 根据电表箱id查询电表列表
     *
     * @param meterBoxId 电表箱id
     * @return
     */
    List<TransformerEntity> selectByMeterBoxId(String meterBoxId);

}

