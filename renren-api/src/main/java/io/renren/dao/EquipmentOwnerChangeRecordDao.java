package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import io.renren.dto.EquipmentOwnerChangeRecordDto;
import io.renren.entity.EquipmentOwnerChangeRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设备责任人变更记录表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface EquipmentOwnerChangeRecordDao extends BaseMapper<EquipmentOwnerChangeRecordEntity> {

    /**
     * 根据表箱id查询设备主人变更记录
     *
     * @param meterBoxId
     * @return
     */
    Page<EquipmentOwnerChangeRecordDto> selectByMeterBoxId(@Param("meterBoxId") String meterBoxId);

}
