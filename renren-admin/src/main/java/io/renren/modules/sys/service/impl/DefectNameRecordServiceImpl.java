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

import io.renren.modules.sys.dao.DefectNameRecordDao;
import io.renren.modules.sys.entity.DefectNameRecordEntity;
import io.renren.modules.sys.service.DefectNameRecordService;


@Service("defectNameRecordService")
public class DefectNameRecordServiceImpl extends ServiceImpl<DefectNameRecordDao, DefectNameRecordEntity> implements DefectNameRecordService {
    @Autowired
    private DefectNameRecordDao defectNameRecordDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DefectNameRecordEntity> page = this.page(
                new Query<DefectNameRecordEntity>().getPage(params),
                new QueryWrapper<DefectNameRecordEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> getRecords() {
        List<Map<String,Object>> list=null;
        list=defectNameRecordDao.getRecords();
        if(list==null){
            throw new RRException("未找到相关数据");
        }
        return list;
    }

}
