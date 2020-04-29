package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import io.renren.dto.EquipmentOwnerChangeRecordDto;
import io.renren.entity.EquipmentOwnerChangeRecordEntity;


/**
 * 设备责任人变更记录表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface EquipmentOwnerChangeRecordService extends IService<EquipmentOwnerChangeRecordEntity> {
    /**
     * 根据表箱id查询设备主人变更记录
     * @param pageNun
     * @param pageSize
     * @param meterBoxId
     * @return
     */
    PageInfo<EquipmentOwnerChangeRecordDto> selectByMeterBoxId(Integer pageNum, Integer pageSize, String meterBoxId);
}

