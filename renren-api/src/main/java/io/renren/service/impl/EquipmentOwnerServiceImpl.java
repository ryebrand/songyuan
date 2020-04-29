package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.dao.EquipmentOwnerDao;
import io.renren.entity.EquipmentOwnerEntity;
import io.renren.service.EquipmentOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("equipmentOwnerService")
public class EquipmentOwnerServiceImpl extends ServiceImpl<EquipmentOwnerDao, EquipmentOwnerEntity> implements EquipmentOwnerService {
    @Autowired
    private EquipmentOwnerDao equipmentOwnerDao;

    @Override
    public EquipmentOwnerEntity getOwnerByMeterBoxId(long id) {
        EquipmentOwnerEntity equipmentOwnerEntity=null;
        equipmentOwnerEntity=equipmentOwnerDao.getOwnerByMeterBoxId(id);
        if(equipmentOwnerEntity==null){
            throw new RRException("未找得到对应的数据");
        }
        return equipmentOwnerEntity;
    }
}
