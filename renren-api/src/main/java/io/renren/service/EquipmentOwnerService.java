package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.EquipmentOwnerEntity;


/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface EquipmentOwnerService extends IService<EquipmentOwnerEntity> {
    /**
     * 根据id获取设备主人姓名
     *
     * @param id
     * @return
     */
    EquipmentOwnerEntity getOwnerByMeterBoxId(long id);
}

