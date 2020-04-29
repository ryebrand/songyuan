package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.DefectNameRecordDao;
import io.renren.entity.DefectNameRecordEntity;
import io.renren.service.DefectNameRecordService;
import org.springframework.stereotype.Service;


@Service("defectNameRecordService")
public class DefectNameRecordServiceImpl extends ServiceImpl<DefectNameRecordDao, DefectNameRecordEntity> implements DefectNameRecordService {



}
