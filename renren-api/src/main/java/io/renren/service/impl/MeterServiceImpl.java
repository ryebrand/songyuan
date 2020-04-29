package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.dao.MeterDao;
import io.renren.entity.MeterEntity;
import io.renren.entity.TransformerEntity;
import io.renren.service.MeterService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author JunCheng He
 */
@Service("meterService")
public class MeterServiceImpl extends ServiceImpl<MeterDao, MeterEntity> implements MeterService {


    /**
     * 根据电表箱id查询电表列表
     *
     * @param meterBoxId 电表箱id
     * @return
     */
    @Override
    public List<TransformerEntity> selectByMeterBoxId(String meterBoxId) {
        if (meterBoxId == null) {
            throw new RRException("电表箱id不能为空！");
        }
        //List<MeterEntity> list = this.list(new QueryWrapper<MeterEntity>().eq("ht_user_id", meterBoxId).eq("delete_status", 0));

        List<TransformerEntity> list = this.baseMapper.selectByUserId(meterBoxId);
        return list;
    }
}
