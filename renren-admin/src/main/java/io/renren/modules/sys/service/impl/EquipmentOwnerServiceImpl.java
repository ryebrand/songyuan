package io.renren.modules.sys.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.sys.entity.EquipmentOwnerChangeRecordEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.EquipmentOwnerDao;
import io.renren.modules.sys.entity.EquipmentOwnerEntity;
import io.renren.modules.sys.service.EquipmentOwnerService;


/**
 * @author JunCheng He
 */
@Service("equipmentOwnerService")
public class EquipmentOwnerServiceImpl extends ServiceImpl<EquipmentOwnerDao, EquipmentOwnerEntity> implements EquipmentOwnerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String phoneNumber = (String) params.get("phoneNumber");
        String studioCamera = (String) params.get("studioCamera");
        IPage<EquipmentOwnerEntity> page = this.page(
                new Query<EquipmentOwnerEntity>().getPage(params),
                new QueryWrapper<EquipmentOwnerEntity>()
                        .like(!StringUtils.isBlank(name), "name", name)
                        .like(!StringUtils.isBlank(phoneNumber), "phone_number", phoneNumber)
                        .like(!StringUtils.isBlank(studioCamera), "studio_camera", studioCamera)
        );
        return new PageUtils(page);
    }

    @Override
    public EquipmentOwnerEntity getOwnerById(long id) {
        EquipmentOwnerEntity equipmentOwnerEntity = null;
        equipmentOwnerEntity = this.baseMapper.getOwnerById(id);
        if (equipmentOwnerEntity == null) {
            throw new RRException("未找到对应的数据");
        }
        return equipmentOwnerEntity;
    }

    @Override
    public EquipmentOwnerEntity getOwnerByMeterBoxId(long id) {
        EquipmentOwnerEntity equipmentOwnerEntity = null;
        equipmentOwnerEntity = this.baseMapper.getOwnerByMeterBoxId(id);
        if (equipmentOwnerEntity == null) {
            throw new RRException("未找得到对应的数据");
        }
        return equipmentOwnerEntity;
    }

    /**
     * 根据手机号码查询责任人ID
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public Integer selectByPhoneNumber(String phoneNumber) {
        return this.baseMapper.selectByPhoneNumber(phoneNumber);
    }

}
