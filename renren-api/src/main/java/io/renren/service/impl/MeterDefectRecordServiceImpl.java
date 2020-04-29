package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MeterDefectRecordDao;
import io.renren.entity.MeterDefectRecordEntity;
import io.renren.service.MeterDefectRecordService;
import org.springframework.stereotype.Service;


@Service("meterDefectRecordService")
public class MeterDefectRecordServiceImpl extends ServiceImpl<MeterDefectRecordDao, MeterDefectRecordEntity> implements MeterDefectRecordService {


}
