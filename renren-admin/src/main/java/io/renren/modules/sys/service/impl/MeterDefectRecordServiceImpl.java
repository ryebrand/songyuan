package io.renren.modules.sys.service.impl;

import io.renren.common.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MeterDefectRecordDao;
import io.renren.modules.sys.entity.MeterDefectRecordEntity;
import io.renren.modules.sys.service.MeterDefectRecordService;


@Service("meterDefectRecordService")
public class MeterDefectRecordServiceImpl extends ServiceImpl<MeterDefectRecordDao, MeterDefectRecordEntity> implements MeterDefectRecordService {
    @Autowired
    private MeterDefectRecordDao meterDefectRecordDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeterDefectRecordEntity> page = this.page(
                new Query<MeterDefectRecordEntity>().getPage(params),
                new QueryWrapper<MeterDefectRecordEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Integer>> getMeterDefectRecordById() {
        List<Map<String,Integer>> list=null;
        list=meterDefectRecordDao.getMeterDefectRecordById();
        if(list==null){
            throw new RRException("未找到相关数据");
        }
        return list;
    }

}
