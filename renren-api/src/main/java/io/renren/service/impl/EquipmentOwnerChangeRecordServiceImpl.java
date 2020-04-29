package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.exception.RRException;
import io.renren.dao.EquipmentOwnerChangeRecordDao;
import io.renren.dto.EquipmentOwnerChangeRecordDto;
import io.renren.entity.EquipmentOwnerChangeRecordEntity;
import io.renren.service.EquipmentOwnerChangeRecordService;
import org.springframework.stereotype.Service;


/**
 * @author JunCheng He
 */
@Service("equipmentOwnerChangeRecordService")
public class EquipmentOwnerChangeRecordServiceImpl extends ServiceImpl<EquipmentOwnerChangeRecordDao, EquipmentOwnerChangeRecordEntity> implements EquipmentOwnerChangeRecordService {


    /**
     * 根据表箱id查询设备主人变更记录
     *
     * @param pageNum
     * @param pageSize
     * @param meterBoxId
     * @return
     */
    @Override
    public PageInfo<EquipmentOwnerChangeRecordDto> selectByMeterBoxId(Integer pageNum, Integer pageSize, String meterBoxId) {
        if (meterBoxId == null) {
            throw new RRException("表箱id不能为空！");
        }
        PageInfo<EquipmentOwnerChangeRecordDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> this.baseMapper.selectByMeterBoxId(meterBoxId));
        return page;
    }
}
