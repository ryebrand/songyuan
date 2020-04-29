package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.EquipmentOwnerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface EquipmentOwnerDao extends BaseMapper<EquipmentOwnerEntity> {
    /**
     * 根据id获取设备主人姓名
     *
     * @param id
     * @return
     */
    EquipmentOwnerEntity getOwnerByMeterBoxId(long id);
}
