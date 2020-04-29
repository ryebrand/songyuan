package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.EquipmentOwnerChangeRecordDao;
import io.renren.modules.sys.entity.EquipmentOwnerChangeRecordEntity;
import io.renren.modules.sys.service.EquipmentOwnerChangeRecordService;


@Service("equipmentOwnerChangeRecordService")
public class EquipmentOwnerChangeRecordServiceImpl extends ServiceImpl<EquipmentOwnerChangeRecordDao, EquipmentOwnerChangeRecordEntity> implements EquipmentOwnerChangeRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EquipmentOwnerChangeRecordEntity> page = this.page(
                new Query<EquipmentOwnerChangeRecordEntity>().getPage(params),
                new QueryWrapper<EquipmentOwnerChangeRecordEntity>()
        );

        return new PageUtils(page);
    }

}
