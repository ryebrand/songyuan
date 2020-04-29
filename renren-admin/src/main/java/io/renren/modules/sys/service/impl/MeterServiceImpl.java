package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MeterDao;
import io.renren.modules.sys.entity.MeterEntity;
import io.renren.modules.sys.service.MeterService;


@Service("meterService")
public class MeterServiceImpl extends ServiceImpl<MeterDao, MeterEntity> implements MeterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeterEntity> page = this.page(
                new Query<MeterEntity>().getPage(params),
                new QueryWrapper<MeterEntity>()
                        .eq("meter_box_id", params.get("id"))
                        .eq("delete_status", 0)
        );



        return new PageUtils(page);
    }

}
